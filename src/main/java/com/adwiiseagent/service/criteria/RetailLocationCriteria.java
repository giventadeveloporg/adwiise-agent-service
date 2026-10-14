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
public class RetailLocationCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;
    private StringFilter tenantId;
    private StringFilter locationName;
    private StringFilter locationCode;
    private StringFilter region;
    private StringFilter city;
    private BooleanFilter isActive;
    private Boolean distinct;

    public RetailLocationCriteria() {}

    public RetailLocationCriteria(RetailLocationCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.tenantId = other.tenantId == null ? null : other.tenantId.copy();
        this.locationName = other.locationName == null ? null : other.locationName.copy();
        this.locationCode = other.locationCode == null ? null : other.locationCode.copy();
        this.region = other.region == null ? null : other.region.copy();
        this.city = other.city == null ? null : other.city.copy();
        this.isActive = other.isActive == null ? null : other.isActive.copy();
        this.distinct = other.distinct;
    }

    @Override
    public RetailLocationCriteria copy() {
        return new RetailLocationCriteria(this);
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

    public StringFilter getLocationName() {
        return locationName;
    }

    public void setLocationName(StringFilter locationName) {
        this.locationName = locationName;
    }

    public StringFilter getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(StringFilter locationCode) {
        this.locationCode = locationCode;
    }

    public StringFilter getRegion() {
        return region;
    }

    public void setRegion(StringFilter region) {
        this.region = region;
    }

    public StringFilter getCity() {
        return city;
    }

    public void setCity(StringFilter city) {
        this.city = city;
    }

    public BooleanFilter getIsActive() {
        return isActive;
    }

    public void setIsActive(BooleanFilter isActive) {
        this.isActive = isActive;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }
}
