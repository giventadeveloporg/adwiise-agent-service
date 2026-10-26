package com.adwiiseagent.service;

import com.adwiiseagent.domain.AccessRole;
import com.adwiiseagent.errors.BadRequestAlertException;
import com.adwiiseagent.repository.AccessRoleRepository;
import com.adwiiseagent.repository.UserProfileRepository;
import com.adwiiseagent.service.dto.AccessRoleDTO;
import com.adwiiseagent.service.mapper.AccessRoleMapper;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccessRoleService {

    private static final String ENTITY = "accessRole";

    private final AccessRoleRepository repository;
    private final AccessRoleMapper mapper;
    private final RosterCatalogGuard guard;
    private final UserProfileRepository userProfileRepository;

    public AccessRoleService(
        AccessRoleRepository repository,
        AccessRoleMapper mapper,
        RosterCatalogGuard guard,
        UserProfileRepository userProfileRepository
    ) {
        this.repository = repository;
        this.mapper = mapper;
        this.guard = guard;
        this.userProfileRepository = userProfileRepository;
    }

    public AccessRoleDTO save(AccessRoleDTO dto) {
        normalize(dto);
        guard.assertWrite(false, null, dto.getTenantId());
        applyPrivileges(dto);
        assertCodeAvailable(dto.getCode(), dto.getTenantId(), null);
        AccessRole entity = mapper.toEntity(dto);
        entity.setId(null);
        return mapper.toDto(repository.save(entity));
    }

    public AccessRoleDTO update(AccessRoleDTO dto) {
        normalize(dto);
        AccessRole existing = repository
            .findById(dto.getId())
            .orElseThrow(() -> new BadRequestAlertException("Entity not found", ENTITY, "idnotfound"));
        guard.assertWrite(true, existing.getTenantId(), dto.getTenantId());
        protectBuiltIn(existing, dto);
        applyPrivileges(dto);
        if (!existing.getCode().equals(dto.getCode()) && isReferenced(existing)) {
            throw new BadRequestAlertException("This code is in use and cannot be renamed", ENTITY, "codeinuse");
        }
        assertCodeAvailable(dto.getCode(), dto.getTenantId(), existing.getId());
        AccessRole entity = mapper.toEntity(dto);
        if (entity.getCreatedAt() == null) {
            entity.setCreatedAt(existing.getCreatedAt());
        }
        return mapper.toDto(repository.save(entity));
    }

    public Optional<AccessRoleDTO> partialUpdate(AccessRoleDTO dto) {
        return repository
            .findById(dto.getId())
            .map(existing -> {
                String requestedTenant = dto.getTenantId() == null ? existing.getTenantId() : RosterCatalogGuard.normalizeTenant(dto.getTenantId());
                guard.assertWrite(true, existing.getTenantId(), requestedTenant);
                if (dto.getCode() != null) {
                    dto.setCode(RosterCatalogGuard.normalizeCode(dto.getCode()));
                }
                if (dto.getLabel() != null && dto.getLabel().isBlank()) {
                    throw new BadRequestAlertException("label is required", ENTITY, "labelrequired");
                }
                if (dto.getLocationScope() != null) {
                    dto.setLocationScope(normalizeScope(dto.getLocationScope()));
                }
                dto.setTenantId(requestedTenant);
                protectBuiltIn(existing, dto);
                if (!guard.isGlobalWrite() || requestedTenant != null) {
                    if (dto.getPlatformSuper() != null) {
                        dto.setPlatformSuper(false);
                    }
                }
                if (dto.getCode() != null && !existing.getCode().equals(dto.getCode()) && isReferenced(existing)) {
                    throw new BadRequestAlertException("This code is in use and cannot be renamed", ENTITY, "codeinuse");
                }
                if (dto.getCode() != null) {
                    assertCodeAvailable(dto.getCode(), requestedTenant, existing.getId());
                }
                mapper.partialUpdate(existing, dto);
                return mapper.toDto(repository.save(existing));
            });
    }

    @Transactional(readOnly = true)
    public Optional<AccessRoleDTO> findOne(Long id) {
        return repository.findById(id).map(mapper::toDto);
    }

    public void delete(Long id) {
        AccessRole existing = repository
            .findById(id)
            .orElseThrow(() -> new BadRequestAlertException("Entity not found", ENTITY, "idnotfound"));
        guard.assertWrite(true, existing.getTenantId(), existing.getTenantId());
        if ("SUPER_ADMIN".equals(existing.getCode()) || "ADMIN".equals(existing.getCode())) {
            throw new BadRequestAlertException("This built-in role cannot be removed", ENTITY, "builtin");
        }
        if (isReferenced(existing)) {
            existing.setIsActive(false);
            repository.save(existing);
            return;
        }
        repository.delete(existing);
    }

    private boolean isReferenced(AccessRole existing) {
        if (existing.getTenantId() == null) {
            return userProfileRepository.existsByUserRole(existing.getCode());
        }
        return userProfileRepository.existsByUserRoleAndTenantId(existing.getCode(), existing.getTenantId());
    }

    private void normalize(AccessRoleDTO dto) {
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
        if (dto.getGrantsAdmin() == null) {
            dto.setGrantsAdmin(false);
        }
        if (dto.getPlatformSuper() == null) {
            dto.setPlatformSuper(false);
        }
        dto.setLocationScope(normalizeScope(dto.getLocationScope()));
    }

    private void applyPrivileges(AccessRoleDTO dto) {
        if (dto.getTenantId() != null || !guard.isGlobalWrite()) {
            dto.setPlatformSuper(false);
        }
    }

    private void protectBuiltIn(AccessRole existing, AccessRoleDTO dto) {
        if ("SUPER_ADMIN".equals(existing.getCode())) {
            dto.setCode("SUPER_ADMIN");
            dto.setGrantsAdmin(true);
            dto.setPlatformSuper(true);
            dto.setIsActive(true);
            dto.setLocationScope("ALL");
        } else if ("ADMIN".equals(existing.getCode())) {
            dto.setCode("ADMIN");
            dto.setGrantsAdmin(true);
            dto.setPlatformSuper(false);
            dto.setIsActive(true);
            dto.setLocationScope("ALL");
        }
    }

    private void assertCodeAvailable(String code, String tenantId, Long excludeId) {
        Optional<AccessRole> found = tenantId == null
            ? repository.findByCodeAndTenantIdIsNull(code)
            : repository.findByCodeAndTenantId(code, tenantId);
        if (found.isPresent() && (excludeId == null || !found.get().getId().equals(excludeId))) {
            throw new BadRequestAlertException("A catalog row with this code already exists", ENTITY, "codeexists");
        }
    }

    private static String normalizeScope(String scope) {
        if (scope == null) {
            return "NONE";
        }
        String value = scope.trim().toUpperCase();
        if ("ALL".equals(value) || "ASSIGNED".equals(value)) {
            return value;
        }
        return "NONE";
    }
}
