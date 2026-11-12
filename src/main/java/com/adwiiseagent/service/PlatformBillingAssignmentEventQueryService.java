package com.adwiiseagent.service;

import com.adwiiseagent.domain.PlatformBillingAssignmentEvent;
import com.adwiiseagent.repository.PlatformBillingAssignmentEventRepository;
import com.adwiiseagent.service.criteria.PlatformBillingAssignmentEventCriteria;
import com.adwiiseagent.service.dto.PlatformBillingAssignmentEventDTO;
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
public class PlatformBillingAssignmentEventQueryService {

    private final PlatformBillingAssignmentEventRepository repository;
    private final PlatformBillingAssignmentEventService service;

    public PlatformBillingAssignmentEventQueryService(
        PlatformBillingAssignmentEventRepository repository,
        PlatformBillingAssignmentEventService service
    ) {
        this.repository = repository;
        this.service = service;
    }

    public Page<PlatformBillingAssignmentEventDTO> findByCriteria(PlatformBillingAssignmentEventCriteria criteria, Pageable page) {
        return repository.findAll(specification(criteria), page).map(service::toDto);
    }

    private Specification<PlatformBillingAssignmentEvent> specification(PlatformBillingAssignmentEventCriteria criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (criteria == null) return cb.and(predicates.toArray(Predicate[]::new));
            if (criteria.getId() != null && criteria.getId().getEquals() != null) {
                predicates.add(cb.equal(root.get("id"), criteria.getId().getEquals()));
            }
            if (criteria.getOrganizationId() != null && criteria.getOrganizationId().getEquals() != null) {
                predicates.add(cb.equal(root.get("organizationId"), criteria.getOrganizationId().getEquals()));
            }
            if (criteria.getTenantId() != null && criteria.getTenantId().getEquals() != null) {
                predicates.add(cb.equal(root.get("tenantId"), criteria.getTenantId().getEquals()));
            }
            if (criteria.getProduct() != null && criteria.getProduct().getEquals() != null) {
                predicates.add(cb.equal(root.get("product"), criteria.getProduct().getEquals()));
            }
            if (criteria.getOccurredAt() != null && criteria.getOccurredAt().getGreaterThanOrEqual() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("occurredAt"), criteria.getOccurredAt().getGreaterThanOrEqual()));
            }
            if (criteria.getOccurredAt() != null && criteria.getOccurredAt().getLessThanOrEqual() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("occurredAt"), criteria.getOccurredAt().getLessThanOrEqual()));
            }
            return cb.and(predicates.toArray(Predicate[]::new));
        };
    }
}
