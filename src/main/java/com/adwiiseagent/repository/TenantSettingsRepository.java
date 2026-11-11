package com.adwiiseagent.repository;

import com.adwiiseagent.domain.TenantSettings;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TenantSettings entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TenantSettingsRepository extends JpaRepository<TenantSettings, Long>, JpaSpecificationExecutor<TenantSettings> {
    /**
     * Find tenant settings by tenant ID.
     *
     * @param tenantId the tenant ID
     * @return the tenant settings
     */
    Optional<TenantSettings> findByTenantId(String tenantId);

    List<TenantSettings> findByTenantOrganizationId(Long organizationId);

    @Query("select s.tenantId from TenantSettings s order by s.tenantId")
    List<String> findAllTenantIds();

    @Query("select s.tenantOrganization.id, s.tenantId from TenantSettings s where s.tenantOrganization.id in :ids order by s.tenantId")
    List<Object[]> findTenantIdsByOrganizationIds(@Param("ids") Collection<Long> ids);
}
