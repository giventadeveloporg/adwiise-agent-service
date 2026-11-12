package com.adwiiseagent.service;

import com.adwiiseagent.domain.PlatformBillingCharge;
import com.adwiiseagent.repository.PlatformBillingChargeRepository;
import com.adwiiseagent.service.criteria.PlatformBillingChargeCriteria;
import com.adwiiseagent.service.dto.PlatformBillingChargeDTO;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.filter.StringFilter;
import tech.jhipster.service.filter.ZonedDateTimeFilter;

@Service
@Transactional(readOnly = true)
public class PlatformBillingChargeQueryService {

    private final PlatformBillingChargeRepository repository;
    private final PlatformBillingChargeService service;

    public PlatformBillingChargeQueryService(PlatformBillingChargeRepository repository, PlatformBillingChargeService service) {
        this.repository = repository;
        this.service = service;
    }

    public Page<PlatformBillingChargeDTO> findByCriteria(PlatformBillingChargeCriteria criteria, Pageable page) {
        return repository.findAll(specification(criteria), page).map(service::toDto);
    }

    private Specification<PlatformBillingCharge> specification(PlatformBillingChargeCriteria criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (criteria == null) {
                return cb.and(predicates.toArray(Predicate[]::new));
            }
            if (criteria.getId() != null && criteria.getId().getEquals() != null) {
                predicates.add(cb.equal(root.get("id"), criteria.getId().getEquals()));
            }
            if (criteria.getOrganizationId() != null && criteria.getOrganizationId().getEquals() != null) {
                predicates.add(cb.equal(root.get("organizationId"), criteria.getOrganizationId().getEquals()));
            }
            addString(predicates, cb, root.get("tenantId"), criteria.getTenantId());
            addString(predicates, cb, root.get("product"), criteria.getProduct());
            addString(predicates, cb, root.get("billingScope"), criteria.getBillingScope());
            addString(predicates, cb, root.get("status"), criteria.getStatus());
            addString(predicates, cb, root.get("stripeSubscriptionId"), criteria.getStripeSubscriptionId());
            addString(predicates, cb, root.get("stripeInvoiceId"), criteria.getStripeInvoiceId());
            addString(predicates, cb, root.get("stripeEventId"), criteria.getStripeEventId());
            addOccurred(predicates, cb, root.get("occurredAt"), criteria.getOccurredAt());
            return cb.and(predicates.toArray(Predicate[]::new));
        };
    }

    private void addString(
        List<Predicate> predicates,
        jakarta.persistence.criteria.CriteriaBuilder cb,
        jakarta.persistence.criteria.Path<String> path,
        StringFilter filter
    ) {
        if (filter == null) return;
        if (filter.getEquals() != null) predicates.add(cb.equal(path, filter.getEquals()));
        if (filter.getContains() != null) predicates.add(cb.like(cb.lower(path), "%" + filter.getContains().toLowerCase() + "%"));
    }

    private void addOccurred(
        List<Predicate> predicates,
        jakarta.persistence.criteria.CriteriaBuilder cb,
        jakarta.persistence.criteria.Path<java.time.ZonedDateTime> path,
        ZonedDateTimeFilter filter
    ) {
        if (filter == null) return;
        if (filter.getGreaterThanOrEqual() != null) predicates.add(cb.greaterThanOrEqualTo(path, filter.getGreaterThanOrEqual()));
        if (filter.getLessThanOrEqual() != null) predicates.add(cb.lessThanOrEqualTo(path, filter.getLessThanOrEqual()));
    }
}
