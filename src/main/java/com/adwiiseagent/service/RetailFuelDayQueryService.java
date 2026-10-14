package com.adwiiseagent.service;

import com.adwiiseagent.domain.RetailFuelDay;
import com.adwiiseagent.domain.RetailFuelDay_;
import com.adwiiseagent.repository.RetailFuelDayRepository;
import com.adwiiseagent.service.criteria.RetailFuelDayCriteria;
import com.adwiiseagent.service.dto.RetailFuelDayDTO;
import com.adwiiseagent.service.mapper.RetailFuelDayMapper;
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
public class RetailFuelDayQueryService extends QueryService<RetailFuelDay> {

    private static final Logger LOG = LoggerFactory.getLogger(RetailFuelDayQueryService.class);

    private final RetailFuelDayRepository retailFuelDayRepository;
    private final RetailFuelDayMapper retailFuelDayMapper;
    private final RetailAccessService retailAccessService;

    public RetailFuelDayQueryService(
        RetailFuelDayRepository retailFuelDayRepository,
        RetailFuelDayMapper retailFuelDayMapper,
        RetailAccessService retailAccessService
    ) {
        this.retailFuelDayRepository = retailFuelDayRepository;
        this.retailFuelDayMapper = retailFuelDayMapper;
        this.retailAccessService = retailAccessService;
    }

    public Page<RetailFuelDayDTO> findByCriteria(RetailFuelDayCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        if (criteria == null) {
            criteria = new RetailFuelDayCriteria();
        }
        applyAccess(criteria);
        Specification<RetailFuelDay> specification = createSpecification(criteria);
        return retailFuelDayRepository.findAll(specification, page).map(retailFuelDayMapper::toDto);
    }

    public long countByCriteria(RetailFuelDayCriteria criteria) {
        if (criteria == null) {
            criteria = new RetailFuelDayCriteria();
        }
        applyAccess(criteria);
        Specification<RetailFuelDay> specification = createSpecification(criteria);
        return retailFuelDayRepository.count(specification);
    }

    private void applyAccess(RetailFuelDayCriteria criteria) {
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

    protected Specification<RetailFuelDay> createSpecification(RetailFuelDayCriteria criteria) {
        Specification<RetailFuelDay> specification = Specification.where(null);
        if (criteria != null) {
            specification =
                Specification.allOf(
                    Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                    criteria.getId() != null ? buildRangeSpecification(criteria.getId(), RetailFuelDay_.id) : null,
                    criteria.getTenantId() != null ? buildStringSpecification(criteria.getTenantId(), RetailFuelDay_.tenantId) : null,
                    criteria.getLocationId() != null ? buildRangeSpecification(criteria.getLocationId(), RetailFuelDay_.locationId) : null,
                    criteria.getMetricDate() != null ? buildRangeSpecification(criteria.getMetricDate(), RetailFuelDay_.metricDate) : null,
                    null
                );
        }
        return specification;
    }
}
