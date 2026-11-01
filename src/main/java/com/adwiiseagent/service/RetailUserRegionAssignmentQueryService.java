package com.adwiiseagent.service;

import com.adwiiseagent.domain.RetailUserRegionAssignment;
import com.adwiiseagent.domain.RetailUserRegionAssignment_;
import com.adwiiseagent.repository.RetailUserRegionAssignmentRepository;
import com.adwiiseagent.service.criteria.RetailUserRegionAssignmentCriteria;
import com.adwiiseagent.service.dto.RetailUserRegionAssignmentDTO;
import com.adwiiseagent.service.mapper.RetailUserRegionAssignmentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;
import tech.jhipster.service.filter.LongFilter;

@Service
@Transactional(readOnly = true)
public class RetailUserRegionAssignmentQueryService extends QueryService<RetailUserRegionAssignment> {

    private static final Logger LOG = LoggerFactory.getLogger(RetailUserRegionAssignmentQueryService.class);

    private final RetailUserRegionAssignmentRepository retailUserRegionAssignmentRepository;
    private final RetailUserRegionAssignmentMapper retailUserRegionAssignmentMapper;
    private final RetailAccessService retailAccessService;

    public RetailUserRegionAssignmentQueryService(
        RetailUserRegionAssignmentRepository retailUserRegionAssignmentRepository,
        RetailUserRegionAssignmentMapper retailUserRegionAssignmentMapper,
        RetailAccessService retailAccessService
    ) {
        this.retailUserRegionAssignmentRepository = retailUserRegionAssignmentRepository;
        this.retailUserRegionAssignmentMapper = retailUserRegionAssignmentMapper;
        this.retailAccessService = retailAccessService;
    }

    public Page<RetailUserRegionAssignmentDTO> findByCriteria(RetailUserRegionAssignmentCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        if (criteria == null) {
            criteria = new RetailUserRegionAssignmentCriteria();
        }
        applyAccess(criteria);
        Specification<RetailUserRegionAssignment> specification = createSpecification(criteria);
        return retailUserRegionAssignmentRepository.findAll(specification, page).map(retailUserRegionAssignmentMapper::toDto);
    }

    public long countByCriteria(RetailUserRegionAssignmentCriteria criteria) {
        if (criteria == null) {
            criteria = new RetailUserRegionAssignmentCriteria();
        }
        applyAccess(criteria);
        Specification<RetailUserRegionAssignment> specification = createSpecification(criteria);
        return retailUserRegionAssignmentRepository.count(specification);
    }

    private void applyAccess(RetailUserRegionAssignmentCriteria criteria) {
        retailAccessService.assertModuleAccess();
        if (retailAccessService.isUnrestrictedServiceAccess() || retailAccessService.getAllowedLocationIdsOrNull() == null) {
            return;
        }
        Long self = retailAccessService.currentUserProfileIdOrNull();
        if (self == null) {
            throw new AccessDeniedException("Access denied for region assignments.");
        }
        LongFilter filter = criteria.getUserProfileId();
        if (filter != null && filter.getEquals() != null && !self.equals(filter.getEquals())) {
            throw new AccessDeniedException("Access denied for another person's region assignments.");
        }
        if (filter == null) {
            filter = new LongFilter();
            criteria.setUserProfileId(filter);
        }
        filter.setEquals(self);
    }

    protected Specification<RetailUserRegionAssignment> createSpecification(RetailUserRegionAssignmentCriteria criteria) {
        Specification<RetailUserRegionAssignment> specification = Specification.where(null);
        if (criteria != null) {
            specification =
                Specification.allOf(
                    Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                    criteria.getId() != null ? buildRangeSpecification(criteria.getId(), RetailUserRegionAssignment_.id) : null,
                    criteria.getTenantId() != null
                        ? buildStringSpecification(criteria.getTenantId(), RetailUserRegionAssignment_.tenantId)
                        : null,
                    criteria.getUserProfileId() != null
                        ? buildRangeSpecification(criteria.getUserProfileId(), RetailUserRegionAssignment_.userProfileId)
                        : null,
                    criteria.getRegion() != null ? buildStringSpecification(criteria.getRegion(), RetailUserRegionAssignment_.region) : null
                );
        }
        return specification;
    }
}
