package com.eventsitemanager.service;

import com.eventsitemanager.domain.RetailUserLocationAssignment;
import com.eventsitemanager.domain.RetailUserLocationAssignment_;
import com.eventsitemanager.repository.RetailUserLocationAssignmentRepository;
import com.eventsitemanager.service.criteria.RetailUserLocationAssignmentCriteria;
import com.eventsitemanager.service.dto.RetailUserLocationAssignmentDTO;
import com.eventsitemanager.service.mapper.RetailUserLocationAssignmentMapper;
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
public class RetailUserLocationAssignmentQueryService extends QueryService<RetailUserLocationAssignment> {

    private static final Logger LOG = LoggerFactory.getLogger(RetailUserLocationAssignmentQueryService.class);

    private final RetailUserLocationAssignmentRepository retailUserLocationAssignmentRepository;
    private final RetailUserLocationAssignmentMapper retailUserLocationAssignmentMapper;
    private final RetailAccessService retailAccessService;

    public RetailUserLocationAssignmentQueryService(
        RetailUserLocationAssignmentRepository retailUserLocationAssignmentRepository,
        RetailUserLocationAssignmentMapper retailUserLocationAssignmentMapper,
        RetailAccessService retailAccessService
    ) {
        this.retailUserLocationAssignmentRepository = retailUserLocationAssignmentRepository;
        this.retailUserLocationAssignmentMapper = retailUserLocationAssignmentMapper;
        this.retailAccessService = retailAccessService;
    }

    public Page<RetailUserLocationAssignmentDTO> findByCriteria(RetailUserLocationAssignmentCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        if (criteria == null) {
            criteria = new RetailUserLocationAssignmentCriteria();
        }
        applyAccess(criteria);
        Specification<RetailUserLocationAssignment> specification = createSpecification(criteria);
        return retailUserLocationAssignmentRepository.findAll(specification, page).map(retailUserLocationAssignmentMapper::toDto);
    }

    public long countByCriteria(RetailUserLocationAssignmentCriteria criteria) {
        if (criteria == null) {
            criteria = new RetailUserLocationAssignmentCriteria();
        }
        applyAccess(criteria);
        Specification<RetailUserLocationAssignment> specification = createSpecification(criteria);
        return retailUserLocationAssignmentRepository.count(specification);
    }

    private void applyAccess(RetailUserLocationAssignmentCriteria criteria) {
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

    protected Specification<RetailUserLocationAssignment> createSpecification(RetailUserLocationAssignmentCriteria criteria) {
        Specification<RetailUserLocationAssignment> specification = Specification.where(null);
        if (criteria != null) {
            specification =
                Specification.allOf(
                    Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                    criteria.getId() != null ? buildRangeSpecification(criteria.getId(), RetailUserLocationAssignment_.id) : null,
                    criteria.getTenantId() != null
                        ? buildStringSpecification(criteria.getTenantId(), RetailUserLocationAssignment_.tenantId)
                        : null,
                    criteria.getUserProfileId() != null
                        ? buildRangeSpecification(criteria.getUserProfileId(), RetailUserLocationAssignment_.userProfileId)
                        : null,
                    criteria.getLocationId() != null
                        ? buildRangeSpecification(criteria.getLocationId(), RetailUserLocationAssignment_.locationId)
                        : null,
                    null
                );
        }
        return specification;
    }
}
