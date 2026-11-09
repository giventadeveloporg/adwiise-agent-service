package com.adwiiseagent.service;

import com.adwiiseagent.repository.DomainOwnershipCatalogRepository;
import com.adwiiseagent.security.TenantContext;
import com.adwiiseagent.service.criteria.RosterCatalogCriteria;
import com.adwiiseagent.service.dto.DomainOwnershipCatalogDTO;
import com.adwiiseagent.service.mapper.DomainOwnershipCatalogMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class DomainOwnershipCatalogQueryService {

    private final DomainOwnershipCatalogRepository repository;
    private final DomainOwnershipCatalogMapper mapper;

    public DomainOwnershipCatalogQueryService(DomainOwnershipCatalogRepository repository, DomainOwnershipCatalogMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Page<DomainOwnershipCatalogDTO> findByCriteria(RosterCatalogCriteria criteria, Pageable page) {
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
