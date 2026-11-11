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

    @Query(
        "select (count(organization) > 0) from TenantOrganization organization where organization.domain is not null and lower(trim(organization.domain)) = lower(trim(:domain)) and organization.id <> :excludeId"
    )
    boolean existsByDomainIgnoreCaseAndIdNot(@Param("domain") String domain, @Param("excludeId") Long excludeId);

    @Query(
        "select (count(organization) > 0) from TenantOrganization organization where lower(trim(organization.organizationName)) = lower(trim(:organizationName)) and organization.id <> :excludeId"
    )
    boolean existsByOrganizationNameIgnoreCaseAndIdNot(
        @Param("organizationName") String organizationName,
        @Param("excludeId") Long excludeId
    );

    boolean existsBySiteType(String siteType);

    boolean existsBySubscriptionPlan(String subscriptionPlan);

    boolean existsByRetailerTypeCode(String retailerTypeCode);
}
