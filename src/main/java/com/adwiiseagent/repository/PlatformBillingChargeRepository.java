package com.adwiiseagent.repository;

import com.adwiiseagent.domain.PlatformBillingCharge;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PlatformBillingChargeRepository
    extends JpaRepository<PlatformBillingCharge, Long>, JpaSpecificationExecutor<PlatformBillingCharge> {
    Optional<PlatformBillingCharge> findByStripeEventId(String stripeEventId);
}
