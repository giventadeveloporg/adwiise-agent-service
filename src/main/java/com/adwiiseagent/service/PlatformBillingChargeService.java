package com.adwiiseagent.service;

import com.adwiiseagent.domain.PlatformBillingCharge;
import com.adwiiseagent.repository.PlatformBillingChargeRepository;
import com.adwiiseagent.service.dto.PlatformBillingChargeDTO;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PlatformBillingChargeService {

    private final PlatformBillingChargeRepository repository;

    public PlatformBillingChargeService(PlatformBillingChargeRepository repository) {
        this.repository = repository;
    }

    public PlatformBillingChargeDTO save(PlatformBillingChargeDTO dto) {
        if (dto.getStripeEventId() != null && !dto.getStripeEventId().isBlank()) {
            Optional<PlatformBillingCharge> existing = repository.findByStripeEventId(dto.getStripeEventId());
            if (existing.isPresent()) {
                return toDto(existing.orElseThrow());
            }
        }
        PlatformBillingCharge entity = new PlatformBillingCharge();
        copy(dto, entity, false);
        entity.setId(null);
        return toDto(repository.save(entity));
    }

    public PlatformBillingChargeDTO update(PlatformBillingChargeDTO dto) {
        PlatformBillingCharge existing = repository.findById(dto.getId()).orElseThrow();
        copy(dto, existing, false);
        return toDto(repository.save(existing));
    }

    public Optional<PlatformBillingChargeDTO> partialUpdate(PlatformBillingChargeDTO dto) {
        return repository
            .findById(dto.getId())
            .map(existing -> {
                copy(dto, existing, true);
                return toDto(repository.save(existing));
            });
    }

    @Transactional(readOnly = true)
    public Optional<PlatformBillingChargeDTO> findOne(Long id) {
        return repository.findById(id).map(this::toDto);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public PlatformBillingChargeDTO toDto(PlatformBillingCharge entity) {
        PlatformBillingChargeDTO dto = new PlatformBillingChargeDTO();
        dto.setId(entity.getId());
        dto.setOrganizationId(entity.getOrganizationId());
        dto.setTenantId(entity.getTenantId());
        dto.setProduct(entity.getProduct());
        dto.setBillingScope(entity.getBillingScope());
        dto.setPlatformPlanId(entity.getPlatformPlanId());
        dto.setPlanCode(entity.getPlanCode());
        dto.setAmountUsd(entity.getAmountUsd());
        dto.setCurrency(entity.getCurrency());
        dto.setBillingInterval(entity.getBillingInterval());
        dto.setIntervalCount(entity.getIntervalCount());
        dto.setStripeEventId(entity.getStripeEventId());
        dto.setStripeInvoiceId(entity.getStripeInvoiceId());
        dto.setStripeSubscriptionId(entity.getStripeSubscriptionId());
        dto.setStripePaymentIntentId(entity.getStripePaymentIntentId());
        dto.setStatus(entity.getStatus());
        dto.setPeriodStart(entity.getPeriodStart());
        dto.setPeriodEnd(entity.getPeriodEnd());
        dto.setOccurredAt(entity.getOccurredAt());
        dto.setFailureReason(entity.getFailureReason());
        dto.setNotes(entity.getNotes());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }

    private void copy(PlatformBillingChargeDTO dto, PlatformBillingCharge entity, boolean partial) {
        if (!partial || dto.getOrganizationId() != null) entity.setOrganizationId(dto.getOrganizationId());
        if (!partial || dto.getTenantId() != null) entity.setTenantId(blankToNull(dto.getTenantId()));
        if (!partial || dto.getProduct() != null) entity.setProduct(dto.getProduct());
        if (!partial || dto.getBillingScope() != null) entity.setBillingScope(dto.getBillingScope());
        if (!partial || dto.getPlatformPlanId() != null) entity.setPlatformPlanId(dto.getPlatformPlanId());
        if (!partial || dto.getPlanCode() != null) entity.setPlanCode(blankToNull(dto.getPlanCode()));
        if (!partial || dto.getAmountUsd() != null) entity.setAmountUsd(dto.getAmountUsd());
        if (!partial || dto.getCurrency() != null) entity.setCurrency(dto.getCurrency());
        if (!partial || dto.getBillingInterval() != null) entity.setBillingInterval(blankToNull(dto.getBillingInterval()));
        if (!partial || dto.getIntervalCount() != null) entity.setIntervalCount(dto.getIntervalCount());
        if (!partial || dto.getStripeEventId() != null) entity.setStripeEventId(blankToNull(dto.getStripeEventId()));
        if (!partial || dto.getStripeInvoiceId() != null) entity.setStripeInvoiceId(blankToNull(dto.getStripeInvoiceId()));
        if (!partial || dto.getStripeSubscriptionId() != null) entity.setStripeSubscriptionId(blankToNull(dto.getStripeSubscriptionId()));
        if (!partial || dto.getStripePaymentIntentId() != null) entity.setStripePaymentIntentId(
            blankToNull(dto.getStripePaymentIntentId())
        );
        if (!partial || dto.getStatus() != null) entity.setStatus(dto.getStatus());
        if (!partial || dto.getPeriodStart() != null) entity.setPeriodStart(dto.getPeriodStart());
        if (!partial || dto.getPeriodEnd() != null) entity.setPeriodEnd(dto.getPeriodEnd());
        if (!partial || dto.getOccurredAt() != null) entity.setOccurredAt(dto.getOccurredAt());
        if (!partial || dto.getFailureReason() != null) entity.setFailureReason(dto.getFailureReason());
        if (!partial || dto.getNotes() != null) entity.setNotes(dto.getNotes());
        if (dto.getCreatedAt() != null) entity.setCreatedAt(dto.getCreatedAt());
    }

    private String blankToNull(String value) {
        if (value == null || value.isBlank()) return null;
        return value;
    }
}
