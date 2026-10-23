package com.adwiiseagent.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LocalDateFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

@ParameterObject
public class TeamMemberAchievementCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;
    private StringFilter tenantId;
    private LongFilter teamMemberId;
    private StringFilter title;
    private StringFilter description;
    private LocalDateFilter achievementDate;
    private StringFilter category;
    private StringFilter issuer;
    private IntegerFilter displayOrder;
    private BooleanFilter isFeatured;
    private Boolean distinct;

    public TeamMemberAchievementCriteria() {}

    public TeamMemberAchievementCriteria(TeamMemberAchievementCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.tenantId = other.optionalTenantId().map(StringFilter::copy).orElse(null);
        this.teamMemberId = other.optionalTeamMemberId().map(LongFilter::copy).orElse(null);
        this.title = other.optionalTitle().map(StringFilter::copy).orElse(null);
        this.description = other.optionalDescription().map(StringFilter::copy).orElse(null);
        this.achievementDate = other.optionalAchievementDate().map(LocalDateFilter::copy).orElse(null);
        this.category = other.optionalCategory().map(StringFilter::copy).orElse(null);
        this.issuer = other.optionalIssuer().map(StringFilter::copy).orElse(null);
        this.displayOrder = other.optionalDisplayOrder().map(IntegerFilter::copy).orElse(null);
        this.isFeatured = other.optionalIsFeatured().map(BooleanFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public TeamMemberAchievementCriteria copy() {
        return new TeamMemberAchievementCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public Optional<LongFilter> optionalId() {
        return Optional.ofNullable(id);
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTenantId() {
        return tenantId;
    }

    public Optional<StringFilter> optionalTenantId() {
        return Optional.ofNullable(tenantId);
    }

    public void setTenantId(StringFilter tenantId) {
        this.tenantId = tenantId;
    }

    public LongFilter getTeamMemberId() {
        return teamMemberId;
    }

    public Optional<LongFilter> optionalTeamMemberId() {
        return Optional.ofNullable(teamMemberId);
    }

    public void setTeamMemberId(LongFilter teamMemberId) {
        this.teamMemberId = teamMemberId;
    }

    public StringFilter getTitle() {
        return title;
    }

    public Optional<StringFilter> optionalTitle() {
        return Optional.ofNullable(title);
    }

    public void setTitle(StringFilter title) {
        this.title = title;
    }

    public StringFilter getDescription() {
        return description;
    }

    public Optional<StringFilter> optionalDescription() {
        return Optional.ofNullable(description);
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public LocalDateFilter getAchievementDate() {
        return achievementDate;
    }

    public Optional<LocalDateFilter> optionalAchievementDate() {
        return Optional.ofNullable(achievementDate);
    }

    public void setAchievementDate(LocalDateFilter achievementDate) {
        this.achievementDate = achievementDate;
    }

    public StringFilter getCategory() {
        return category;
    }

    public Optional<StringFilter> optionalCategory() {
        return Optional.ofNullable(category);
    }

    public void setCategory(StringFilter category) {
        this.category = category;
    }

    public StringFilter getIssuer() {
        return issuer;
    }

    public Optional<StringFilter> optionalIssuer() {
        return Optional.ofNullable(issuer);
    }

    public void setIssuer(StringFilter issuer) {
        this.issuer = issuer;
    }

    public IntegerFilter getDisplayOrder() {
        return displayOrder;
    }

    public Optional<IntegerFilter> optionalDisplayOrder() {
        return Optional.ofNullable(displayOrder);
    }

    public void setDisplayOrder(IntegerFilter displayOrder) {
        this.displayOrder = displayOrder;
    }

    public BooleanFilter getIsFeatured() {
        return isFeatured;
    }

    public Optional<BooleanFilter> optionalIsFeatured() {
        return Optional.ofNullable(isFeatured);
    }

    public void setIsFeatured(BooleanFilter isFeatured) {
        this.isFeatured = isFeatured;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TeamMemberAchievementCriteria that = (TeamMemberAchievementCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(tenantId, that.tenantId) &&
            Objects.equals(teamMemberId, that.teamMemberId) &&
            Objects.equals(title, that.title) &&
            Objects.equals(category, that.category) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tenantId, teamMemberId, title, category, distinct);
    }
}
