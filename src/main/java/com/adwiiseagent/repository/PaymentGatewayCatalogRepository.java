package com.adwiiseagent.repository;

import com.adwiiseagent.domain.PaymentGatewayCatalog;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentGatewayCatalogRepository extends JpaRepository<PaymentGatewayCatalog, Long> {
    List<PaymentGatewayCatalog> findByIsActiveTrueOrderBySortOrderAscLabelAsc();

    Optional<PaymentGatewayCatalog> findFirstByCodeAndTenantIdIsNull(String code);

    Optional<PaymentGatewayCatalog> findFirstByCodeAndTenantId(String code, String tenantId);
}
