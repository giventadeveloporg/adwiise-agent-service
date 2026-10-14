package com.adwiiseagent.repository;

import com.adwiiseagent.domain.TenantProductBilling;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TenantProductBillingRepository
    extends JpaRepository<TenantProductBilling, Long>, JpaSpecificationExecutor<TenantProductBilling> {
    Optional<TenantProductBilling> findByStripeSubscriptionId(String stripeSubscriptionId);
}
