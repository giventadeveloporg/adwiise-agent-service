package com.adwiiseagent.service;

import com.adwiiseagent.domain.PlatformBillingCharge;
import com.adwiiseagent.repository.PlatformBillingChargeRepository;
import com.adwiiseagent.service.criteria.PlatformBillingChargeCriteria;
import com.adwiiseagent.service.dto.PlatformBillingChargeDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
    private final EntityManager entityManager;

    public PlatformBillingChargeQueryService(
        PlatformBillingChargeRepository repository,
        PlatformBillingChargeService service,
        EntityManager entityManager
    ) {
        this.repository = repository;
        this.service = service;
        this.entityManager = entityManager;
    }

    public Page<PlatformBillingChargeDTO> findByCriteria(PlatformBillingChargeCriteria criteria, Pageable page) {
        return repository.findAll(specification(criteria), page).map(service::toDto);
    }

    /** Count and dollar sum of the matching charges, one entry per status. */
    public List<Map<String, Object>> totalsByStatus(PlatformBillingChargeCriteria criteria) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = cb.createQuery(Object[].class);
        Root<PlatformBillingCharge> root = query.from(PlatformBillingCharge.class);
        query.multiselect(root.get("status"), cb.count(root), cb.sum(root.<BigDecimal>get("amountUsd")));
        Predicate where = specification(criteria).toPredicate(root, query, cb);
        if (where != null) query.where(where);
        query.groupBy(root.get("status"));
        List<Map<String, Object>> totals = new ArrayList<>();
        for (Object[] row : entityManager.createQuery(query).getResultList()) {
            Map<String, Object> entry = new LinkedHashMap<>();
            entry.put("status", row[0]);
            entry.put("count", row[1]);
            entry.put("amountUsd", row[2] == null ? BigDecimal.ZERO : row[2]);
            totals.add(entry);
        }
        return totals;
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
            if (criteria.getOrganizationId() != null && criteria.getOrganizationId().getIn() != null) {
                predicates.add(root.get("organizationId").in(criteria.getOrganizationId().getIn()));
            }
            addString(predicates, cb, root.get("tenantId"), criteria.getTenantId());
            addString(predicates, cb, root.get("product"), criteria.getProduct());
            addString(predicates, cb, root.get("billingScope"), criteria.getBillingScope());
            addString(predicates, cb, root.get("status"), criteria.getStatus());
            addString(predicates, cb, root.get("stripeSubscriptionId"), criteria.getStripeSubscriptionId());
            addString(predicates, cb, root.get("stripeInvoiceId"), criteria.getStripeInvoiceId());
            addString(predicates, cb, root.get("stripeEventId"), criteria.getStripeEventId());
            addOccurred(predicates, cb, root.get("occurredAt"), criteria.getOccurredAt());
            addString(predicates, cb, root.get("stripePaymentIntentId"), criteria.getStripePaymentIntentId());
            addString(predicates, cb, root.get("planCode"), criteria.getPlanCode());
            addOccurred(predicates, cb, root.get("periodEnd"), criteria.getPeriodEnd());
            if (criteria.getPlatformPlanId() != null && criteria.getPlatformPlanId().getEquals() != null) {
                predicates.add(cb.equal(root.get("platformPlanId"), criteria.getPlatformPlanId().getEquals()));
            }
            if (criteria.getAmountUsd() != null) {
                Path<BigDecimal> amount = root.get("amountUsd");
                if (criteria.getAmountUsd().getEquals() != null) predicates.add(cb.equal(amount, criteria.getAmountUsd().getEquals()));
                if (criteria.getAmountUsd().getGreaterThanOrEqual() != null) {
                    predicates.add(cb.greaterThanOrEqualTo(amount, criteria.getAmountUsd().getGreaterThanOrEqual()));
                }
                if (criteria.getAmountUsd().getLessThanOrEqual() != null) {
                    predicates.add(cb.lessThanOrEqualTo(amount, criteria.getAmountUsd().getLessThanOrEqual()));
                }
            }
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
        if (filter.getIn() != null && !filter.getIn().isEmpty()) predicates.add(path.in(filter.getIn()));
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
