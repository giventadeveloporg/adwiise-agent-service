package com.adwiiseagent.service;

import com.adwiiseagent.repository.RetailerTypeRepository;
import com.adwiiseagent.security.TenantContext;
import com.adwiiseagent.service.criteria.RosterCatalogCriteria;
import com.adwiiseagent.service.dto.RetailerTypeDTO;
import com.adwiiseagent.service.mapper.RetailerTypeMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class RetailerTypeQueryService {

    private final RetailerTypeRepository repository;
    private final RetailerTypeMapper mapper;

    public RetailerTypeQueryService(RetailerTypeRepository repository, RetailerTypeMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Page<RetailerTypeDTO> findByCriteria(RosterCatalogCriteria criteria, Pageable page) {
        return repository
            .findAll(RosterCatalogSpecs.visible(TenantContext.getCurrentTenant(), criteria), SportsCategoryQueryService.withDefaultSort(page))
            .map(mapper::toDto);
    }

    public long countByCriteria(RosterCatalogCriteria criteria) {
        return repository.count(RosterCatalogSpecs.visible(TenantContext.getCurrentTenant(), criteria));
    }
}
