package com.adwiiseagent.repository;

import com.adwiiseagent.domain.SubscriptionPlanCatalog;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionPlanCatalogRepository
    extends JpaRepository<SubscriptionPlanCatalog, Long>, JpaSpecificationExecutor<SubscriptionPlanCatalog> {
    Optional<SubscriptionPlanCatalog> findByCodeAndTenantId(String code, String tenantId);

    Optional<SubscriptionPlanCatalog> findByCodeAndTenantIdIsNull(String code);
}
