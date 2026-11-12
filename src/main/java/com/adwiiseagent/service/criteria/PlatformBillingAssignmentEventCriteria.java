package com.adwiiseagent.service.criteria;

import java.io.Serializable;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;
import tech.jhipster.service.filter.ZonedDateTimeFilter;

public class PlatformBillingAssignmentEventCriteria implements Serializable {

    private LongFilter id;
    private LongFilter organizationId;
    private StringFilter tenantId;
    private StringFilter product;
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

    public ZonedDateTimeFilter getOccurredAt() {
        return occurredAt;
    }

    public void setOccurredAt(ZonedDateTimeFilter occurredAt) {
        this.occurredAt = occurredAt;
    }
}
