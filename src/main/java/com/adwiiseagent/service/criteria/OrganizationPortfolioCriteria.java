package com.adwiiseagent.service.criteria;

import java.io.Serializable;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;
import tech.jhipster.service.filter.ZonedDateTimeFilter;

@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OrganizationPortfolioCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;
    private StringFilter portfolioName;
    private StringFilter primaryTenantId;
    private BooleanFilter isActive;
    private ZonedDateTimeFilter createdAt;
    private Boolean distinct;

    public OrganizationPortfolioCriteria() {}

    public OrganizationPortfolioCriteria(OrganizationPortfolioCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.portfolioName = other.portfolioName == null ? null : other.portfolioName.copy();
        this.primaryTenantId = other.primaryTenantId == null ? null : other.primaryTenantId.copy();
        this.isActive = other.isActive == null ? null : other.isActive.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.distinct = other.distinct;
    }

    @Override
    public OrganizationPortfolioCriteria copy() {
        return new OrganizationPortfolioCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getPortfolioName() {
        return portfolioName;
    }

    public void setPortfolioName(StringFilter portfolioName) {
        this.portfolioName = portfolioName;
    }

    public StringFilter getPrimaryTenantId() {
        return primaryTenantId;
    }

    public void setPrimaryTenantId(StringFilter primaryTenantId) {
        this.primaryTenantId = primaryTenantId;
    }

    public BooleanFilter getIsActive() {
        return isActive;
    }

    public void setIsActive(BooleanFilter isActive) {
        this.isActive = isActive;
    }

    public ZonedDateTimeFilter getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTimeFilter createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }
}
