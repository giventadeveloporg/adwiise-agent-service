package com.adwiiseagent.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Entity
@Table(name = "platform_billing_charge")
public class PlatformBillingCharge implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "platformBillingChargeSeq")
    @SequenceGenerator(name = "platformBillingChargeSeq", sequenceName = "public.platform_billing_charge_id_seq", allocationSize = 1)
    private Long id;

    @NotNull
    @Column(name = "organization_id", nullable = false)
    private Long organizationId;

    @Size(max = 255)
    @Column(name = "tenant_id", length = 255)
    private String tenantId;

    @NotNull
    @Size(max = 32)
    @Column(name = "product", length = 32, nullable = false)
    private String product;

    @NotNull
    @Size(max = 20)
    @Column(name = "billing_scope", length = 20, nullable = false)
    private String billingScope;

    @Column(name = "platform_plan_id")
    private Long platformPlanId;

    @Size(max = 64)
    @Column(name = "plan_code", length = 64)
    private String planCode;

    @NotNull
    @Column(name = "amount_usd", precision = 21, scale = 2, nullable = false)
    private BigDecimal amountUsd;

    @NotNull
    @Size(max = 3)
    @Column(name = "currency", length = 3, nullable = false)
    private String currency = "USD";

    @Size(max = 20)
    @Column(name = "billing_interval", length = 20)
    private String billingInterval;

    @NotNull
    @Column(name = "interval_count", nullable = false)
    private Integer intervalCount = 1;

    @Size(max = 255)
    @Column(name = "stripe_event_id", length = 255)
    private String stripeEventId;

    @Size(max = 255)
    @Column(name = "stripe_invoice_id", length = 255)
    private String stripeInvoiceId;

    @Size(max = 255)
    @Column(name = "stripe_subscription_id", length = 255)
    private String stripeSubscriptionId;

    @Size(max = 255)
    @Column(name = "stripe_payment_intent_id", length = 255)
    private String stripePaymentIntentId;

    @NotNull
    @Size(max = 20)
    @Column(name = "status", length = 20, nullable = false)
    private String status;

    @Column(name = "period_start")
    private ZonedDateTime periodStart;

    @Column(name = "period_end")
    private ZonedDateTime periodEnd;

    @NotNull
    @Column(name = "occurred_at", nullable = false)
    private ZonedDateTime occurredAt;

    @Size(max = 2000)
    @Column(name = "failure_reason", length = 2000)
    private String failureReason;

    @Size(max = 2000)
    @Column(name = "notes", length = 2000)
    private String notes;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;

    @PrePersist
    public void prePersist() {
        ZonedDateTime now = ZonedDateTime.now();
        if (occurredAt == null) occurredAt = now;
        if (createdAt == null) createdAt = now;
        if (currency == null || currency.isBlank()) currency = "USD";
        if (intervalCount == null || intervalCount < 1) intervalCount = 1;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
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

    public String getBillingScope() {
        return billingScope;
    }

    public void setBillingScope(String billingScope) {
        this.billingScope = billingScope;
    }

    public Long getPlatformPlanId() {
        return platformPlanId;
    }

    public void setPlatformPlanId(Long platformPlanId) {
        this.platformPlanId = platformPlanId;
    }

    public String getPlanCode() {
        return planCode;
    }

    public void setPlanCode(String planCode) {
        this.planCode = planCode;
    }

    public BigDecimal getAmountUsd() {
        return amountUsd;
    }

    public void setAmountUsd(BigDecimal amountUsd) {
        this.amountUsd = amountUsd;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
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

    public String getStripeEventId() {
        return stripeEventId;
    }

    public void setStripeEventId(String stripeEventId) {
        this.stripeEventId = stripeEventId;
    }

    public String getStripeInvoiceId() {
        return stripeInvoiceId;
    }

    public void setStripeInvoiceId(String stripeInvoiceId) {
        this.stripeInvoiceId = stripeInvoiceId;
    }

    public String getStripeSubscriptionId() {
        return stripeSubscriptionId;
    }

    public void setStripeSubscriptionId(String stripeSubscriptionId) {
        this.stripeSubscriptionId = stripeSubscriptionId;
    }

    public String getStripePaymentIntentId() {
        return stripePaymentIntentId;
    }

    public void setStripePaymentIntentId(String stripePaymentIntentId) {
        this.stripePaymentIntentId = stripePaymentIntentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ZonedDateTime getPeriodStart() {
        return periodStart;
    }

    public void setPeriodStart(ZonedDateTime periodStart) {
        this.periodStart = periodStart;
    }

    public ZonedDateTime getPeriodEnd() {
        return periodEnd;
    }

    public void setPeriodEnd(ZonedDateTime periodEnd) {
        this.periodEnd = periodEnd;
    }

    public ZonedDateTime getOccurredAt() {
        return occurredAt;
    }

    public void setOccurredAt(ZonedDateTime occurredAt) {
        this.occurredAt = occurredAt;
    }

    public String getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
