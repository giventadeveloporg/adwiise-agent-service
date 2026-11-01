package com.adwiiseagent.service;

import com.adwiiseagent.repository.SiteTypeCatalogRepository;
import com.adwiiseagent.security.TenantContext;
import com.adwiiseagent.service.criteria.RosterCatalogCriteria;
import com.adwiiseagent.service.dto.SiteTypeCatalogDTO;
import com.adwiiseagent.service.mapper.SiteTypeCatalogMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class SiteTypeCatalogQueryService {

    private final SiteTypeCatalogRepository repository;
    private final SiteTypeCatalogMapper mapper;

    public SiteTypeCatalogQueryService(SiteTypeCatalogRepository repository, SiteTypeCatalogMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Page<SiteTypeCatalogDTO> findByCriteria(RosterCatalogCriteria criteria, Pageable page) {
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
