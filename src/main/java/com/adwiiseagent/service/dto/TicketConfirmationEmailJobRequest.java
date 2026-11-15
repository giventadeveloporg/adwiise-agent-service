package com.adwiiseagent.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Request posted to the batch-jobs ticket confirmation email endpoint.
 */
public class TicketConfirmationEmailJobRequest {

    @JsonProperty("tenantId")
    private String tenantId;

    @JsonProperty("eventId")
    private Long eventId;

    @JsonProperty("ticketTransactionId")
    private Long ticketTransactionId;

    @JsonProperty("recipientEmail")
    private String recipientEmail;

    @JsonProperty("emailHostUrlPrefix")
    private String emailHostUrlPrefix;

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Long getTicketTransactionId() {
        return ticketTransactionId;
    }

    public void setTicketTransactionId(Long ticketTransactionId) {
        this.ticketTransactionId = ticketTransactionId;
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public void setRecipientEmail(String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }

    public String getEmailHostUrlPrefix() {
        return emailHostUrlPrefix;
    }

    public void setEmailHostUrlPrefix(String emailHostUrlPrefix) {
        this.emailHostUrlPrefix = emailHostUrlPrefix;
    }
}
