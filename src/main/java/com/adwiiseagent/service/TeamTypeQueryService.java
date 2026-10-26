package com.adwiiseagent.service;

import com.adwiiseagent.repository.TeamTypeRepository;
import com.adwiiseagent.security.TenantContext;
import com.adwiiseagent.service.criteria.RosterCatalogCriteria;
import com.adwiiseagent.service.dto.TeamTypeDTO;
import com.adwiiseagent.service.mapper.TeamTypeMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class TeamTypeQueryService {

    private final TeamTypeRepository repository;
    private final TeamTypeMapper mapper;

    public TeamTypeQueryService(TeamTypeRepository repository, TeamTypeMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Page<TeamTypeDTO> findByCriteria(RosterCatalogCriteria criteria, Pageable page) {
        return repository
            .findAll(RosterCatalogSpecs.visible(TenantContext.getCurrentTenant(), criteria), SportsCategoryQueryService.withDefaultSort(page))
            .map(mapper::toDto);
    }

    public long countByCriteria(RosterCatalogCriteria criteria) {
        return repository.count(RosterCatalogSpecs.visible(TenantContext.getCurrentTenant(), criteria));
    }
}
