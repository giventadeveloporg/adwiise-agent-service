package com.adwiiseagent.service;

import com.adwiiseagent.service.dto.TenantOnboardingRejectDTO;
import com.adwiiseagent.service.dto.TenantOnboardingRequestDTO;
import com.adwiiseagent.service.dto.TenantOnboardingSubmitDTO;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.adwiiseagent.domain.TenantOnboardingRequest}.
 * Approval (tenant provisioning) lives in {@link TenantOnboardingProvisioningService}.
 */
public interface TenantOnboardingRequestService {
    /**
     * Create a PENDING request. Status, request code and timestamps are always assigned here.
     */
    TenantOnboardingRequestDTO submit(TenantOnboardingSubmitDTO submitDTO);

    /**
     * Edit request content while it is PENDING or FAILED.
     */
    Optional<TenantOnboardingRequestDTO> partialUpdate(TenantOnboardingRequestDTO dto);

    Optional<TenantOnboardingRequestDTO> findOne(Long id);

    /**
     * Move a PENDING or FAILED request to REJECTED.
     */
    TenantOnboardingRequestDTO reject(Long id, TenantOnboardingRejectDTO rejectDTO);
}
