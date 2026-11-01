package com.adwiiseagent.service;

import com.adwiiseagent.domain.SiteTypeCatalog;
import com.adwiiseagent.errors.BadRequestAlertException;
import com.adwiiseagent.repository.SiteTypeCatalogRepository;
import com.adwiiseagent.repository.TenantOnboardingRequestRepository;
import com.adwiiseagent.repository.TenantOrganizationRepository;
import com.adwiiseagent.service.dto.SiteTypeCatalogDTO;
import com.adwiiseagent.service.mapper.SiteTypeCatalogMapper;
import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SiteTypeCatalogService {

    private static final String ENTITY = "siteTypeCatalog";
    private static final Set<String> BUILT_IN = Set.of("EVENT_ORG", "RETAIL");

    private final SiteTypeCatalogRepository repository;
    private final SiteTypeCatalogMapper mapper;
    private final RosterCatalogGuard guard;
    private final TenantOrganizationRepository tenantOrganizationRepository;
    private final TenantOnboardingRequestRepository tenantOnboardingRequestRepository;

    public SiteTypeCatalogService(
        SiteTypeCatalogRepository repository,
        SiteTypeCatalogMapper mapper,
        RosterCatalogGuard guard,
        TenantOrganizationRepository tenantOrganizationRepository,
        TenantOnboardingRequestRepository tenantOnboardingRequestRepository
    ) {
        this.repository = repository;
        this.mapper = mapper;
        this.guard = guard;
        this.tenantOrganizationRepository = tenantOrganizationRepository;
        this.tenantOnboardingRequestRepository = tenantOnboardingRequestRepository;
    }

    public SiteTypeCatalogDTO save(SiteTypeCatalogDTO dto) {
        normalize(dto);
        guard.assertWrite(false, null, dto.getTenantId());
        assertCodeAvailable(dto.getCode(), dto.getTenantId(), null);
        SiteTypeCatalog entity = mapper.toEntity(dto);
        entity.setId(null);
        return mapper.toDto(repository.save(entity));
    }

    public SiteTypeCatalogDTO update(SiteTypeCatalogDTO dto) {
        normalize(dto);
        SiteTypeCatalog existing = repository
            .findById(dto.getId())
            .orElseThrow(() -> new BadRequestAlertException("Entity not found", ENTITY, "idnotfound"));
        guard.assertWrite(true, existing.getTenantId(), dto.getTenantId());
        protectBuiltIn(existing, dto);
        if (!existing.getCode().equals(dto.getCode()) && isReferenced(existing)) {
            throw new BadRequestAlertException("This code is in use and cannot be renamed", ENTITY, "codeinuse");
        }
        assertCodeAvailable(dto.getCode(), dto.getTenantId(), existing.getId());
        SiteTypeCatalog entity = mapper.toEntity(dto);
        if (entity.getCreatedAt() == null) {
            entity.setCreatedAt(existing.getCreatedAt());
        }
        return mapper.toDto(repository.save(entity));
    }

    public Optional<SiteTypeCatalogDTO> partialUpdate(SiteTypeCatalogDTO dto) {
        return repository
            .findById(dto.getId())
            .map(existing -> {
                String requestedTenant = dto.getTenantId() == null
                    ? existing.getTenantId()
                    : RosterCatalogGuard.normalizeTenant(dto.getTenantId());
                guard.assertWrite(true, existing.getTenantId(), requestedTenant);
                if (dto.getCode() != null) {
                    dto.setCode(RosterCatalogGuard.normalizeCode(dto.getCode()));
                }
                if (dto.getLabel() != null && dto.getLabel().isBlank()) {
                    throw new BadRequestAlertException("label is required", ENTITY, "labelrequired");
                }
                if (dto.getModuleKey() != null) {
                    dto.setModuleKey(normalizeModule(dto.getModuleKey()));
                }
                dto.setTenantId(requestedTenant);
                protectBuiltIn(existing, dto);
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
    public Optional<SiteTypeCatalogDTO> findOne(Long id) {
        return repository.findById(id).map(mapper::toDto);
    }

    public void delete(Long id) {
        SiteTypeCatalog existing = repository
            .findById(id)
            .orElseThrow(() -> new BadRequestAlertException("Entity not found", ENTITY, "idnotfound"));
        guard.assertWrite(true, existing.getTenantId(), existing.getTenantId());
        if (BUILT_IN.contains(existing.getCode()) && existing.getTenantId() == null) {
            throw new BadRequestAlertException("This site type cannot be removed", ENTITY, "builtin");
        }
        if (isReferenced(existing)) {
            existing.setIsActive(false);
            repository.save(existing);
            return;
        }
        repository.delete(existing);
    }

    private boolean isReferenced(SiteTypeCatalog existing) {
        if (tenantOrganizationRepository.existsBySiteType(existing.getCode())) {
            return true;
        }
        return tenantOnboardingRequestRepository.existsBySiteType(existing.getCode());
    }

    private void normalize(SiteTypeCatalogDTO dto) {
        dto.setTenantId(RosterCatalogGuard.normalizeTenant(dto.getTenantId()));
        dto.setCode(RosterCatalogGuard.normalizeCode(dto.getCode()));
        if (dto.getLabel() == null || dto.getLabel().isBlank()) {
            throw new BadRequestAlertException("label is required", ENTITY, "labelrequired");
        }
        dto.setLabel(dto.getLabel().trim());
        if (dto.getHint() != null) {
            dto.setHint(dto.getHint().trim());
        }
        dto.setModuleKey(normalizeModule(dto.getModuleKey()));
        if (dto.getSortOrder() == null) {
            dto.setSortOrder(0);
        }
        if (dto.getIsActive() == null) {
            dto.setIsActive(true);
        }
    }

    private void protectBuiltIn(SiteTypeCatalog existing, SiteTypeCatalogDTO dto) {
        if (existing.getTenantId() == null && BUILT_IN.contains(existing.getCode())) {
            dto.setCode(existing.getCode());
            dto.setIsActive(true);
        }
    }

    private void assertCodeAvailable(String code, String tenantId, Long excludeId) {
        Optional<SiteTypeCatalog> found = tenantId == null
            ? repository.findByCodeAndTenantIdIsNull(code)
            : repository.findByCodeAndTenantId(code, tenantId);
        if (found.isPresent() && (excludeId == null || !found.orElseThrow().getId().equals(excludeId))) {
            throw new BadRequestAlertException("A catalog row with this code already exists", ENTITY, "codeexists");
        }
    }

    private static String normalizeModule(String moduleKey) {
        if (moduleKey == null || moduleKey.isBlank()) {
            return "EVENT";
        }
        String value = moduleKey.trim().toUpperCase().replace(' ', '_');
        if (value.length() > 32) {
            throw new BadRequestAlertException("moduleKey must be 32 characters or fewer", ENTITY, "moduletoolong");
        }
        return value;
    }
}
