package com.adwiiseagent.service;

import com.adwiiseagent.repository.SubscriptionPlanCatalogRepository;
import com.adwiiseagent.security.TenantContext;
import com.adwiiseagent.service.criteria.RosterCatalogCriteria;
import com.adwiiseagent.service.dto.SubscriptionPlanCatalogDTO;
import com.adwiiseagent.service.mapper.SubscriptionPlanCatalogMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class SubscriptionPlanCatalogQueryService {

    private final SubscriptionPlanCatalogRepository repository;
    private final SubscriptionPlanCatalogMapper mapper;

    public SubscriptionPlanCatalogQueryService(SubscriptionPlanCatalogRepository repository, SubscriptionPlanCatalogMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Page<SubscriptionPlanCatalogDTO> findByCriteria(RosterCatalogCriteria criteria, Pageable page) {
        return repository
            .findAll(
                RosterCatalogSpecs.visible(TenantContext.getCurrentTenant(), criteria),
                SportsCategoryQueryService.withDefaultSort(page)
            )
            .map(mapper::toDto);
    }

    public long countByCriteria(RosterCatalogCriteria criteria) {
        return repository.count(RosterCatalogSpecs.visible(TenantContext.getCurrentTenant(), criteria));
    }
}
