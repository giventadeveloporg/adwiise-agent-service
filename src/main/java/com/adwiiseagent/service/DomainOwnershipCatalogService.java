package com.adwiiseagent.service;

import com.adwiiseagent.domain.DomainOwnershipCatalog;
import com.adwiiseagent.errors.BadRequestAlertException;
import com.adwiiseagent.repository.DomainOwnershipCatalogRepository;
import com.adwiiseagent.repository.TenantOnboardingRequestRepository;
import com.adwiiseagent.service.dto.DomainOwnershipCatalogDTO;
import com.adwiiseagent.service.mapper.DomainOwnershipCatalogMapper;
import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DomainOwnershipCatalogService {

    private static final String ENTITY = "domainOwnershipCatalog";
    private static final Set<String> BUILT_IN = Set.of("CUSTOMER_REGISTRAR", "PLATFORM_REGISTERS", "ALREADY_IN_PLATFORM");

    private final DomainOwnershipCatalogRepository repository;
    private final DomainOwnershipCatalogMapper mapper;
    private final RosterCatalogGuard guard;
    private final TenantOnboardingRequestRepository tenantOnboardingRequestRepository;

    public DomainOwnershipCatalogService(
        DomainOwnershipCatalogRepository repository,
        DomainOwnershipCatalogMapper mapper,
        RosterCatalogGuard guard,
        TenantOnboardingRequestRepository tenantOnboardingRequestRepository
    ) {
        this.repository = repository;
        this.mapper = mapper;
        this.guard = guard;
        this.tenantOnboardingRequestRepository = tenantOnboardingRequestRepository;
    }

    public DomainOwnershipCatalogDTO save(DomainOwnershipCatalogDTO dto) {
        normalize(dto);
        guard.assertWrite(false, null, dto.getTenantId());
        assertCodeAvailable(dto.getCode(), dto.getTenantId(), null);
        DomainOwnershipCatalog entity = mapper.toEntity(dto);
        entity.setId(null);
        return mapper.toDto(repository.save(entity));
    }

    public DomainOwnershipCatalogDTO update(DomainOwnershipCatalogDTO dto) {
        DomainOwnershipCatalog existing = repository
            .findById(dto.getId())
            .orElseThrow(() -> new BadRequestAlertException("Entity not found", ENTITY, "idnotfound"));
        normalize(dto);
        guard.assertWrite(true, existing.getTenantId(), dto.getTenantId());
        protectBuiltIn(existing, dto);
        assertCodeAvailable(dto.getCode(), dto.getTenantId(), existing.getId());
        mapper.partialUpdate(existing, dto);
        return mapper.toDto(repository.save(existing));
    }

    public Optional<DomainOwnershipCatalogDTO> partialUpdate(DomainOwnershipCatalogDTO dto) {
        return repository
            .findById(dto.getId())
            .map(existing -> {
                normalize(dto);
                guard.assertWrite(true, existing.getTenantId(), dto.getTenantId() != null ? dto.getTenantId() : existing.getTenantId());
                protectBuiltIn(existing, dto);
                if (dto.getCode() != null) {
                    assertCodeAvailable(
                        dto.getCode(),
                        dto.getTenantId() != null ? dto.getTenantId() : existing.getTenantId(),
                        existing.getId()
                    );
                }
                mapper.partialUpdate(existing, dto);
                return mapper.toDto(repository.save(existing));
            });
    }

    @Transactional(readOnly = true)
    public Optional<DomainOwnershipCatalogDTO> findOne(Long id) {
        return repository.findById(id).map(mapper::toDto);
    }

    public void delete(Long id) {
        DomainOwnershipCatalog existing = repository
            .findById(id)
            .orElseThrow(() -> new BadRequestAlertException("Entity not found", ENTITY, "idnotfound"));
        guard.assertWrite(true, existing.getTenantId(), existing.getTenantId());
        if (BUILT_IN.contains(existing.getCode()) && existing.getTenantId() == null) {
            throw new BadRequestAlertException("This domain ownership cannot be removed", ENTITY, "builtin");
        }
        if (tenantOnboardingRequestRepository.existsByDomainOwnership(existing.getCode())) {
            existing.setIsActive(false);
            repository.save(existing);
            return;
        }
        repository.delete(existing);
    }

    private void normalize(DomainOwnershipCatalogDTO dto) {
        dto.setTenantId(RosterCatalogGuard.normalizeTenant(dto.getTenantId()));
        if (dto.getCode() != null) {
            dto.setCode(RosterCatalogGuard.normalizeCode(dto.getCode()));
        }
        if (dto.getLabel() != null) {
            dto.setLabel(dto.getLabel().trim());
        }
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

    private void protectBuiltIn(DomainOwnershipCatalog existing, DomainOwnershipCatalogDTO dto) {
        if (existing.getTenantId() == null && BUILT_IN.contains(existing.getCode())) {
            dto.setCode(existing.getCode());
            dto.setIsActive(true);
        }
    }

    private void assertCodeAvailable(String code, String tenantId, Long excludeId) {
        Optional<DomainOwnershipCatalog> found = tenantId == null
            ? repository.findByCodeAndTenantIdIsNull(code)
            : repository.findByCodeAndTenantId(code, tenantId);
        if (found.isPresent() && (excludeId == null || !found.orElseThrow().getId().equals(excludeId))) {
            throw new BadRequestAlertException("A catalog row with this code already exists", ENTITY, "codeexists");
        }
    }
}
