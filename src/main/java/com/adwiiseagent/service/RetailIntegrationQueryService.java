package com.adwiiseagent.service;

import com.adwiiseagent.domain.RetailIntegration;
import com.adwiiseagent.domain.RetailIntegration_;
import com.adwiiseagent.repository.RetailIntegrationRepository;
import com.adwiiseagent.service.criteria.RetailIntegrationCriteria;
import com.adwiiseagent.service.dto.RetailIntegrationDTO;
import com.adwiiseagent.service.mapper.RetailIntegrationMapper;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;
import tech.jhipster.service.filter.LongFilter;

@Service
@Transactional(readOnly = true)
public class RetailIntegrationQueryService extends QueryService<RetailIntegration> {

    private static final Logger LOG = LoggerFactory.getLogger(RetailIntegrationQueryService.class);

    private final RetailIntegrationRepository retailIntegrationRepository;
    private final RetailIntegrationMapper retailIntegrationMapper;
    private final RetailAccessService retailAccessService;

    public RetailIntegrationQueryService(
        RetailIntegrationRepository retailIntegrationRepository,
        RetailIntegrationMapper retailIntegrationMapper,
        RetailAccessService retailAccessService
    ) {
        this.retailIntegrationRepository = retailIntegrationRepository;
        this.retailIntegrationMapper = retailIntegrationMapper;
        this.retailAccessService = retailAccessService;
    }

    public Page<RetailIntegrationDTO> findByCriteria(RetailIntegrationCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        if (criteria == null) {
            criteria = new RetailIntegrationCriteria();
        }
        applyAccess(criteria);
        Specification<RetailIntegration> specification = createSpecification(criteria);
        return retailIntegrationRepository.findAll(specification, page).map(retailIntegrationMapper::toDto);
    }

    public long countByCriteria(RetailIntegrationCriteria criteria) {
        if (criteria == null) {
            criteria = new RetailIntegrationCriteria();
        }
        applyAccess(criteria);
        Specification<RetailIntegration> specification = createSpecification(criteria);
        return retailIntegrationRepository.count(specification);
    }

    private void applyAccess(RetailIntegrationCriteria criteria) {
        retailAccessService.assertModuleAccess();
        if (retailAccessService.isUnrestrictedServiceAccess()) {
            return;
        }

        Set<Long> allowed = retailAccessService.getAllowedLocationIdsOrNull();
        if (allowed == null) {
            return;
        }
        LongFilter filter = criteria.getLocationId();
        if (filter == null) {
            filter = new LongFilter();
            criteria.setLocationId(filter);
        }
        retailAccessService.restrict(filter, allowed);
    }

    protected Specification<RetailIntegration> createSpecification(RetailIntegrationCriteria criteria) {
        Specification<RetailIntegration> specification = Specification.where(null);
        if (criteria != null) {
            specification =
                Specification.allOf(
                    Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                    criteria.getId() != null ? buildRangeSpecification(criteria.getId(), RetailIntegration_.id) : null,
                    criteria.getTenantId() != null ? buildStringSpecification(criteria.getTenantId(), RetailIntegration_.tenantId) : null,
                    criteria.getLocationId() != null
                        ? buildRangeSpecification(criteria.getLocationId(), RetailIntegration_.locationId)
                        : null,
                    criteria.getSystemType() != null
                        ? buildStringSpecification(criteria.getSystemType(), RetailIntegration_.systemType)
                        : null,
                    criteria.getIsEnabled() != null ? buildSpecification(criteria.getIsEnabled(), RetailIntegration_.isEnabled) : null,
                    null
                );
        }
        return specification;
    }
}
