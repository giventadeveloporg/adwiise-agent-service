package com.adwiiseagent.service;

import com.adwiiseagent.domain.RecommendationCategory;
import com.adwiiseagent.errors.BadRequestAlertException;
import com.adwiiseagent.repository.RecommendationCategoryRepository;
import com.adwiiseagent.service.dto.RecommendationCategoryDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RecommendationCategoryService {

    private static final String ENTITY = "recommendationCategory";

    private final RecommendationCategoryRepository repository;
    private final RosterCatalogGuard guard;

    public RecommendationCategoryService(RecommendationCategoryRepository repository, RosterCatalogGuard guard) {
        this.repository = repository;
        this.guard = guard;
    }

    public RecommendationCategoryDTO save(RecommendationCategoryDTO dto) {
        normalize(dto);
        guard.assertWrite(false, null, null);
        if (repository.findByCode(dto.getCode()).isPresent()) {
            throw new BadRequestAlertException("This category already exists", ENTITY, "codeexists");
        }
        return toDto(repository.save(toEntity(dto, new RecommendationCategory())));
    }

    public RecommendationCategoryDTO update(RecommendationCategoryDTO dto) {
        normalize(dto);
        RecommendationCategory existing = repository
            .findById(dto.getId())
            .orElseThrow(() -> new BadRequestAlertException("Entity not found", ENTITY, "idnotfound"));
        guard.assertWrite(true, null, null);
        repository
            .findByCode(dto.getCode())
            .filter(other -> !other.getId().equals(existing.getId()))
            .ifPresent(other -> {
                throw new BadRequestAlertException("This category already exists", ENTITY, "codeexists");
            });
        return toDto(repository.save(toEntity(dto, existing)));
    }

    @Transactional(readOnly = true)
    public List<RecommendationCategoryDTO> findAll() {
        return repository.findAllByOrderBySortOrderAsc().stream().map(this::toDto).toList();
    }

    @Transactional(readOnly = true)
    public Optional<RecommendationCategoryDTO> findOne(Long id) {
        return repository.findById(id).map(this::toDto);
    }

    public void delete(Long id) {
        guard.assertWrite(true, null, null);
        repository.deleteById(id);
    }

    private void normalize(RecommendationCategoryDTO dto) {
        String code = RosterCatalogGuard.normalizeCode(dto.getCode()).toUpperCase();
        if (code.length() > 32) {
            throw new BadRequestAlertException("code must be 32 characters or fewer", ENTITY, "codetoolong");
        }
        dto.setCode(code);
        if (dto.getCode() == null) {
            throw new BadRequestAlertException("code is required", ENTITY, "coderequired");
        }
        if (dto.getLabel() == null || dto.getLabel().isBlank()) {
            throw new BadRequestAlertException("label is required", ENTITY, "labelrequired");
        }
        dto.setLabel(dto.getLabel().trim());
        if (dto.getSortOrder() == null) {
            dto.setSortOrder(0);
        }
        if (dto.getIsActive() == null) {
            dto.setIsActive(true);
        }
    }

    private RecommendationCategory toEntity(RecommendationCategoryDTO dto, RecommendationCategory entity) {
        entity.setCode(dto.getCode());
        entity.setLabel(dto.getLabel());
        entity.setSortOrder(dto.getSortOrder());
        entity.setIsActive(dto.getIsActive());
        return entity;
    }

    private RecommendationCategoryDTO toDto(RecommendationCategory entity) {
        RecommendationCategoryDTO dto = new RecommendationCategoryDTO();
        dto.setId(entity.getId());
        dto.setCode(entity.getCode());
        dto.setLabel(entity.getLabel());
        dto.setSortOrder(entity.getSortOrder());
        dto.setIsActive(entity.getIsActive());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }
}
