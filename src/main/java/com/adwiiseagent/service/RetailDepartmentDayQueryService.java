package com.adwiiseagent.service;

import com.adwiiseagent.domain.RetailDepartmentDay;
import com.adwiiseagent.domain.RetailDepartmentDay_;
import com.adwiiseagent.repository.RetailDepartmentDayRepository;
import com.adwiiseagent.service.criteria.RetailDepartmentDayCriteria;
import com.adwiiseagent.service.dto.RetailDepartmentDayDTO;
import com.adwiiseagent.service.mapper.RetailDepartmentDayMapper;
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
public class RetailDepartmentDayQueryService extends QueryService<RetailDepartmentDay> {

    private static final Logger LOG = LoggerFactory.getLogger(RetailDepartmentDayQueryService.class);

    private final RetailDepartmentDayRepository retailDepartmentDayRepository;
    private final RetailDepartmentDayMapper retailDepartmentDayMapper;
    private final RetailAccessService retailAccessService;

    public RetailDepartmentDayQueryService(
        RetailDepartmentDayRepository retailDepartmentDayRepository,
        RetailDepartmentDayMapper retailDepartmentDayMapper,
        RetailAccessService retailAccessService
    ) {
        this.retailDepartmentDayRepository = retailDepartmentDayRepository;
        this.retailDepartmentDayMapper = retailDepartmentDayMapper;
        this.retailAccessService = retailAccessService;
    }

    public Page<RetailDepartmentDayDTO> findByCriteria(RetailDepartmentDayCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        if (criteria == null) {
            criteria = new RetailDepartmentDayCriteria();
        }
        applyAccess(criteria);
        Specification<RetailDepartmentDay> specification = createSpecification(criteria);
        return retailDepartmentDayRepository.findAll(specification, page).map(retailDepartmentDayMapper::toDto);
    }

    public long countByCriteria(RetailDepartmentDayCriteria criteria) {
        if (criteria == null) {
            criteria = new RetailDepartmentDayCriteria();
        }
        applyAccess(criteria);
        Specification<RetailDepartmentDay> specification = createSpecification(criteria);
        return retailDepartmentDayRepository.count(specification);
    }

    private void applyAccess(RetailDepartmentDayCriteria criteria) {
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

    protected Specification<RetailDepartmentDay> createSpecification(RetailDepartmentDayCriteria criteria) {
        Specification<RetailDepartmentDay> specification = Specification.where(null);
        if (criteria != null) {
            specification =
                Specification.allOf(
                    Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                    criteria.getId() != null ? buildRangeSpecification(criteria.getId(), RetailDepartmentDay_.id) : null,
                    criteria.getTenantId() != null ? buildStringSpecification(criteria.getTenantId(), RetailDepartmentDay_.tenantId) : null,
                    criteria.getLocationId() != null
                        ? buildRangeSpecification(criteria.getLocationId(), RetailDepartmentDay_.locationId)
                        : null,
                    criteria.getMetricDate() != null
                        ? buildRangeSpecification(criteria.getMetricDate(), RetailDepartmentDay_.metricDate)
                        : null,
                    criteria.getDepartment() != null
                        ? buildStringSpecification(criteria.getDepartment(), RetailDepartmentDay_.department)
                        : null,
                    null
                );
        }
        return specification;
    }
}
