package com.eventsitemanager.service.criteria;

import java.io.Serializable;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

@ParameterObject
public class PlatformBillingPlanCriteria implements Serializable, Criteria {

    private LongFilter id;
    private StringFilter tenantId;
    private StringFilter product;
    private StringFilter code;
    private BooleanFilter published;

    public PlatformBillingPlanCriteria() {}

    public PlatformBillingPlanCriteria(PlatformBillingPlanCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.tenantId = other.tenantId == null ? null : other.tenantId.copy();
        this.product = other.product == null ? null : other.product.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.published = other.published == null ? null : other.published.copy();
    }

    @Override
    public PlatformBillingPlanCriteria copy() {
        return new PlatformBillingPlanCriteria(this);
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

    public StringFilter getCode() {
        return code;
    }

    public void setCode(StringFilter code) {
        this.code = code;
    }

    public BooleanFilter getPublished() {
        return published;
    }

    public void setPublished(BooleanFilter published) {
        this.published = published;
    }
}
