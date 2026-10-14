package com.adwiiseagent.service;

import com.adwiiseagent.domain.RetailRecommendation;
import java.util.Set;
import org.springframework.data.jpa.domain.Specification;
import tech.jhipster.service.filter.LongFilter;

public interface RetailAccessService {
    boolean isUnrestrictedServiceAccess();
    void assertModuleAccess();
    void assertAllLocationsScope();
    void assertLocationIdAllowed(Long locationId);
    void assertRecommendationAccess(Long locationId);
    Set<Long> getAllowedLocationIdsOrNull();
    void restrict(LongFilter filter, Set<Long> allowed);
    Specification<RetailRecommendation> recommendationSpecification();
}
