package com.adwiiseagent.service.criteria;

import org.springframework.data.jpa.domain.Specification;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria filters that use string attribute names so new entities do not depend on generated metamodels.
 */
public final class AttributeSpecifications {

    private AttributeSpecifications() {}

    public static <T> Specification<T> longEquals(String attribute, LongFilter filter) {
        if (filter == null) {
            return null;
        }
        return (root, query, cb) -> {
            if (filter.getEquals() != null) {
                return cb.equal(root.get(attribute), filter.getEquals());
            }
            if (filter.getIn() != null && !filter.getIn().isEmpty()) {
                return root.get(attribute).in(filter.getIn());
            }
            if (filter.getSpecified() != null) {
                return Boolean.TRUE.equals(filter.getSpecified()) ? cb.isNotNull(root.get(attribute)) : cb.isNull(root.get(attribute));
            }
            return cb.conjunction();
        };
    }

    public static <T> Specification<T> integerEquals(String attribute, IntegerFilter filter) {
        if (filter == null) {
            return null;
        }
        return (root, query, cb) -> {
            if (filter.getEquals() != null) {
                return cb.equal(root.get(attribute), filter.getEquals());
            }
            if (filter.getSpecified() != null) {
                return Boolean.TRUE.equals(filter.getSpecified()) ? cb.isNotNull(root.get(attribute)) : cb.isNull(root.get(attribute));
            }
            return cb.conjunction();
        };
    }

    public static <T> Specification<T> stringMatch(String attribute, StringFilter filter) {
        if (filter == null) {
            return null;
        }
        return (root, query, cb) -> {
            if (filter.getEquals() != null) {
                return cb.equal(root.get(attribute), filter.getEquals());
            }
            if (filter.getContains() != null) {
                return cb.like(cb.lower(root.get(attribute)), "%" + filter.getContains().toLowerCase() + "%");
            }
            if (filter.getIn() != null && !filter.getIn().isEmpty()) {
                return root.get(attribute).in(filter.getIn());
            }
            if (filter.getSpecified() != null) {
                return Boolean.TRUE.equals(filter.getSpecified()) ? cb.isNotNull(root.get(attribute)) : cb.isNull(root.get(attribute));
            }
            return cb.conjunction();
        };
    }

    public static <T> Specification<T> booleanEquals(String attribute, BooleanFilter filter) {
        if (filter == null) {
            return null;
        }
        return (root, query, cb) -> {
            if (filter.getEquals() != null) {
                return cb.equal(root.get(attribute), filter.getEquals());
            }
            if (filter.getSpecified() != null) {
                return Boolean.TRUE.equals(filter.getSpecified()) ? cb.isNotNull(root.get(attribute)) : cb.isNull(root.get(attribute));
            }
            return cb.conjunction();
        };
    }
}
