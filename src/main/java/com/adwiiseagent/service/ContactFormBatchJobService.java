package com.adwiiseagent.service;

import com.adwiiseagent.service.dto.ContactFormEmailJobRequest;
import com.adwiiseagent.service.dto.ContactFormEmailJobResponse;

/**
 * Client service for submitting contact form email jobs to the batch jobs microservice.
 * Mirrors the pattern used by {@link com.adwiiseagent.service.BatchJobEmailService}.
 */
public interface ContactFormBatchJobService {
    /**
     * Trigger a contact form email batch job.
     *
     * @param request the contact form email job request
     * @return the contact form email job response
     */
    ContactFormEmailJobResponse triggerContactFormEmailJob(ContactFormEmailJobRequest request);
}
