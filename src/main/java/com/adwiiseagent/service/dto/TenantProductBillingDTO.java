package com.adwiiseagent.service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;

public class TenantProductBillingDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String tenantId;

    @NotNull
    @Size(max = 32)
    private String product;

    private Boolean chargeEnabled;
    private String status;
    private Long platformPlanId;
    private BigDecimal customFeeUsd;
    private String billingMode;
    private String billingInterval;
    private Integer intervalCount;
    private LocalDate reviewByDate;
    private Integer graceDays;
    private ZonedDateTime graceEndsAt;
    private String notes;
    private String stripePriceId;
    private String stripeSubscriptionId;
    private String stripePaymentId;
    private LocalDate paidThroughDate;
    private Integer locationQuantity;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

    /** When true on a partial update, customFeeUsd is stored even if it is null. */
    private Boolean clearCustomFee;
    private Long organizationId;
    private String organizationStripeCustomerId;
    private BigDecimal organizationMonthlyFeeUsd;
    private Boolean organizationFlatFee;
    private String organizationBillingScope;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Boolean getChargeEnabled() {
        return chargeEnabled;
    }

    public void setChargeEnabled(Boolean chargeEnabled) {
        this.chargeEnabled = chargeEnabled;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getPlatformPlanId() {
        return platformPlanId;
    }

    public void setPlatformPlanId(Long platformPlanId) {
        this.platformPlanId = platformPlanId;
    }

    public BigDecimal getCustomFeeUsd() {
        return customFeeUsd;
    }

    public void setCustomFeeUsd(BigDecimal customFeeUsd) {
        this.customFeeUsd = customFeeUsd;
    }

    public String getBillingMode() {
        return billingMode;
    }

    public void setBillingMode(String billingMode) {
        this.billingMode = billingMode;
    }

    public String getBillingInterval() {
        return billingInterval;
    }

    public void setBillingInterval(String billingInterval) {
        this.billingInterval = billingInterval;
    }

    public Integer getIntervalCount() {
        return intervalCount;
    }

    public void setIntervalCount(Integer intervalCount) {
        this.intervalCount = intervalCount;
    }

    public LocalDate getReviewByDate() {
        return reviewByDate;
    }

    public void setReviewByDate(LocalDate reviewByDate) {
        this.reviewByDate = reviewByDate;
    }

    public Integer getGraceDays() {
        return graceDays;
    }

    public void setGraceDays(Integer graceDays) {
        this.graceDays = graceDays;
    }

    public ZonedDateTime getGraceEndsAt() {
        return graceEndsAt;
    }

    public void setGraceEndsAt(ZonedDateTime graceEndsAt) {
        this.graceEndsAt = graceEndsAt;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getStripePriceId() {
        return stripePriceId;
    }

    public void setStripePriceId(String stripePriceId) {
        this.stripePriceId = stripePriceId;
    }

    public String getStripeSubscriptionId() {
        return stripeSubscriptionId;
    }

    public void setStripeSubscriptionId(String stripeSubscriptionId) {
        this.stripeSubscriptionId = stripeSubscriptionId;
    }

    public String getStripePaymentId() {
        return stripePaymentId;
    }

    public void setStripePaymentId(String stripePaymentId) {
        this.stripePaymentId = stripePaymentId;
    }

    public LocalDate getPaidThroughDate() {
        return paidThroughDate;
    }

    public void setPaidThroughDate(LocalDate paidThroughDate) {
        this.paidThroughDate = paidThroughDate;
    }

    public Integer getLocationQuantity() {
        return locationQuantity;
    }

    public void setLocationQuantity(Integer locationQuantity) {
        this.locationQuantity = locationQuantity;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Boolean getClearCustomFee() {
        return clearCustomFee;
    }

    public void setClearCustomFee(Boolean clearCustomFee) {
        this.clearCustomFee = clearCustomFee;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrganizationStripeCustomerId() {
        return organizationStripeCustomerId;
    }

    public void setOrganizationStripeCustomerId(String organizationStripeCustomerId) {
        this.organizationStripeCustomerId = organizationStripeCustomerId;
    }

    public BigDecimal getOrganizationMonthlyFeeUsd() {
        return organizationMonthlyFeeUsd;
    }

    public void setOrganizationMonthlyFeeUsd(BigDecimal organizationMonthlyFeeUsd) {
        this.organizationMonthlyFeeUsd = organizationMonthlyFeeUsd;
    }

    public Boolean getOrganizationFlatFee() {
        return organizationFlatFee;
    }

    public void setOrganizationFlatFee(Boolean organizationFlatFee) {
        this.organizationFlatFee = organizationFlatFee;
    }

    public String getOrganizationBillingScope() {
        return organizationBillingScope;
    }

    public void setOrganizationBillingScope(String organizationBillingScope) {
        this.organizationBillingScope = organizationBillingScope;
    }
}
