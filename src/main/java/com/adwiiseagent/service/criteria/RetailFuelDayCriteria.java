package com.adwiiseagent.service.criteria;

import java.io.Serializable;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.LocalDateFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RetailFuelDayCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;
    private StringFilter tenantId;
    private LongFilter locationId;
    private LocalDateFilter metricDate;
    private Boolean distinct;

    public RetailFuelDayCriteria() {}

    public RetailFuelDayCriteria(RetailFuelDayCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.tenantId = other.tenantId == null ? null : other.tenantId.copy();
        this.locationId = other.locationId == null ? null : other.locationId.copy();
        this.metricDate = other.metricDate == null ? null : other.metricDate.copy();
        this.distinct = other.distinct;
    }

    @Override
    public RetailFuelDayCriteria copy() {
        return new RetailFuelDayCriteria(this);
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

    public LocalDateFilter getMetricDate() {
        return metricDate;
    }

    public void setMetricDate(LocalDateFilter metricDate) {
        this.metricDate = metricDate;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }
}
