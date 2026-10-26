package com.adwiiseagent.service;

import com.adwiiseagent.repository.AchievementCategoryRepository;
import com.adwiiseagent.security.TenantContext;
import com.adwiiseagent.service.criteria.RosterCatalogCriteria;
import com.adwiiseagent.service.dto.AchievementCategoryDTO;
import com.adwiiseagent.service.mapper.AchievementCategoryMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AchievementCategoryQueryService {

    private final AchievementCategoryRepository repository;
    private final AchievementCategoryMapper mapper;

    public AchievementCategoryQueryService(AchievementCategoryRepository repository, AchievementCategoryMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Page<AchievementCategoryDTO> findByCriteria(RosterCatalogCriteria criteria, Pageable page) {
        return repository
            .findAll(RosterCatalogSpecs.visible(TenantContext.getCurrentTenant(), criteria), SportsCategoryQueryService.withDefaultSort(page))
            .map(mapper::toDto);
    }

    public long countByCriteria(RosterCatalogCriteria criteria) {
        return repository.count(RosterCatalogSpecs.visible(TenantContext.getCurrentTenant(), criteria));
    }
}
