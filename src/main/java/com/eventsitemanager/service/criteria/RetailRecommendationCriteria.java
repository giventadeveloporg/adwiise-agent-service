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
public class RetailRecommendationCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;
    private StringFilter tenantId;
    private LongFilter locationId;
    private LocalDateFilter recommendationDate;
    private StringFilter category;
    private StringFilter status;
    private StringFilter sourceModelRunId;
    private Boolean distinct;

    public RetailRecommendationCriteria() {}

    public RetailRecommendationCriteria(RetailRecommendationCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.tenantId = other.tenantId == null ? null : other.tenantId.copy();
        this.locationId = other.locationId == null ? null : other.locationId.copy();
        this.recommendationDate = other.recommendationDate == null ? null : other.recommendationDate.copy();
        this.category = other.category == null ? null : other.category.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.sourceModelRunId = other.sourceModelRunId == null ? null : other.sourceModelRunId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public RetailRecommendationCriteria copy() {
        return new RetailRecommendationCriteria(this);
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

    public LocalDateFilter getRecommendationDate() {
        return recommendationDate;
    }

    public void setRecommendationDate(LocalDateFilter recommendationDate) {
        this.recommendationDate = recommendationDate;
    }

    public StringFilter getCategory() {
        return category;
    }

    public void setCategory(StringFilter category) {
        this.category = category;
    }

    public StringFilter getStatus() {
        return status;
    }

    public void setStatus(StringFilter status) {
        this.status = status;
    }

    public StringFilter getSourceModelRunId() {
        return sourceModelRunId;
    }

    public void setSourceModelRunId(StringFilter sourceModelRunId) {
        this.sourceModelRunId = sourceModelRunId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }
}
