package com.eventsitemanager.service.criteria;

import java.io.Serializable;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.LocalDateFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RetailIntegrationCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;
    private StringFilter tenantId;
    private LongFilter locationId;
    private StringFilter systemType;
    private BooleanFilter isEnabled;
    private Boolean distinct;

    public RetailIntegrationCriteria() {}

    public RetailIntegrationCriteria(RetailIntegrationCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.tenantId = other.tenantId == null ? null : other.tenantId.copy();
        this.locationId = other.locationId == null ? null : other.locationId.copy();
        this.systemType = other.systemType == null ? null : other.systemType.copy();
        this.isEnabled = other.isEnabled == null ? null : other.isEnabled.copy();
        this.distinct = other.distinct;
    }

    @Override
    public RetailIntegrationCriteria copy() {
        return new RetailIntegrationCriteria(this);
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

    public LongFilter getLocationId() {
        return locationId;
    }

    public void setLocationId(LongFilter locationId) {
        this.locationId = locationId;
    }

    public StringFilter getSystemType() {
        return systemType;
    }

    public void setSystemType(StringFilter systemType) {
        this.systemType = systemType;
    }

    public BooleanFilter getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(BooleanFilter isEnabled) {
        this.isEnabled = isEnabled;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }
}
