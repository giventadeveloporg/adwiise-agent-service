package com.eventsitemanager.service;

import com.eventsitemanager.domain.RetailRecommendation;
import com.eventsitemanager.domain.RetailRecommendation_;
import com.eventsitemanager.repository.RetailRecommendationRepository;
import com.eventsitemanager.service.criteria.RetailRecommendationCriteria;
import com.eventsitemanager.service.dto.RetailRecommendationDTO;
import com.eventsitemanager.service.mapper.RetailRecommendationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

@Service
@Transactional(readOnly = true)
public class RetailRecommendationQueryService extends QueryService<RetailRecommendation> {

    private static final Logger LOG = LoggerFactory.getLogger(RetailRecommendationQueryService.class);

    private final RetailRecommendationRepository retailRecommendationRepository;
    private final RetailRecommendationMapper retailRecommendationMapper;
    private final RetailAccessService retailAccessService;

    public RetailRecommendationQueryService(
        RetailRecommendationRepository retailRecommendationRepository,
        RetailRecommendationMapper retailRecommendationMapper,
        RetailAccessService retailAccessService
    ) {
        this.retailRecommendationRepository = retailRecommendationRepository;
        this.retailRecommendationMapper = retailRecommendationMapper;
        this.retailAccessService = retailAccessService;
    }

    public Page<RetailRecommendationDTO> findByCriteria(RetailRecommendationCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        if (criteria == null) {
            criteria = new RetailRecommendationCriteria();
        }
        applyAccess(criteria);
        Specification<RetailRecommendation> specification = createSpecification(criteria);
        specification = specification.and(retailAccessService.recommendationSpecification());
        return retailRecommendationRepository.findAll(specification, page).map(retailRecommendationMapper::toDto);
    }

    public long countByCriteria(RetailRecommendationCriteria criteria) {
        if (criteria == null) {
            criteria = new RetailRecommendationCriteria();
        }
        applyAccess(criteria);
        Specification<RetailRecommendation> specification = createSpecification(criteria);
        specification = specification.and(retailAccessService.recommendationSpecification());
        return retailRecommendationRepository.count(specification);
    }

    private void applyAccess(RetailRecommendationCriteria criteria) {
        retailAccessService.assertModuleAccess();
        if (retailAccessService.isUnrestrictedServiceAccess()) {
            return;
        }
    }

    protected Specification<RetailRecommendation> createSpecification(RetailRecommendationCriteria criteria) {
        Specification<RetailRecommendation> specification = Specification.where(null);
        if (criteria != null) {
            specification =
                Specification.allOf(
                    Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                    criteria.getId() != null ? buildRangeSpecification(criteria.getId(), RetailRecommendation_.id) : null,
                    criteria.getTenantId() != null
                        ? buildStringSpecification(criteria.getTenantId(), RetailRecommendation_.tenantId)
                        : null,
                    criteria.getLocationId() != null
                        ? buildRangeSpecification(criteria.getLocationId(), RetailRecommendation_.locationId)
                        : null,
                    criteria.getRecommendationDate() != null
                        ? buildRangeSpecification(criteria.getRecommendationDate(), RetailRecommendation_.recommendationDate)
                        : null,
                    criteria.getCategory() != null
                        ? buildStringSpecification(criteria.getCategory(), RetailRecommendation_.category)
                        : null,
                    criteria.getStatus() != null ? buildStringSpecification(criteria.getStatus(), RetailRecommendation_.status) : null,
                    criteria.getSourceModelRunId() != null
                        ? buildStringSpecification(criteria.getSourceModelRunId(), RetailRecommendation_.sourceModelRunId)
                        : null,
                    null
                );
        }
        return specification;
    }
}
