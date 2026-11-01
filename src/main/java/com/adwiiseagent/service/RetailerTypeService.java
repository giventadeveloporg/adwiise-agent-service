package com.adwiiseagent.service;

import com.adwiiseagent.domain.RetailerType;
import com.adwiiseagent.errors.BadRequestAlertException;
import com.adwiiseagent.repository.RetailLocationRepository;
import com.adwiiseagent.repository.RetailerTypeRepository;
import com.adwiiseagent.repository.TenantOrganizationRepository;
import com.adwiiseagent.service.dto.RetailerTypeDTO;
import com.adwiiseagent.service.mapper.RetailerTypeMapper;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RetailerTypeService {

    private static final String ENTITY = "retailerType";

    private final RetailerTypeRepository repository;
    private final RetailerTypeMapper mapper;
    private final RosterCatalogGuard guard;
    private final TenantOrganizationRepository tenantOrganizationRepository;
    private final RetailLocationRepository retailLocationRepository;
    private final SiteTypeCatalogLookup siteTypeCatalogLookup;

    public RetailerTypeService(
        RetailerTypeRepository repository,
        RetailerTypeMapper mapper,
        RosterCatalogGuard guard,
        TenantOrganizationRepository tenantOrganizationRepository,
        RetailLocationRepository retailLocationRepository,
        SiteTypeCatalogLookup siteTypeCatalogLookup
    ) {
        this.repository = repository;
        this.mapper = mapper;
        this.guard = guard;
        this.tenantOrganizationRepository = tenantOrganizationRepository;
        this.retailLocationRepository = retailLocationRepository;
        this.siteTypeCatalogLookup = siteTypeCatalogLookup;
    }

    public RetailerTypeDTO save(RetailerTypeDTO dto) {
        normalize(dto);
        guard.assertWrite(false, null, dto.getTenantId());
        assertCodeAvailable(dto.getCode(), dto.getTenantId(), null);
        RetailerType entity = mapper.toEntity(dto);
        entity.setId(null);
        return mapper.toDto(repository.save(entity));
    }

    public RetailerTypeDTO update(RetailerTypeDTO dto) {
        normalize(dto);
        RetailerType existing = repository
            .findById(dto.getId())
            .orElseThrow(() -> new BadRequestAlertException("Entity not found", ENTITY, "idnotfound"));
        guard.assertWrite(true, existing.getTenantId(), dto.getTenantId());
        protectBuiltIn(existing, dto);
        if (!existing.getCode().equals(dto.getCode()) && isReferenced(existing)) {
            throw new BadRequestAlertException("This code is in use and cannot be renamed", ENTITY, "codeinuse");
        }
        assertCodeAvailable(dto.getCode(), dto.getTenantId(), existing.getId());
        RetailerType entity = mapper.toEntity(dto);
        if (entity.getCreatedAt() == null) {
            entity.setCreatedAt(existing.getCreatedAt());
        }
        return mapper.toDto(repository.save(entity));
    }

    public Optional<RetailerTypeDTO> partialUpdate(RetailerTypeDTO dto) {
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
                if (dto.getSiteTypeCode() != null) {
                    String parent = dto.getSiteTypeCode().trim().toUpperCase();
                    siteTypeCatalogLookup.requireAssignable(parent, requestedTenant);
                    dto.setSiteTypeCode(parent);
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
    public Optional<RetailerTypeDTO> findOne(Long id) {
        return repository.findById(id).map(mapper::toDto);
    }

    public void delete(Long id) {
        RetailerType existing = repository
            .findById(id)
            .orElseThrow(() -> new BadRequestAlertException("Entity not found", ENTITY, "idnotfound"));
        guard.assertWrite(true, existing.getTenantId(), existing.getTenantId());
        if ("GAS_STATION".equals(existing.getCode()) && existing.getTenantId() == null) {
            throw new BadRequestAlertException("The gas station pack cannot be removed", ENTITY, "builtin");
        }
        if (isReferenced(existing)) {
            existing.setIsActive(false);
            repository.save(existing);
            return;
        }
        repository.delete(existing);
    }

    private boolean isReferenced(RetailerType existing) {
        return (
            tenantOrganizationRepository.existsByRetailerTypeCode(existing.getCode()) ||
            retailLocationRepository.existsByRetailerTypeCode(existing.getCode())
        );
    }

    private void normalize(RetailerTypeDTO dto) {
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
        if (dto.getSiteTypeCode() == null || dto.getSiteTypeCode().isBlank()) {
            dto.setSiteTypeCode("RETAIL");
        }
        dto.setSiteTypeCode(dto.getSiteTypeCode().trim().toUpperCase());
        siteTypeCatalogLookup.requireAssignable(dto.getSiteTypeCode(), dto.getTenantId());
        if (dto.getSortOrder() == null) {
            dto.setSortOrder(0);
        }
        if (dto.getIsActive() == null) {
            dto.setIsActive(true);
        }
    }

    private void protectBuiltIn(RetailerType existing, RetailerTypeDTO dto) {
        if (existing.getTenantId() == null && "GAS_STATION".equals(existing.getCode())) {
            dto.setCode("GAS_STATION");
            dto.setIsActive(true);
            dto.setSiteTypeCode("RETAIL");
        }
    }

    private void assertCodeAvailable(String code, String tenantId, Long excludeId) {
        Optional<RetailerType> found = tenantId == null
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
