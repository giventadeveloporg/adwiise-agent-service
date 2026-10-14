package com.adwiiseagent.service;

import com.adwiiseagent.domain.RetailLocationProfile;
import com.adwiiseagent.domain.RetailLocationProfile_;
import com.adwiiseagent.repository.RetailLocationProfileRepository;
import com.adwiiseagent.service.criteria.RetailLocationProfileCriteria;
import com.adwiiseagent.service.dto.RetailLocationProfileDTO;
import com.adwiiseagent.service.mapper.RetailLocationProfileMapper;
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
public class RetailLocationProfileQueryService extends QueryService<RetailLocationProfile> {

    private static final Logger LOG = LoggerFactory.getLogger(RetailLocationProfileQueryService.class);

    private final RetailLocationProfileRepository retailLocationProfileRepository;
    private final RetailLocationProfileMapper retailLocationProfileMapper;
    private final RetailAccessService retailAccessService;

    public RetailLocationProfileQueryService(
        RetailLocationProfileRepository retailLocationProfileRepository,
        RetailLocationProfileMapper retailLocationProfileMapper,
        RetailAccessService retailAccessService
    ) {
        this.retailLocationProfileRepository = retailLocationProfileRepository;
        this.retailLocationProfileMapper = retailLocationProfileMapper;
        this.retailAccessService = retailAccessService;
    }

    public Page<RetailLocationProfileDTO> findByCriteria(RetailLocationProfileCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        if (criteria == null) {
            criteria = new RetailLocationProfileCriteria();
        }
        applyAccess(criteria);
        Specification<RetailLocationProfile> specification = createSpecification(criteria);
        return retailLocationProfileRepository.findAll(specification, page).map(retailLocationProfileMapper::toDto);
    }

    public long countByCriteria(RetailLocationProfileCriteria criteria) {
        if (criteria == null) {
            criteria = new RetailLocationProfileCriteria();
        }
        applyAccess(criteria);
        Specification<RetailLocationProfile> specification = createSpecification(criteria);
        return retailLocationProfileRepository.count(specification);
    }

    private void applyAccess(RetailLocationProfileCriteria criteria) {
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

    protected Specification<RetailLocationProfile> createSpecification(RetailLocationProfileCriteria criteria) {
        Specification<RetailLocationProfile> specification = Specification.where(null);
        if (criteria != null) {
            specification =
                Specification.allOf(
                    Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                    criteria.getId() != null ? buildRangeSpecification(criteria.getId(), RetailLocationProfile_.id) : null,
                    criteria.getTenantId() != null
                        ? buildStringSpecification(criteria.getTenantId(), RetailLocationProfile_.tenantId)
                        : null,
                    criteria.getLocationId() != null
                        ? buildRangeSpecification(criteria.getLocationId(), RetailLocationProfile_.locationId)
                        : null,
                    null
                );
        }
        return specification;
    }
}
