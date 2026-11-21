package com.adwiiseagent.service.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for contact form email submission.
 * SES from/to addresses are resolved server-side from tenant_email_addresses using emailType.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ContactFormDTO implements Serializable {

    @NotNull
    @NotBlank
    @Size(max = 255)
    private String firstName;

    @NotNull
    @NotBlank
    @Size(max = 255)
    private String lastName;

    @NotNull
    @NotBlank
    private String messageBody;

    /**
     * Visitor email address (Reply-To and confirmation recipient).
     */
    @NotNull
    @NotBlank
    @Size(max = 255)
    @Email
    @JsonAlias("fromEmail")
    private String senderEmail;

    /**
     * Tenant email type used to look up verified from/copy-to addresses (e.g. CONTACT).
     */
    @NotNull
    @NotBlank
    @Size(max = 50)
    private String emailType;

    /**
     * Optional inbox override. When set, the batch job sends the submission here
     * instead of the address resolved from tenant_email_addresses.
     */
    @Email
    @Size(max = 255)
    private String toEmail;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public String getEmailType() {
        return emailType;
    }

    public void setEmailType(String emailType) {
        this.emailType = emailType;
    }

    public String getToEmail() {
        return toEmail;
    }

    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContactFormDTO)) {
            return false;
        }

        ContactFormDTO contactFormDTO = (ContactFormDTO) o;
        return (
            Objects.equals(this.firstName, contactFormDTO.firstName) &&
            Objects.equals(this.lastName, contactFormDTO.lastName) &&
            Objects.equals(this.senderEmail, contactFormDTO.senderEmail) &&
            Objects.equals(this.emailType, contactFormDTO.emailType)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.firstName, this.lastName, this.senderEmail, this.emailType);
    }

    @Override
    public String toString() {
        return (
            "ContactFormDTO{" +
            "firstName='" +
            getFirstName() +
            "'" +
            ", lastName='" +
            getLastName() +
            "'" +
            ", senderEmail='" +
            getSenderEmail() +
            "'" +
            ", emailType='" +
            getEmailType() +
            "'" +
            "}"
        );
    }
}
