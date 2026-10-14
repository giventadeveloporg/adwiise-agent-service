package com.adwiiseagent.service;

import com.adwiiseagent.domain.TenantProductBilling;
import com.adwiiseagent.repository.TenantProductBillingRepository;
import com.adwiiseagent.service.criteria.TenantProductBillingCriteria;
import com.adwiiseagent.service.dto.TenantProductBillingDTO;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class TenantProductBillingQueryService {

    private final TenantProductBillingRepository repository;
    private final TenantProductBillingService tenantProductBillingService;

    public TenantProductBillingQueryService(
        TenantProductBillingRepository repository,
        TenantProductBillingService tenantProductBillingService
    ) {
        this.repository = repository;
        this.tenantProductBillingService = tenantProductBillingService;
    }

    public Page<TenantProductBillingDTO> findByCriteria(TenantProductBillingCriteria criteria, Pageable page) {
        return repository.findAll(specification(criteria), page).map(tenantProductBillingService::toDto);
    }

    private Specification<TenantProductBilling> specification(TenantProductBillingCriteria criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (criteria != null) {
                if (criteria.getId() != null && criteria.getId().getEquals() != null) {
                    predicates.add(cb.equal(root.get("id"), criteria.getId().getEquals()));
                }
                if (criteria.getTenantId() != null && criteria.getTenantId().getEquals() != null) {
                    predicates.add(cb.equal(root.get("tenantId"), criteria.getTenantId().getEquals()));
                }
                if (criteria.getProduct() != null && criteria.getProduct().getEquals() != null) {
                    predicates.add(cb.equal(root.get("product"), criteria.getProduct().getEquals()));
                }
                if (criteria.getStatus() != null && criteria.getStatus().getEquals() != null) {
                    predicates.add(cb.equal(root.get("status"), criteria.getStatus().getEquals()));
                }
                if (criteria.getChargeEnabled() != null && criteria.getChargeEnabled().getEquals() != null) {
                    predicates.add(cb.equal(root.get("chargeEnabled"), criteria.getChargeEnabled().getEquals()));
                }
                if (criteria.getStripeSubscriptionId() != null && criteria.getStripeSubscriptionId().getEquals() != null) {
                    predicates.add(cb.equal(root.get("stripeSubscriptionId"), criteria.getStripeSubscriptionId().getEquals()));
                }
            }
            return cb.and(predicates.toArray(Predicate[]::new));
        };
    }
}
