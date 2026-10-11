package com.eventsitemanager.service.criteria;

import java.io.Serializable;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

@ParameterObject
public class TenantProductBillingCriteria implements Serializable, Criteria {

    private LongFilter id;
    private StringFilter tenantId;
    private StringFilter product;
    private StringFilter status;
    private BooleanFilter chargeEnabled;
    private StringFilter stripeSubscriptionId;

    public TenantProductBillingCriteria() {}

    public TenantProductBillingCriteria(TenantProductBillingCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.tenantId = other.tenantId == null ? null : other.tenantId.copy();
        this.product = other.product == null ? null : other.product.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.chargeEnabled = other.chargeEnabled == null ? null : other.chargeEnabled.copy();
        this.stripeSubscriptionId = other.stripeSubscriptionId == null ? null : other.stripeSubscriptionId.copy();
    }

    @Override
    public TenantProductBillingCriteria copy() {
        return new TenantProductBillingCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
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

    public StringFilter getStatus() {
        return status;
    }

    public void setStatus(StringFilter status) {
        this.status = status;
    }

    public BooleanFilter getChargeEnabled() {
        return chargeEnabled;
    }

    public void setChargeEnabled(BooleanFilter chargeEnabled) {
        this.chargeEnabled = chargeEnabled;
    }

    public StringFilter getStripeSubscriptionId() {
        return stripeSubscriptionId;
    }

    public void setStripeSubscriptionId(StringFilter stripeSubscriptionId) {
        this.stripeSubscriptionId = stripeSubscriptionId;
    }
}
