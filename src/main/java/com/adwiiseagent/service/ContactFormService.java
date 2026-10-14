package com.adwiiseagent.service;

import com.adwiiseagent.service.dto.ContactFormDTO;
import java.util.Map;

/**
 * Service Interface for sending contact form emails.
 */
public interface ContactFormService {
    /**
     * Send a contact form email.
     *
     * @param contactFormDTO the contact form data
     * @param tenantId the tenant ID
     * @return Map containing success status and message ID
     */
    Map<String, Object> sendContactFormEmail(ContactFormDTO contactFormDTO, String tenantId);
}
