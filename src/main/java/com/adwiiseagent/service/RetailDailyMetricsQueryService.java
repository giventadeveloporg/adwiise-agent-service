package com.adwiiseagent.service;

import com.adwiiseagent.domain.RetailDailyMetrics;
import com.adwiiseagent.domain.RetailDailyMetrics_;
import com.adwiiseagent.repository.RetailDailyMetricsRepository;
import com.adwiiseagent.service.criteria.RetailDailyMetricsCriteria;
import com.adwiiseagent.service.dto.RetailDailyMetricsDTO;
import com.adwiiseagent.service.mapper.RetailDailyMetricsMapper;
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
public class RetailDailyMetricsQueryService extends QueryService<RetailDailyMetrics> {

    private static final Logger LOG = LoggerFactory.getLogger(RetailDailyMetricsQueryService.class);

    private final RetailDailyMetricsRepository retailDailyMetricsRepository;
    private final RetailDailyMetricsMapper retailDailyMetricsMapper;
    private final RetailAccessService retailAccessService;

    public RetailDailyMetricsQueryService(
        RetailDailyMetricsRepository retailDailyMetricsRepository,
        RetailDailyMetricsMapper retailDailyMetricsMapper,
        RetailAccessService retailAccessService
    ) {
        this.retailDailyMetricsRepository = retailDailyMetricsRepository;
        this.retailDailyMetricsMapper = retailDailyMetricsMapper;
        this.retailAccessService = retailAccessService;
    }

    public Page<RetailDailyMetricsDTO> findByCriteria(RetailDailyMetricsCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        if (criteria == null) {
            criteria = new RetailDailyMetricsCriteria();
        }
        applyAccess(criteria);
        Specification<RetailDailyMetrics> specification = createSpecification(criteria);
        return retailDailyMetricsRepository.findAll(specification, page).map(retailDailyMetricsMapper::toDto);
    }

    public long countByCriteria(RetailDailyMetricsCriteria criteria) {
        if (criteria == null) {
            criteria = new RetailDailyMetricsCriteria();
        }
        applyAccess(criteria);
        Specification<RetailDailyMetrics> specification = createSpecification(criteria);
        return retailDailyMetricsRepository.count(specification);
    }

    private void applyAccess(RetailDailyMetricsCriteria criteria) {
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

    protected Specification<RetailDailyMetrics> createSpecification(RetailDailyMetricsCriteria criteria) {
        Specification<RetailDailyMetrics> specification = Specification.where(null);
        if (criteria != null) {
            specification =
                Specification.allOf(
                    Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                    criteria.getId() != null ? buildRangeSpecification(criteria.getId(), RetailDailyMetrics_.id) : null,
                    criteria.getTenantId() != null ? buildStringSpecification(criteria.getTenantId(), RetailDailyMetrics_.tenantId) : null,
                    criteria.getLocationId() != null
                        ? buildRangeSpecification(criteria.getLocationId(), RetailDailyMetrics_.locationId)
                        : null,
                    criteria.getMetricDate() != null
                        ? buildRangeSpecification(criteria.getMetricDate(), RetailDailyMetrics_.metricDate)
                        : null,
                    null
                );
        }
        return specification;
    }
}
