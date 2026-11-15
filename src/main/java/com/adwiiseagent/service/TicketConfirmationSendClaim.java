package com.adwiiseagent.service;

import com.adwiiseagent.repository.EventTicketTransactionRepository;
import java.time.ZonedDateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Atomically claims a ticket confirmation send so the purchase path and the success page cannot both email.
 */
@Service
public class TicketConfirmationSendClaim {

    private final EventTicketTransactionRepository eventTicketTransactionRepository;

    public TicketConfirmationSendClaim(EventTicketTransactionRepository eventTicketTransactionRepository) {
        this.eventTicketTransactionRepository = eventTicketTransactionRepository;
    }

    /**
     * @return the timestamp written when this call claimed the send, or null when it was already claimed
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ZonedDateTime claim(Long transactionId) {
        ZonedDateTime sentAt = ZonedDateTime.now();
        int updated = eventTicketTransactionRepository.claimConfirmationSend(transactionId, sentAt);
        return updated == 1 ? sentAt : null;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void release(Long transactionId, ZonedDateTime claimedAt) {
        eventTicketTransactionRepository.releaseConfirmationSend(transactionId, claimedAt, ZonedDateTime.now());
    }
}
