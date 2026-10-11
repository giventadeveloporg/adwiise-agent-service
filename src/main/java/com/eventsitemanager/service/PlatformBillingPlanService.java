package com.eventsitemanager.service;

import com.eventsitemanager.domain.PlatformBillingPlan;
import com.eventsitemanager.repository.PlatformBillingPlanRepository;
import com.eventsitemanager.service.dto.PlatformBillingPlanDTO;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PlatformBillingPlanService {

    private final PlatformBillingPlanRepository repository;

    public PlatformBillingPlanService(PlatformBillingPlanRepository repository) {
        this.repository = repository;
    }

    public PlatformBillingPlanDTO save(PlatformBillingPlanDTO dto) {
        PlatformBillingPlan entity = toEntity(dto);
        entity.setId(null);
        return toDto(repository.save(entity));
    }

    public PlatformBillingPlanDTO update(PlatformBillingPlanDTO dto) {
        PlatformBillingPlan existing = repository.findById(dto.getId()).orElseThrow();
        copy(dto, existing, false);
        return toDto(repository.save(existing));
    }

    public Optional<PlatformBillingPlanDTO> partialUpdate(PlatformBillingPlanDTO dto) {
        return repository
            .findById(dto.getId())
            .map(existing -> {
                copy(dto, existing, true);
                return toDto(repository.save(existing));
            });
    }

    @Transactional(readOnly = true)
    public Optional<PlatformBillingPlanDTO> findOne(Long id) {
        return repository.findById(id).map(this::toDto);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public PlatformBillingPlanDTO toDto(PlatformBillingPlan entity) {
        PlatformBillingPlanDTO dto = new PlatformBillingPlanDTO();
        dto.setId(entity.getId());
        dto.setTenantId(entity.getTenantId());
        dto.setProduct(entity.getProduct());
        dto.setName(entity.getName());
        dto.setCode(entity.getCode());
        dto.setBillingMode(entity.getBillingMode());
        dto.setBillingInterval(entity.getBillingInterval());
        dto.setListPriceUsd(entity.getListPriceUsd());
        dto.setPublished(entity.getPublished());
        dto.setFeatureSummary(entity.getFeatureSummary());
        dto.setGraduatedTiersJson(entity.getGraduatedTiersJson());
        dto.setStripeProductId(entity.getStripeProductId());
        dto.setStripePriceId(entity.getStripePriceId());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

    private PlatformBillingPlan toEntity(PlatformBillingPlanDTO dto) {
        PlatformBillingPlan entity = new PlatformBillingPlan();
        copy(dto, entity, false);
        return entity;
    }

    private void copy(PlatformBillingPlanDTO dto, PlatformBillingPlan entity, boolean partial) {
        if (!partial || dto.getTenantId() != null) entity.setTenantId(blankToNull(dto.getTenantId()));
        if (!partial || dto.getProduct() != null) entity.setProduct(dto.getProduct());
        if (!partial || dto.getName() != null) entity.setName(dto.getName());
        if (!partial || dto.getCode() != null) entity.setCode(dto.getCode());
        if (!partial || dto.getBillingMode() != null) entity.setBillingMode(dto.getBillingMode());
        if (!partial || dto.getBillingInterval() != null) entity.setBillingInterval(blankToNull(dto.getBillingInterval()));
        if (!partial || dto.getListPriceUsd() != null) entity.setListPriceUsd(dto.getListPriceUsd());
        if (!partial || dto.getPublished() != null) entity.setPublished(dto.getPublished());
        if (!partial || dto.getFeatureSummary() != null) entity.setFeatureSummary(dto.getFeatureSummary());
        if (!partial || dto.getGraduatedTiersJson() != null) entity.setGraduatedTiersJson(dto.getGraduatedTiersJson());
        if (!partial || dto.getStripeProductId() != null) entity.setStripeProductId(blankToNull(dto.getStripeProductId()));
        if (!partial || dto.getStripePriceId() != null) entity.setStripePriceId(blankToNull(dto.getStripePriceId()));
        if (dto.getCreatedAt() != null) entity.setCreatedAt(dto.getCreatedAt());
        if (dto.getUpdatedAt() != null) entity.setUpdatedAt(dto.getUpdatedAt());
    }

    private String blankToNull(String value) {
        if (value == null || value.isBlank()) return null;
        return value;
    }
}
