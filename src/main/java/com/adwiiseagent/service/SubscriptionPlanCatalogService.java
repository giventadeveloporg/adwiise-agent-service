package com.adwiiseagent.service;

import com.adwiiseagent.domain.SubscriptionPlanCatalog;
import com.adwiiseagent.errors.BadRequestAlertException;
import com.adwiiseagent.repository.SubscriptionPlanCatalogRepository;
import com.adwiiseagent.repository.TenantOrganizationRepository;
import com.adwiiseagent.service.dto.SubscriptionPlanCatalogDTO;
import com.adwiiseagent.service.mapper.SubscriptionPlanCatalogMapper;
import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SubscriptionPlanCatalogService {

    private static final String ENTITY = "subscriptionPlanCatalog";
    private static final Set<String> BUILT_IN = Set.of("BASIC", "PROFESSIONAL", "ENTERPRISE");

    private final SubscriptionPlanCatalogRepository repository;
    private final SubscriptionPlanCatalogMapper mapper;
    private final RosterCatalogGuard guard;
    private final TenantOrganizationRepository tenantOrganizationRepository;

    public SubscriptionPlanCatalogService(
        SubscriptionPlanCatalogRepository repository,
        SubscriptionPlanCatalogMapper mapper,
        RosterCatalogGuard guard,
        TenantOrganizationRepository tenantOrganizationRepository
    ) {
        this.repository = repository;
        this.mapper = mapper;
        this.guard = guard;
        this.tenantOrganizationRepository = tenantOrganizationRepository;
    }

    public SubscriptionPlanCatalogDTO save(SubscriptionPlanCatalogDTO dto) {
        normalize(dto);
        guard.assertWrite(false, null, dto.getTenantId());
        assertCodeAvailable(dto.getCode(), dto.getTenantId(), null);
        SubscriptionPlanCatalog entity = mapper.toEntity(dto);
        entity.setId(null);
        return mapper.toDto(repository.save(entity));
    }

    public SubscriptionPlanCatalogDTO update(SubscriptionPlanCatalogDTO dto) {
        normalize(dto);
        SubscriptionPlanCatalog existing = repository
            .findById(dto.getId())
            .orElseThrow(() -> new BadRequestAlertException("Entity not found", ENTITY, "idnotfound"));
        guard.assertWrite(true, existing.getTenantId(), dto.getTenantId());
        protectBuiltIn(existing, dto);
        if (!existing.getCode().equals(dto.getCode()) && isReferenced(existing)) {
            throw new BadRequestAlertException("This code is in use and cannot be renamed", ENTITY, "codeinuse");
        }
        assertCodeAvailable(dto.getCode(), dto.getTenantId(), existing.getId());
        SubscriptionPlanCatalog entity = mapper.toEntity(dto);
        if (entity.getCreatedAt() == null) {
            entity.setCreatedAt(existing.getCreatedAt());
        }
        return mapper.toDto(repository.save(entity));
    }

    public Optional<SubscriptionPlanCatalogDTO> partialUpdate(SubscriptionPlanCatalogDTO dto) {
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
    public Optional<SubscriptionPlanCatalogDTO> findOne(Long id) {
        return repository.findById(id).map(mapper::toDto);
    }

    public void delete(Long id) {
        SubscriptionPlanCatalog existing = repository
            .findById(id)
            .orElseThrow(() -> new BadRequestAlertException("Entity not found", ENTITY, "idnotfound"));
        guard.assertWrite(true, existing.getTenantId(), existing.getTenantId());
        if (BUILT_IN.contains(existing.getCode()) && existing.getTenantId() == null) {
            throw new BadRequestAlertException("This subscription plan cannot be removed", ENTITY, "builtin");
        }
        if (isReferenced(existing)) {
            existing.setIsActive(false);
            repository.save(existing);
            return;
        }
        repository.delete(existing);
    }

    private boolean isReferenced(SubscriptionPlanCatalog existing) {
        return tenantOrganizationRepository.existsBySubscriptionPlan(existing.getCode());
    }

    private void normalize(SubscriptionPlanCatalogDTO dto) {
        dto.setTenantId(RosterCatalogGuard.normalizeTenant(dto.getTenantId()));
        dto.setCode(RosterCatalogGuard.normalizeCode(dto.getCode()));
        if (dto.getLabel() == null || dto.getLabel().isBlank()) {
            throw new BadRequestAlertException("label is required", ENTITY, "labelrequired");
        }
        dto.setLabel(dto.getLabel().trim());
        if (dto.getHint() != null) {
            dto.setHint(dto.getHint().trim());
        }
        if (dto.getSortOrder() == null) {
            dto.setSortOrder(0);
        }
        if (dto.getIsActive() == null) {
            dto.setIsActive(true);
        }
    }

    private void protectBuiltIn(SubscriptionPlanCatalog existing, SubscriptionPlanCatalogDTO dto) {
        if (existing.getTenantId() == null && BUILT_IN.contains(existing.getCode())) {
            dto.setCode(existing.getCode());
            dto.setIsActive(true);
        }
    }

    private void assertCodeAvailable(String code, String tenantId, Long excludeId) {
        Optional<SubscriptionPlanCatalog> found = tenantId == null
            ? repository.findByCodeAndTenantIdIsNull(code)
            : repository.findByCodeAndTenantId(code, tenantId);
        if (found.isPresent() && (excludeId == null || !found.orElseThrow().getId().equals(excludeId))) {
            throw new BadRequestAlertException("A catalog row with this code already exists", ENTITY, "codeexists");
        }
    }
}
