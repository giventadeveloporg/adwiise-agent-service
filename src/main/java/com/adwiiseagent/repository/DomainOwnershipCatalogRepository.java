package com.adwiiseagent.repository;

import com.adwiiseagent.domain.DomainOwnershipCatalog;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DomainOwnershipCatalogRepository
    extends JpaRepository<DomainOwnershipCatalog, Long>, JpaSpecificationExecutor<DomainOwnershipCatalog> {
    Optional<DomainOwnershipCatalog> findByCodeAndTenantId(String code, String tenantId);

    Optional<DomainOwnershipCatalog> findByCodeAndTenantIdIsNull(String code);

    @Query(
        """
        select c from DomainOwnershipCatalog c
        where c.code = :code and c.isActive = true
          and (c.tenantId is null or (:tenantId is not null and c.tenantId = :tenantId))
        order by case when c.tenantId is null then 1 else 0 end
        """
    )
    List<DomainOwnershipCatalog> findActiveForTenant(@Param("code") String code, @Param("tenantId") String tenantId);
}
