package com.adwiiseagent.service;

import com.adwiiseagent.domain.AchievementCategory;
import com.adwiiseagent.errors.BadRequestAlertException;
import com.adwiiseagent.repository.AchievementCategoryRepository;
import com.adwiiseagent.repository.TeamMemberAchievementRepository;
import com.adwiiseagent.service.dto.AchievementCategoryDTO;
import com.adwiiseagent.service.mapper.AchievementCategoryMapper;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AchievementCategoryService {

    private static final String ENTITY = "achievementCategory";

    private final AchievementCategoryRepository repository;
    private final AchievementCategoryMapper mapper;
    private final RosterCatalogGuard guard;
    private final TeamMemberAchievementRepository achievementRepository;

    public AchievementCategoryService(
        AchievementCategoryRepository repository,
        AchievementCategoryMapper mapper,
        RosterCatalogGuard guard,
        TeamMemberAchievementRepository achievementRepository
    ) {
        this.repository = repository;
        this.mapper = mapper;
        this.guard = guard;
        this.achievementRepository = achievementRepository;
    }

    public AchievementCategoryDTO save(AchievementCategoryDTO dto) {
        normalize(dto);
        guard.assertWrite(false, null, dto.getTenantId());
        assertCodeAvailable(dto.getCode(), dto.getTenantId(), null);
        AchievementCategory entity = mapper.toEntity(dto);
        entity.setId(null);
        return mapper.toDto(repository.save(entity));
    }

    public AchievementCategoryDTO update(AchievementCategoryDTO dto) {
        normalize(dto);
        AchievementCategory existing = repository
            .findById(dto.getId())
            .orElseThrow(() -> new BadRequestAlertException("Entity not found", ENTITY, "idnotfound"));
        guard.assertWrite(true, existing.getTenantId(), dto.getTenantId());
        if (!existing.getCode().equals(dto.getCode()) && isReferenced(existing)) {
            throw new BadRequestAlertException("This code is in use and cannot be renamed", ENTITY, "codeinuse");
        }
        assertCodeAvailable(dto.getCode(), dto.getTenantId(), existing.getId());
        AchievementCategory entity = mapper.toEntity(dto);
        if (entity.getCreatedAt() == null) {
            entity.setCreatedAt(existing.getCreatedAt());
        }
        return mapper.toDto(repository.save(entity));
    }

    public Optional<AchievementCategoryDTO> partialUpdate(AchievementCategoryDTO dto) {
        return repository
            .findById(dto.getId())
            .map(existing -> {
                String requestedTenant = dto.getTenantId() == null
                    ? existing.getTenantId()
                    : RosterCatalogGuard.normalizeTenant(dto.getTenantId());
                guard.assertWrite(true, existing.getTenantId(), requestedTenant);
                if (dto.getCode() != null) {
                    String code = RosterCatalogGuard.normalizeCode(dto.getCode());
                    dto.setCode(code);
                    if (!existing.getCode().equals(code) && isReferenced(existing)) {
                        throw new BadRequestAlertException("This code is in use and cannot be renamed", ENTITY, "codeinuse");
                    }
                    assertCodeAvailable(code, requestedTenant, existing.getId());
                }
                if (dto.getLabel() != null) {
                    if (dto.getLabel().isBlank()) {
                        throw new BadRequestAlertException("label is required", ENTITY, "labelrequired");
                    }
                    dto.setLabel(dto.getLabel().trim());
                }
                dto.setTenantId(requestedTenant);
                mapper.partialUpdate(existing, dto);
                return mapper.toDto(repository.save(existing));
            });
    }

    @Transactional(readOnly = true)
    public Optional<AchievementCategoryDTO> findOne(Long id) {
        return repository.findById(id).map(mapper::toDto);
    }

    public void delete(Long id) {
        AchievementCategory existing = repository
            .findById(id)
            .orElseThrow(() -> new BadRequestAlertException("Entity not found", ENTITY, "idnotfound"));
        guard.assertWrite(true, existing.getTenantId(), existing.getTenantId());
        if (isReferenced(existing)) {
            existing.setIsActive(false);
            repository.save(existing);
            return;
        }
        repository.delete(existing);
    }

    private boolean isReferenced(AchievementCategory existing) {
        if (achievementRepository.existsByAchievementCategoryId(existing.getId())) {
            return true;
        }
        if (existing.getTenantId() == null) {
            return achievementRepository.existsByCategory(existing.getCode());
        }
        return achievementRepository.existsByCategoryAndTenantId(existing.getCode(), existing.getTenantId());
    }

    private void normalize(AchievementCategoryDTO dto) {
        dto.setTenantId(RosterCatalogGuard.normalizeTenant(dto.getTenantId()));
        dto.setCode(RosterCatalogGuard.normalizeCode(dto.getCode()));
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

    private void assertCodeAvailable(String code, String tenantId, Long excludeId) {
        Optional<AchievementCategory> found = tenantId == null
            ? repository.findByCodeAndTenantIdIsNull(code)
            : repository.findByCodeAndTenantId(code, tenantId);
        if (found.isPresent() && (excludeId == null || !found.orElseThrow().getId().equals(excludeId))) {
            throw new BadRequestAlertException("A catalog row with this code already exists", ENTITY, "codeexists");
        }
    }
}
