package com.adwiiseagent.service;

/**
 * Triggers the paid-ticket QR email in the batch-jobs service.
 * A second call for the same transaction is skipped once confirmation has been claimed.
 */
public interface TicketConfirmationEmailBatchJobService {
    /**
     * Claim the send, then ask the batch job to deliver the ticket email.
     * Returns without sending when the ticket confirmation was already claimed.
     *
     * @throws IllegalStateException when the batch job does not accept the send
     */
    void sendIfNotAlreadySent(String tenantId, Long eventId, Long ticketTransactionId, String recipientEmail, String emailHostUrlPrefix);
}
