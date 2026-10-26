package com.adwiiseagent.service;

import com.adwiiseagent.repository.SportsCategoryRepository;
import com.adwiiseagent.security.TenantContext;
import com.adwiiseagent.service.criteria.RosterCatalogCriteria;
import com.adwiiseagent.service.dto.SportsCategoryDTO;
import com.adwiiseagent.service.mapper.SportsCategoryMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class SportsCategoryQueryService {

    private final SportsCategoryRepository repository;
    private final SportsCategoryMapper mapper;

    public SportsCategoryQueryService(SportsCategoryRepository repository, SportsCategoryMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Page<SportsCategoryDTO> findByCriteria(RosterCatalogCriteria criteria, Pageable page) {
        return repository.findAll(RosterCatalogSpecs.visible(TenantContext.getCurrentTenant(), criteria), withDefaultSort(page)).map(mapper::toDto);
    }

    public long countByCriteria(RosterCatalogCriteria criteria) {
        return repository.count(RosterCatalogSpecs.visible(TenantContext.getCurrentTenant(), criteria));
    }

    static Pageable withDefaultSort(Pageable page) {
        if (page.isPaged() && page.getSort().isUnsorted()) {
            return PageRequest.of(
                page.getPageNumber(),
                page.getPageSize(),
                Sort.by(Sort.Order.asc("sortOrder"), Sort.Order.asc("label"))
            );
        }
        return page;
    }
}
