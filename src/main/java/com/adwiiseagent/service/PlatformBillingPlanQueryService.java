package com.adwiiseagent.service;

import com.adwiiseagent.domain.PlatformBillingPlan;
import com.adwiiseagent.repository.PlatformBillingPlanRepository;
import com.adwiiseagent.service.criteria.PlatformBillingPlanCriteria;
import com.adwiiseagent.service.dto.PlatformBillingPlanDTO;
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
public class PlatformBillingPlanQueryService {

    private final PlatformBillingPlanRepository repository;
    private final PlatformBillingPlanService platformBillingPlanService;

    public PlatformBillingPlanQueryService(
        PlatformBillingPlanRepository repository,
        PlatformBillingPlanService platformBillingPlanService
    ) {
        this.repository = repository;
        this.platformBillingPlanService = platformBillingPlanService;
    }

    public Page<PlatformBillingPlanDTO> findByCriteria(PlatformBillingPlanCriteria criteria, Pageable page) {
        return repository.findAll(specification(criteria), page).map(platformBillingPlanService::toDto);
    }

    public long countByCriteria(PlatformBillingPlanCriteria criteria) {
        return repository.count(specification(criteria));
    }

    private Specification<PlatformBillingPlan> specification(PlatformBillingPlanCriteria criteria) {
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
                if (criteria.getCode() != null && criteria.getCode().getEquals() != null) {
                    predicates.add(cb.equal(root.get("code"), criteria.getCode().getEquals()));
                }
                if (criteria.getPublished() != null && criteria.getPublished().getEquals() != null) {
                    predicates.add(cb.equal(root.get("published"), criteria.getPublished().getEquals()));
                }
            }
            return cb.and(predicates.toArray(Predicate[]::new));
        };
    }
}
