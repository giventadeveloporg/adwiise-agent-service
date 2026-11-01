package com.adwiiseagent.service;

import com.adwiiseagent.domain.RetailerSizeBand;
import com.adwiiseagent.domain.TenantOrganization;
import com.adwiiseagent.errors.BadRequestAlertException;
import com.adwiiseagent.repository.PlatformBillingPlanRepository;
import com.adwiiseagent.repository.RetailLocationRepository;
import com.adwiiseagent.repository.RetailerSizeBandRepository;
import com.adwiiseagent.repository.TenantOrganizationRepository;
import com.adwiiseagent.service.dto.RetailSizeResolutionDTO;
import com.adwiiseagent.service.dto.RetailerSizeBandDTO;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RetailerSizeBandService {

    private static final String ENTITY = "retailerSizeBand";
    private static final String BILLING_PRODUCT = "RETAIL";

    private final RetailerSizeBandRepository repository;
    private final RetailLocationRepository locationRepository;
    private final TenantOrganizationRepository organizationRepository;
    private final PlatformBillingPlanRepository billingPlanRepository;
    private final RosterCatalogGuard guard;

    public RetailerSizeBandService(
        RetailerSizeBandRepository repository,
        RetailLocationRepository locationRepository,
        TenantOrganizationRepository organizationRepository,
        PlatformBillingPlanRepository billingPlanRepository,
        RosterCatalogGuard guard
    ) {
        this.repository = repository;
        this.locationRepository = locationRepository;
        this.organizationRepository = organizationRepository;
        this.billingPlanRepository = billingPlanRepository;
        this.guard = guard;
    }

    public RetailerSizeBandDTO save(RetailerSizeBandDTO dto) {
        normalize(dto);
        guard.assertWrite(false, null, null);
        if (repository.findByRetailerTypeCodeAndCode(dto.getRetailerTypeCode(), dto.getCode()).isPresent()) {
            throw new BadRequestAlertException("This size band already exists", ENTITY, "codeexists");
        }
        return toDto(repository.save(toEntity(dto, new RetailerSizeBand())));
    }

    public RetailerSizeBandDTO update(RetailerSizeBandDTO dto) {
        normalize(dto);
        RetailerSizeBand existing = repository
            .findById(dto.getId())
            .orElseThrow(() -> new BadRequestAlertException("Entity not found", ENTITY, "idnotfound"));
        guard.assertWrite(true, null, null);
        repository
            .findByRetailerTypeCodeAndCode(dto.getRetailerTypeCode(), dto.getCode())
            .filter(other -> !other.getId().equals(existing.getId()))
            .ifPresent(other -> {
                throw new BadRequestAlertException("This size band already exists", ENTITY, "codeexists");
            });
        return toDto(repository.save(toEntity(dto, existing)));
    }

    @Transactional(readOnly = true)
    public List<RetailerSizeBandDTO> findAll() {
        return repository.findAllByOrderByRetailerTypeCodeAscSortOrderAsc().stream().map(this::toDto).toList();
    }

    @Transactional(readOnly = true)
    public Optional<RetailerSizeBandDTO> findOne(Long id) {
        return repository.findById(id).map(this::toDto);
    }

    public void delete(Long id) {
        guard.assertWrite(true, null, null);
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public RetailSizeResolutionDTO resolve(String tenantId, String retailerTypeCode) {
        String pack = canonicalPack(retailerTypeCode);
        if (pack == null) {
            throw new BadRequestAlertException("retailerType is required", ENTITY, "packrequired");
        }
        long count = 0;
        String override = null;
        if (tenantId != null && !tenantId.isBlank()) {
            count = locationRepository.countByTenantIdAndRetailerTypeCode(tenantId.trim(), pack);
            override =
                organizationRepository
                    .findOneByTenantId(tenantId.trim())
                    .map(TenantOrganization::getRetailerSizeBandCode)
                    .filter(code -> code != null && !code.isBlank())
                    .map(code -> code.trim().toUpperCase())
                    .orElse(null);
        }
        long counted = Math.max(count, 1);
        RetailerSizeBand chosen = choose(pack, counted, override);
        RetailSizeResolutionDTO result = new RetailSizeResolutionDTO();
        result.setRetailerTypeCode(pack);
        result.setLocationCount(count);
        result.setOverridden(override != null && chosen != null && override.equals(chosen.getCode()));
        result.setSizeBandCode(chosen == null ? null : chosen.getCode());
        result.setBillingProduct(BILLING_PRODUCT);
        if (chosen != null) {
            String billingCode = pack + "_" + chosen.getCode();
            result.setBillingCode(
                billingPlanRepository.findFirstByProductAndCode(BILLING_PRODUCT, billingCode).isPresent() ? billingCode : null
            );
        }
        return result;
    }

    private RetailerSizeBand choose(String pack, long count, String override) {
        List<RetailerSizeBand> bands = repository
            .findAllByOrderByRetailerTypeCodeAscSortOrderAsc()
            .stream()
            .filter(band -> pack.equals(band.getRetailerTypeCode()) && Boolean.TRUE.equals(band.getIsActive()))
            .toList();
        if (override != null) {
            Optional<RetailerSizeBand> forced = bands.stream().filter(band -> override.equals(band.getCode())).findFirst();
            if (forced.isPresent()) {
                return forced.orElseThrow();
            }
        }
        return bands
            .stream()
            .filter(band -> band.getMinLocations() != null && count >= band.getMinLocations())
            .filter(band -> band.getMaxLocations() == null || count <= band.getMaxLocations())
            .max(Comparator.comparingInt(RetailerSizeBand::getMinLocations))
            .orElse(null);
    }

    static String canonicalPack(String retailerTypeCode) {
        if (retailerTypeCode == null || retailerTypeCode.isBlank()) {
            return null;
        }
        String code = retailerTypeCode.trim().toUpperCase();
        return "GAS_STATION_ENT".equals(code) ? "GAS_STATION" : code;
    }

    private void normalize(RetailerSizeBandDTO dto) {
        dto.setRetailerTypeCode(canonicalPack(dto.getRetailerTypeCode()));
        dto.setCode(RosterCatalogGuard.normalizeCode(dto.getCode()).toUpperCase());
        if (dto.getRetailerTypeCode() == null || dto.getCode() == null) {
            throw new BadRequestAlertException("retailer type and code are required", ENTITY, "coderequired");
        }
        if (dto.getLabel() == null || dto.getLabel().isBlank()) {
            throw new BadRequestAlertException("label is required", ENTITY, "labelrequired");
        }
        dto.setLabel(dto.getLabel().trim());
        if (dto.getMinLocations() == null || dto.getMinLocations() < 1) {
            dto.setMinLocations(1);
        }
        if (dto.getMaxLocations() != null && dto.getMaxLocations() < dto.getMinLocations()) {
            throw new BadRequestAlertException("max locations cannot be below min locations", ENTITY, "badrange");
        }
        if (dto.getSortOrder() == null) {
            dto.setSortOrder(0);
        }
        if (dto.getIsActive() == null) {
            dto.setIsActive(true);
        }
    }

    private RetailerSizeBand toEntity(RetailerSizeBandDTO dto, RetailerSizeBand entity) {
        entity.setRetailerTypeCode(dto.getRetailerTypeCode());
        entity.setCode(dto.getCode());
        entity.setLabel(dto.getLabel());
        entity.setMinLocations(dto.getMinLocations());
        entity.setMaxLocations(dto.getMaxLocations());
        entity.setSortOrder(dto.getSortOrder());
        entity.setIsActive(dto.getIsActive());
        return entity;
    }

    private RetailerSizeBandDTO toDto(RetailerSizeBand entity) {
        RetailerSizeBandDTO dto = new RetailerSizeBandDTO();
        dto.setId(entity.getId());
        dto.setRetailerTypeCode(entity.getRetailerTypeCode());
        dto.setCode(entity.getCode());
        dto.setLabel(entity.getLabel());
        dto.setMinLocations(entity.getMinLocations());
        dto.setMaxLocations(entity.getMaxLocations());
        dto.setSortOrder(entity.getSortOrder());
        dto.setIsActive(entity.getIsActive());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }
}
