package com.adwiiseagent.repository;

import com.adwiiseagent.domain.TenantOrganization;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TenantOrganization entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TenantOrganizationRepository
    extends JpaRepository<TenantOrganization, Long>, JpaSpecificationExecutor<TenantOrganization> {
    @Query("select s.tenantOrganization from TenantSettings s where s.tenantId = :tenantId")
    Optional<TenantOrganization> findByWebsiteTenantId(@Param("tenantId") String tenantId);

    java.util.List<TenantOrganization> findByParentOrganizationId(Long parentOrganizationId);

    long countByParentOrganizationId(Long parentOrganizationId);

    boolean existsByDomainIgnoreCase(String domain);

    boolean existsBySiteType(String siteType);

    boolean existsBySubscriptionPlan(String subscriptionPlan);

    boolean existsByRetailerTypeCode(String retailerTypeCode);
}
