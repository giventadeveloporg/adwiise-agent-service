package com.adwiiseagent.service.impl;

import com.adwiiseagent.domain.EventTicketTransaction;
import com.adwiiseagent.properties.BatchJobProperties;
import com.adwiiseagent.repository.EventTicketTransactionRepository;
import com.adwiiseagent.service.TicketConfirmationEmailBatchJobService;
import com.adwiiseagent.service.TicketConfirmationSendClaim;
import com.adwiiseagent.service.dto.TicketConfirmationEmailJobRequest;
import com.adwiiseagent.service.dto.TicketConfirmationEmailJobResponse;
import java.time.Duration;
import java.time.ZonedDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

/**
 * Posts the paid-ticket QR email to the batch-jobs service after claiming the send.
 */
@Service
public class TicketConfirmationEmailBatchJobServiceImpl implements TicketConfirmationEmailBatchJobService {

    private static final Logger log = LoggerFactory.getLogger(TicketConfirmationEmailBatchJobServiceImpl.class);

    private static final String TICKET_CONFIRMATION_EMAIL_ENDPOINT = "/api/batch-jobs/ticket-confirmation-email";

    private final WebClient webClient;
    private final BatchJobProperties batchJobProperties;
    private final TicketConfirmationSendClaim ticketConfirmationSendClaim;
    private final EventTicketTransactionRepository eventTicketTransactionRepository;

    public TicketConfirmationEmailBatchJobServiceImpl(
        BatchJobProperties batchJobProperties,
        TicketConfirmationSendClaim ticketConfirmationSendClaim,
        EventTicketTransactionRepository eventTicketTransactionRepository,
        @Qualifier("batchJobsWebClient") WebClient batchJobsWebClient
    ) {
        this.batchJobProperties = batchJobProperties;
        this.ticketConfirmationSendClaim = ticketConfirmationSendClaim;
        this.eventTicketTransactionRepository = eventTicketTransactionRepository;
        this.webClient = batchJobsWebClient;
    }

    @Override
    public void sendIfNotAlreadySent(
        String tenantId,
        Long eventId,
        Long ticketTransactionId,
        String recipientEmail,
        String emailHostUrlPrefix
    ) {
        ZonedDateTime claimedAt = ticketConfirmationSendClaim.claim(ticketTransactionId);
        if (claimedAt == null) {
            log.info("Ticket confirmation for transaction {} was already sent. Skipping a second copy.", ticketTransactionId);
            return;
        }

        EventTicketTransaction managed = eventTicketTransactionRepository.findById(ticketTransactionId).orElse(null);
        if (managed != null) {
            managed.setConfirmationSentAt(claimedAt);
        }

        try {
            TicketConfirmationEmailJobResponse response = trigger(
                tenantId,
                eventId,
                ticketTransactionId,
                recipientEmail,
                emailHostUrlPrefix
            );
            if (response == null || !Boolean.TRUE.equals(response.getSuccess())) {
                String message = response == null ? "Ticket confirmation email job returned no response" : response.getMessage();
                throw new IllegalStateException(message);
            }
            log.info(
                "Ticket confirmation email job accepted for transaction {}. JobExecutionId: {}",
                ticketTransactionId,
                response.getJobExecutionId()
            );
        } catch (RuntimeException e) {
            if (managed != null) {
                managed.setConfirmationSentAt(null);
            }
            ticketConfirmationSendClaim.release(ticketTransactionId, claimedAt);
            throw e;
        }
    }

    private TicketConfirmationEmailJobResponse trigger(
        String tenantId,
        Long eventId,
        Long ticketTransactionId,
        String recipientEmail,
        String emailHostUrlPrefix
    ) {
        if (!Boolean.TRUE.equals(batchJobProperties.getEnabled())) {
            log.warn("Batch job service is disabled. Ticket confirmation email was not sent.");
            TicketConfirmationEmailJobResponse response = new TicketConfirmationEmailJobResponse();
            response.setSuccess(false);
            response.setMessage("Batch job service is disabled");
            return response;
        }

        TicketConfirmationEmailJobRequest request = new TicketConfirmationEmailJobRequest();
        request.setTenantId(tenantId);
        request.setEventId(eventId);
        request.setTicketTransactionId(ticketTransactionId);
        request.setRecipientEmail(recipientEmail);
        request.setEmailHostUrlPrefix(emailHostUrlPrefix);

        try {
            log.info(
                "Triggering ticket confirmation email job - tenantId: {}, eventId: {}, ticketTransactionId: {}, recipientEmail: {}",
                tenantId,
                eventId,
                ticketTransactionId,
                recipientEmail
            );
            return webClient
                .post()
                .uri(TICKET_CONFIRMATION_EMAIL_ENDPOINT)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(TicketConfirmationEmailJobResponse.class)
                .timeout(Duration.ofMillis(batchJobProperties.getTimeout()))
                .block();
        } catch (WebClientResponseException e) {
            log.error("HTTP error triggering ticket confirmation email job: {} - {}", e.getStatusCode(), e.getResponseBodyAsString(), e);
            throw new IllegalStateException("HTTP " + e.getStatusCode() + ": " + e.getMessage(), e);
        }
    }
}
