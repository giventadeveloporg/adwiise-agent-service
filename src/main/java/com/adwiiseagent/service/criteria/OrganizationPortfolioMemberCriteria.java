package com.adwiiseagent.service.criteria;

import java.io.Serializable;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OrganizationPortfolioMemberCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;
    private LongFilter portfolioId;
    private StringFilter tenantId;
    private BooleanFilter isPrimary;
    private Boolean distinct;

    public OrganizationPortfolioMemberCriteria() {}

    public OrganizationPortfolioMemberCriteria(OrganizationPortfolioMemberCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.portfolioId = other.portfolioId == null ? null : other.portfolioId.copy();
        this.tenantId = other.tenantId == null ? null : other.tenantId.copy();
        this.isPrimary = other.isPrimary == null ? null : other.isPrimary.copy();
        this.distinct = other.distinct;
    }

    @Override
    public OrganizationPortfolioMemberCriteria copy() {
        return new OrganizationPortfolioMemberCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(LongFilter portfolioId) {
        this.portfolioId = portfolioId;
    }

    public StringFilter getTenantId() {
        return tenantId;
    }

    public void setTenantId(StringFilter tenantId) {
        this.tenantId = tenantId;
    }

    public BooleanFilter getIsPrimary() {
        return isPrimary;
    }

    public void setIsPrimary(BooleanFilter isPrimary) {
        this.isPrimary = isPrimary;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }
}
