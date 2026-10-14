package com.adwiiseagent.service;

import com.adwiiseagent.domain.RetailLocation;
import com.adwiiseagent.domain.RetailLocation_;
import com.adwiiseagent.repository.RetailLocationRepository;
import com.adwiiseagent.service.criteria.RetailLocationCriteria;
import com.adwiiseagent.service.dto.RetailLocationDTO;
import com.adwiiseagent.service.mapper.RetailLocationMapper;
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
public class RetailLocationQueryService extends QueryService<RetailLocation> {

    private static final Logger LOG = LoggerFactory.getLogger(RetailLocationQueryService.class);

    private final RetailLocationRepository retailLocationRepository;
    private final RetailLocationMapper retailLocationMapper;
    private final RetailAccessService retailAccessService;

    public RetailLocationQueryService(
        RetailLocationRepository retailLocationRepository,
        RetailLocationMapper retailLocationMapper,
        RetailAccessService retailAccessService
    ) {
        this.retailLocationRepository = retailLocationRepository;
        this.retailLocationMapper = retailLocationMapper;
        this.retailAccessService = retailAccessService;
    }

    public Page<RetailLocationDTO> findByCriteria(RetailLocationCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        if (criteria == null) {
            criteria = new RetailLocationCriteria();
        }
        applyAccess(criteria);
        Specification<RetailLocation> specification = createSpecification(criteria);
        return retailLocationRepository.findAll(specification, page).map(retailLocationMapper::toDto);
    }

    public long countByCriteria(RetailLocationCriteria criteria) {
        if (criteria == null) {
            criteria = new RetailLocationCriteria();
        }
        applyAccess(criteria);
        Specification<RetailLocation> specification = createSpecification(criteria);
        return retailLocationRepository.count(specification);
    }

    private void applyAccess(RetailLocationCriteria criteria) {
        retailAccessService.assertModuleAccess();
        if (retailAccessService.isUnrestrictedServiceAccess()) {
            return;
        }

        Set<Long> allowed = retailAccessService.getAllowedLocationIdsOrNull();
        if (allowed == null) {
            return;
        }
        LongFilter filter = criteria.getId();
        if (filter == null) {
            filter = new LongFilter();
            criteria.setId(filter);
        }
        retailAccessService.restrict(filter, allowed);
    }

    protected Specification<RetailLocation> createSpecification(RetailLocationCriteria criteria) {
        Specification<RetailLocation> specification = Specification.where(null);
        if (criteria != null) {
            specification =
                Specification.allOf(
                    Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                    criteria.getId() != null ? buildRangeSpecification(criteria.getId(), RetailLocation_.id) : null,
                    criteria.getTenantId() != null ? buildStringSpecification(criteria.getTenantId(), RetailLocation_.tenantId) : null,
                    criteria.getLocationName() != null
                        ? buildStringSpecification(criteria.getLocationName(), RetailLocation_.locationName)
                        : null,
                    criteria.getLocationCode() != null
                        ? buildStringSpecification(criteria.getLocationCode(), RetailLocation_.locationCode)
                        : null,
                    criteria.getRegion() != null ? buildStringSpecification(criteria.getRegion(), RetailLocation_.region) : null,
                    criteria.getCity() != null ? buildStringSpecification(criteria.getCity(), RetailLocation_.city) : null,
                    criteria.getIsActive() != null ? buildSpecification(criteria.getIsActive(), RetailLocation_.isActive) : null,
                    null
                );
        }
        return specification;
    }
}
