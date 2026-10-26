package com.adwiiseagent.service;

import com.adwiiseagent.domain.TeamType;
import com.adwiiseagent.errors.BadRequestAlertException;
import com.adwiiseagent.repository.TeamGroupRepository;
import com.adwiiseagent.repository.TeamTypeRepository;
import com.adwiiseagent.service.dto.TeamTypeDTO;
import com.adwiiseagent.service.mapper.TeamTypeMapper;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TeamTypeService {

    private static final String ENTITY = "teamType";

    private final TeamTypeRepository repository;
    private final TeamTypeMapper mapper;
    private final RosterCatalogGuard guard;
    private final TeamGroupRepository teamGroupRepository;

    public TeamTypeService(
        TeamTypeRepository repository,
        TeamTypeMapper mapper,
        RosterCatalogGuard guard,
        TeamGroupRepository teamGroupRepository
    ) {
        this.repository = repository;
        this.mapper = mapper;
        this.guard = guard;
        this.teamGroupRepository = teamGroupRepository;
    }

    public TeamTypeDTO save(TeamTypeDTO dto) {
        normalize(dto);
        guard.assertWrite(false, null, dto.getTenantId());
        assertCodeAvailable(dto.getCode(), dto.getTenantId(), null);
        TeamType entity = mapper.toEntity(dto);
        entity.setId(null);
        return mapper.toDto(repository.save(entity));
    }

    public TeamTypeDTO update(TeamTypeDTO dto) {
        normalize(dto);
        TeamType existing = repository
            .findById(dto.getId())
            .orElseThrow(() -> new BadRequestAlertException("Entity not found", ENTITY, "idnotfound"));
        guard.assertWrite(true, existing.getTenantId(), dto.getTenantId());
        if (!existing.getCode().equals(dto.getCode()) && isReferenced(existing)) {
            throw new BadRequestAlertException("This code is in use and cannot be renamed", ENTITY, "codeinuse");
        }
        assertCodeAvailable(dto.getCode(), dto.getTenantId(), existing.getId());
        TeamType entity = mapper.toEntity(dto);
        if (entity.getCreatedAt() == null) {
            entity.setCreatedAt(existing.getCreatedAt());
        }
        return mapper.toDto(repository.save(entity));
    }

    public Optional<TeamTypeDTO> partialUpdate(TeamTypeDTO dto) {
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
    public Optional<TeamTypeDTO> findOne(Long id) {
        return repository.findById(id).map(mapper::toDto);
    }

    public void delete(Long id) {
        TeamType existing = repository
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

    private boolean isReferenced(TeamType existing) {
        if (existing.getTenantId() == null) {
            return teamGroupRepository.existsByTeamType(existing.getCode());
        }
        return teamGroupRepository.existsByTeamTypeAndTenantId(existing.getCode(), existing.getTenantId());
    }

    private void normalize(TeamTypeDTO dto) {
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
        Optional<TeamType> found = tenantId == null
            ? repository.findByCodeAndTenantIdIsNull(code)
            : repository.findByCodeAndTenantId(code, tenantId);
        if (found.isPresent() && (excludeId == null || !found.orElseThrow().getId().equals(excludeId))) {
            throw new BadRequestAlertException("A catalog row with this code already exists", ENTITY, "codeexists");
        }
    }
}
