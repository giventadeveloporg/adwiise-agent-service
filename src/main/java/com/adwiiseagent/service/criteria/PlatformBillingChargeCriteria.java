package com.adwiiseagent.service.criteria;

import java.io.Serializable;
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
}
