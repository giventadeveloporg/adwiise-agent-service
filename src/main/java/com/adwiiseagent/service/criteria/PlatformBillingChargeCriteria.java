package com.adwiiseagent.service.criteria;

import java.io.Serializable;
import tech.jhipster.service.filter.BigDecimalFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;
import tech.jhipster.service.filter.ZonedDateTimeFilter;

public class PlatformBillingChargeCriteria implements Serializable {

    private LongFilter id;
    private LongFilter organizationId;
    private StringFilter tenantId;
    private StringFilter product;
    private StringFilter billingScope;
    private StringFilter status;
    private StringFilter stripeSubscriptionId;
    private StringFilter stripeInvoiceId;
    private StringFilter stripeEventId;
    private ZonedDateTimeFilter occurredAt;
    private StringFilter stripePaymentIntentId;
    private StringFilter planCode;
    private LongFilter platformPlanId;
    private BigDecimalFilter amountUsd;
    private ZonedDateTimeFilter periodEnd;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(LongFilter organizationId) {
        this.organizationId = organizationId;
    }

    public StringFilter getTenantId() {
        return tenantId;
    }

    public void setTenantId(StringFilter tenantId) {
        this.tenantId = tenantId;
    }

    public StringFilter getProduct() {
        return product;
    }

    public void setProduct(StringFilter product) {
        this.product = product;
    }

    public StringFilter getBillingScope() {
        return billingScope;
    }

    public void setBillingScope(StringFilter billingScope) {
        this.billingScope = billingScope;
    }

    public StringFilter getStatus() {
        return status;
    }

    public void setStatus(StringFilter status) {
        this.status = status;
    }

    public StringFilter getStripeSubscriptionId() {
        return stripeSubscriptionId;
    }

    public void setStripeSubscriptionId(StringFilter stripeSubscriptionId) {
        this.stripeSubscriptionId = stripeSubscriptionId;
    }

    public StringFilter getStripeInvoiceId() {
        return stripeInvoiceId;
    }

    public void setStripeInvoiceId(StringFilter stripeInvoiceId) {
        this.stripeInvoiceId = stripeInvoiceId;
    }

    public StringFilter getStripeEventId() {
        return stripeEventId;
    }

    public void setStripeEventId(StringFilter stripeEventId) {
        this.stripeEventId = stripeEventId;
    }

    public ZonedDateTimeFilter getOccurredAt() {
        return occurredAt;
    }

    public void setOccurredAt(ZonedDateTimeFilter occurredAt) {
        this.occurredAt = occurredAt;
    }

    public StringFilter getStripePaymentIntentId() {
        return stripePaymentIntentId;
    }

    public void setStripePaymentIntentId(StringFilter stripePaymentIntentId) {
        this.stripePaymentIntentId = stripePaymentIntentId;
    }

    public StringFilter getPlanCode() {
        return planCode;
    }

    public void setPlanCode(StringFilter planCode) {
        this.planCode = planCode;
    }

    public LongFilter getPlatformPlanId() {
        return platformPlanId;
    }

    public void setPlatformPlanId(LongFilter platformPlanId) {
        this.platformPlanId = platformPlanId;
    }

    public BigDecimalFilter getAmountUsd() {
        return amountUsd;
    }

    public void setAmountUsd(BigDecimalFilter amountUsd) {
        this.amountUsd = amountUsd;
    }

    public ZonedDateTimeFilter getPeriodEnd() {
        return periodEnd;
    }

    public void setPeriodEnd(ZonedDateTimeFilter periodEnd) {
        this.periodEnd = periodEnd;
    }
}
