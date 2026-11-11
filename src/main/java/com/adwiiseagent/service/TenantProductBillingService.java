package com.adwiiseagent.service;

import com.adwiiseagent.domain.TenantOrganization;
import com.adwiiseagent.domain.TenantProductBilling;
import com.adwiiseagent.domain.TenantSettings;
import com.adwiiseagent.errors.BadRequestAlertException;
import com.adwiiseagent.repository.TenantProductBillingRepository;
import com.adwiiseagent.repository.TenantSettingsRepository;
import com.adwiiseagent.service.dto.TenantProductBillingDTO;
import java.time.ZonedDateTime;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TenantProductBillingService {

    private final TenantProductBillingRepository repository;
    private final TenantSettingsRepository tenantSettingsRepository;

    public TenantProductBillingService(TenantProductBillingRepository repository, TenantSettingsRepository tenantSettingsRepository) {
        this.repository = repository;
        this.tenantSettingsRepository = tenantSettingsRepository;
    }

    public TenantProductBillingDTO save(TenantProductBillingDTO dto) {
        TenantProductBilling entity = new TenantProductBilling();
        copy(dto, entity, false);
        entity.setId(null);
        applyOrganizationBilling(entity);
        applyChargeSwitch(null, entity);
        return toDto(repository.save(entity));
    }

    public TenantProductBillingDTO update(TenantProductBillingDTO dto) {
        TenantProductBilling existing = repository.findById(dto.getId()).orElseThrow();
        Boolean previousSwitch = existing.getChargeEnabled();
        copy(dto, existing, false);
        applyOrganizationBilling(existing);
        applyChargeSwitch(previousSwitch, existing);
        return toDto(repository.save(existing));
    }

    public Optional<TenantProductBillingDTO> partialUpdate(TenantProductBillingDTO dto) {
        return repository
            .findById(dto.getId())
            .map(existing -> {
                Boolean previousSwitch = existing.getChargeEnabled();
                copy(dto, existing, true);
                applyOrganizationBilling(existing);
                applyChargeSwitch(previousSwitch, existing);
                return toDto(repository.save(existing));
            });
    }

    @Transactional(readOnly = true)
    public Optional<TenantProductBillingDTO> findOne(Long id) {
        return repository.findById(id).map(this::toDto);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    /**
     * Turning the switch on records payment due and a grace end. It does not call Stripe.
     * Turning it off returns the row to complimentary. A review-by date is never used here.
     */
    private void applyChargeSwitch(Boolean previousEnabled, TenantProductBilling entity) {
        boolean enabled = Boolean.TRUE.equals(entity.getChargeEnabled());
        boolean wasEnabled = Boolean.TRUE.equals(previousEnabled);
        if (enabled && !wasEnabled) {
            if (entity.getStatus() == null || "COMPLIMENTARY".equals(entity.getStatus()) || "LOCKED".equals(entity.getStatus())) {
                entity.setStatus("PAYMENT_DUE");
            }
            if (entity.getGraceEndsAt() == null) {
                int days = entity.getGraceDays() == null ? 14 : entity.getGraceDays();
                entity.setGraceEndsAt(ZonedDateTime.now().plusDays(days));
            }
        }
        if (!enabled && wasEnabled) {
            entity.setStatus("COMPLIMENTARY");
            entity.setGraceEndsAt(null);
        }
        if (!enabled && previousEnabled == null) {
            entity.setChargeEnabled(Boolean.FALSE);
            if (entity.getStatus() == null || entity.getStatus().isBlank()) {
                entity.setStatus("COMPLIMENTARY");
            }
        }
    }

    public TenantProductBillingDTO toDto(TenantProductBilling entity) {
        TenantProductBillingDTO dto = new TenantProductBillingDTO();
        dto.setId(entity.getId());
        dto.setTenantId(entity.getTenantId());
        dto.setProduct(entity.getProduct());
        dto.setChargeEnabled(entity.getChargeEnabled());
        dto.setStatus(entity.getStatus());
        dto.setPlatformPlanId(entity.getPlatformPlanId());
        dto.setCustomFeeUsd(entity.getCustomFeeUsd());
        dto.setBillingMode(entity.getBillingMode());
        dto.setReviewByDate(entity.getReviewByDate());
        dto.setGraceDays(entity.getGraceDays());
        dto.setGraceEndsAt(entity.getGraceEndsAt());
        dto.setNotes(entity.getNotes());
        dto.setStripePriceId(entity.getStripePriceId());
        dto.setStripeSubscriptionId(entity.getStripeSubscriptionId());
        dto.setStripePaymentId(entity.getStripePaymentId());
        dto.setPaidThroughDate(entity.getPaidThroughDate());
        dto.setLocationQuantity(entity.getLocationQuantity());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        attachOrganization(dto, entity.getTenantId());
        return dto;
    }

    private void applyOrganizationBilling(TenantProductBilling entity) {
        TenantOrganization organization = owningOrganization(entity.getTenantId());
        if (organization.getMonthlyFeeUsd() != null) {
            entity.setChargeEnabled(Boolean.FALSE);
            entity.setStatus("COMPLIMENTARY");
            entity.setStripeSubscriptionId(null);
            entity.setCustomFeeUsd(null);
            String marker = "covered-by-organization:" + organization.getId();
            String notes = entity.getNotes();
            if (notes == null || notes.isBlank()) {
                entity.setNotes(marker);
            } else if (!notes.contains(marker)) {
                entity.setNotes(notes + "\n" + marker);
            }
            return;
        }
        if (Boolean.TRUE.equals(entity.getChargeEnabled())) {
            String customer = organization.getStripeCustomerId();
            if (customer == null || customer.isBlank()) {
                throw new BadRequestAlertException(
                    "The organization needs a Stripe customer before a website can be charged",
                    "tenantProductBilling",
                    "organizationcustomerrequired"
                );
            }
        }
    }

    private void attachOrganization(TenantProductBillingDTO dto, String tenantId) {
        if (tenantId == null || tenantId.isBlank()) {
            return;
        }
        tenantSettingsRepository
            .findByTenantId(tenantId)
            .map(TenantSettings::getTenantOrganization)
            .ifPresent(organization -> {
                dto.setOrganizationId(organization.getId());
                dto.setOrganizationStripeCustomerId(organization.getStripeCustomerId());
                dto.setOrganizationMonthlyFeeUsd(organization.getMonthlyFeeUsd());
                dto.setOrganizationFlatFee(organization.getMonthlyFeeUsd() != null);
            });
    }

    private TenantOrganization owningOrganization(String tenantId) {
        TenantSettings settings = tenantSettingsRepository
            .findByTenantId(tenantId)
            .orElseThrow(() -> new BadRequestAlertException("Website tenant was not found", "tenantProductBilling", "tenantnotfound"));
        TenantOrganization organization = settings.getTenantOrganization();
        if (organization == null || organization.getId() == null) {
            throw new BadRequestAlertException("Website has no organization", "tenantProductBilling", "organizationrequired");
        }
        return organization;
    }

    private void copy(TenantProductBillingDTO dto, TenantProductBilling entity, boolean partial) {
        if (!partial || dto.getTenantId() != null) entity.setTenantId(dto.getTenantId());
        if (!partial || dto.getProduct() != null) entity.setProduct(dto.getProduct());
        if (!partial || dto.getChargeEnabled() != null) entity.setChargeEnabled(dto.getChargeEnabled());
        if (!partial || dto.getStatus() != null) entity.setStatus(dto.getStatus());
        if (!partial || dto.getPlatformPlanId() != null) entity.setPlatformPlanId(dto.getPlatformPlanId());
        if (Boolean.TRUE.equals(dto.getClearCustomFee())) {
            entity.setCustomFeeUsd(null);
        } else if (!partial || dto.getCustomFeeUsd() != null) {
            entity.setCustomFeeUsd(dto.getCustomFeeUsd());
        }
        if (!partial || dto.getBillingMode() != null) entity.setBillingMode(blankToNull(dto.getBillingMode()));
        if (!partial || dto.getReviewByDate() != null) entity.setReviewByDate(dto.getReviewByDate());
        if (!partial || dto.getGraceDays() != null) entity.setGraceDays(dto.getGraceDays());
        if (!partial || dto.getGraceEndsAt() != null) entity.setGraceEndsAt(dto.getGraceEndsAt());
        if (!partial || dto.getNotes() != null) entity.setNotes(dto.getNotes());
        if (!partial || dto.getStripePriceId() != null) entity.setStripePriceId(blankToNull(dto.getStripePriceId()));
        if (!partial || dto.getStripeSubscriptionId() != null) entity.setStripeSubscriptionId(blankToNull(dto.getStripeSubscriptionId()));
        if (!partial || dto.getStripePaymentId() != null) entity.setStripePaymentId(blankToNull(dto.getStripePaymentId()));
        if (!partial || dto.getPaidThroughDate() != null) entity.setPaidThroughDate(dto.getPaidThroughDate());
        if (!partial || dto.getLocationQuantity() != null) entity.setLocationQuantity(dto.getLocationQuantity());
        if (dto.getCreatedAt() != null) entity.setCreatedAt(dto.getCreatedAt());
        if (dto.getUpdatedAt() != null) entity.setUpdatedAt(dto.getUpdatedAt());
    }

    private String blankToNull(String value) {
        if (value == null || value.isBlank()) return null;
        return value;
    }
}
