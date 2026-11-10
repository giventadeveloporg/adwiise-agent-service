package com.adwiiseagent.repository;

import com.adwiiseagent.domain.TenantOnboardingRequest;
import com.adwiiseagent.domain.enumeration.OnboardingRequestStatus;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TenantOnboardingRequest entity.
 */
@Repository
public interface TenantOnboardingRequestRepository
    extends JpaRepository<TenantOnboardingRequest, Long>, JpaSpecificationExecutor<TenantOnboardingRequest> {
    Optional<TenantOnboardingRequest> findByRequestCode(String requestCode);

    boolean existsByRequestCode(String requestCode);

    boolean existsByStatusAndRequestedHostnameIgnoreCase(OnboardingRequestStatus status, String requestedHostname);

    boolean existsByStatusAndContactEmailIgnoreCase(OnboardingRequestStatus status, String contactEmail);

    boolean existsByAssignedTenantIdAndIdNot(String assignedTenantId, Long id);

    /**
     * Drop a tenant id recorded on onboarding history. PostgreSQL's unique index allows many nulls,
     * so the id can be provisioned again after the organization is gone.
     */
    @Modifying(flushAutomatically = true, clearAutomatically = false)
    @Query("update TenantOnboardingRequest r set r.assignedTenantId = null where r.assignedTenantId = :tenantId")
    int releaseAssignedTenantId(@Param("tenantId") String tenantId);

    @Modifying(flushAutomatically = true, clearAutomatically = false)
    @Query("update TenantOnboardingRequest r set r.assignedTenantId = null where r.assignedTenantId = :tenantId and r.id <> :id")
    int releaseAssignedTenantIdExcept(@Param("tenantId") String tenantId, @Param("id") Long id);

    boolean existsBySiteType(String siteType);

    boolean existsByDomainOwnership(String domainOwnership);

    /** Row lock so two concurrent approve/reject calls cannot both act on a PENDING request. */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select r from TenantOnboardingRequest r where r.id = :id")
    Optional<TenantOnboardingRequest> findByIdForUpdate(@Param("id") Long id);
}
