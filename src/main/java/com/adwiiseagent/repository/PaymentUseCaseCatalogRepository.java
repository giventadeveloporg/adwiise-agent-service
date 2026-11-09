package com.adwiiseagent.repository;

import com.adwiiseagent.domain.PaymentUseCaseCatalog;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentUseCaseCatalogRepository extends JpaRepository<PaymentUseCaseCatalog, Long> {
    List<PaymentUseCaseCatalog> findByIsActiveTrueOrderBySortOrderAscLabelAsc();
}
