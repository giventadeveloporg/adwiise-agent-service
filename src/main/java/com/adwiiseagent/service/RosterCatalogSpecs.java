package com.adwiiseagent.service;

import com.adwiiseagent.service.criteria.RosterCatalogCriteria;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import tech.jhipster.service.filter.StringFilter;

/**
 * Global catalog rows ({@code tenant_id} null) stay visible beside the caller tenant's private rows.
 */
public final class RosterCatalogSpecs {

    private RosterCatalogSpecs() {}

    public static <T> Specification<T> visible(String tenantId, RosterCatalogCriteria criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (tenantId == null || tenantId.isBlank()) {
                predicates.add(cb.isNull(root.get("tenantId")));
            } else {
                predicates.add(cb.or(cb.isNull(root.get("tenantId")), cb.equal(root.get("tenantId"), tenantId)));
            }
            if (criteria != null) {
                if (criteria.getId() != null && criteria.getId().getEquals() != null) {
                    predicates.add(cb.equal(root.get("id"), criteria.getId().getEquals()));
                }
                addString(predicates, cb, root.get("code"), criteria.getCode());
                addString(predicates, cb, root.get("label"), criteria.getLabel());
                if (criteria.getIsActive() != null && criteria.getIsActive().getEquals() != null) {
                    predicates.add(cb.equal(root.get("isActive"), criteria.getIsActive().getEquals()));
                }
                if (criteria.getSortOrder() != null && criteria.getSortOrder().getEquals() != null) {
                    predicates.add(cb.equal(root.get("sortOrder"), criteria.getSortOrder().getEquals()));
                }
            }
            return cb.and(predicates.toArray(Predicate[]::new));
        };
    }

    private static void addString(List<Predicate> predicates, CriteriaBuilder cb, Path<String> path, StringFilter filter) {
        if (filter == null) {
            return;
        }
        if (filter.getEquals() != null) {
            predicates.add(cb.equal(path, filter.getEquals()));
        } else if (filter.getContains() != null && !filter.getContains().isBlank()) {
            predicates.add(cb.like(cb.lower(path), "%" + filter.getContains().toLowerCase() + "%"));
        }
    }
}
