package com.adwiiseagent.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;

@Entity
@Table(name = "tenant_product_billing")
public class TenantProductBilling implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tenantProductBillingSeq")
    @SequenceGenerator(name = "tenantProductBillingSeq", sequenceName = "public.tenant_product_billing_id_seq", allocationSize = 1)
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "tenant_id", length = 255, nullable = false)
    private String tenantId;

    @NotNull
    @Size(max = 32)
    @Column(name = "product", length = 32, nullable = false)
    private String product;

    @NotNull
    @Column(name = "charge_enabled", nullable = false)
    private Boolean chargeEnabled = Boolean.FALSE;

    @NotNull
    @Size(max = 32)
    @Column(name = "status", length = 32, nullable = false)
    private String status = "COMPLIMENTARY";

    @Column(name = "platform_plan_id")
    private Long platformPlanId;

    @Column(name = "custom_fee_usd", precision = 21, scale = 2)
    private BigDecimal customFeeUsd;

    @Size(max = 20)
    @Column(name = "billing_mode", length = 20)
    private String billingMode;

    @Column(name = "review_by_date")
    private LocalDate reviewByDate;

    @NotNull
    @Column(name = "grace_days", nullable = false)
    private Integer graceDays = 14;

    @Column(name = "grace_ends_at")
    private ZonedDateTime graceEndsAt;

    @Size(max = 2000)
    @Column(name = "notes", length = 2000)
    private String notes;

    @Size(max = 255)
    @Column(name = "stripe_price_id", length = 255)
    private String stripePriceId;

    @Size(max = 255)
    @Column(name = "stripe_subscription_id", length = 255)
    private String stripeSubscriptionId;

    @Size(max = 255)
    @Column(name = "stripe_payment_id", length = 255)
    private String stripePaymentId;

    @Column(name = "paid_through_date")
    private LocalDate paidThroughDate;

    @Column(name = "location_quantity")
    private Integer locationQuantity;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private ZonedDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        ZonedDateTime now = ZonedDateTime.now();
        if (createdAt == null) createdAt = now;
        if (updatedAt == null) updatedAt = now;
        if (chargeEnabled == null) chargeEnabled = Boolean.FALSE;
        if (status == null || status.isBlank()) status = "COMPLIMENTARY";
        if (graceDays == null) graceDays = 14;
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = ZonedDateTime.now();
    }

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
}
