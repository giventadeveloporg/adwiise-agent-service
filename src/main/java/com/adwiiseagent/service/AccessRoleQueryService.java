package com.adwiiseagent.service;

import com.adwiiseagent.repository.AccessRoleRepository;
import com.adwiiseagent.security.TenantContext;
import com.adwiiseagent.service.criteria.RosterCatalogCriteria;
import com.adwiiseagent.service.dto.AccessRoleDTO;
import com.adwiiseagent.service.mapper.AccessRoleMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AccessRoleQueryService {

    private final AccessRoleRepository repository;
    private final AccessRoleMapper mapper;

    public AccessRoleQueryService(AccessRoleRepository repository, AccessRoleMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Page<AccessRoleDTO> findByCriteria(RosterCatalogCriteria criteria, Pageable page) {
        return repository
            .findAll(RosterCatalogSpecs.visible(TenantContext.getCurrentTenant(), criteria), SportsCategoryQueryService.withDefaultSort(page))
            .map(mapper::toDto);
    }

    public long countByCriteria(RosterCatalogCriteria criteria) {
        return repository.count(RosterCatalogSpecs.visible(TenantContext.getCurrentTenant(), criteria));
    }
}
