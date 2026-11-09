package com.adwiiseagent.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.adwiiseagent.domain.SchoolHighlight} entity.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SchoolHighlightCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;
    private StringFilter tenantId;
    private StringFilter personName;
    private StringFilter subtitle;
    private StringFilter imageUrl;
    private StringFilter academicYear;
    private IntegerFilter sortOrder;
    private BooleanFilter isActive;
    private Boolean distinct;

    public SchoolHighlightCriteria() {}

    public SchoolHighlightCriteria(SchoolHighlightCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.tenantId = other.optionalTenantId().map(StringFilter::copy).orElse(null);
        this.personName = other.optionalPersonName().map(StringFilter::copy).orElse(null);
        this.subtitle = other.optionalSubtitle().map(StringFilter::copy).orElse(null);
        this.imageUrl = other.optionalImageUrl().map(StringFilter::copy).orElse(null);
        this.academicYear = other.optionalAcademicYear().map(StringFilter::copy).orElse(null);
        this.sortOrder = other.optionalSortOrder().map(IntegerFilter::copy).orElse(null);
        this.isActive = other.optionalIsActive().map(BooleanFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public SchoolHighlightCriteria copy() {
        return new SchoolHighlightCriteria(this);
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

    public StringFilter getPersonName() {
        return personName;
    }

    public Optional<StringFilter> optionalPersonName() {
        return Optional.ofNullable(personName);
    }

    public void setPersonName(StringFilter personName) {
        this.personName = personName;
    }

    public StringFilter getSubtitle() {
        return subtitle;
    }

    public Optional<StringFilter> optionalSubtitle() {
        return Optional.ofNullable(subtitle);
    }

    public void setSubtitle(StringFilter subtitle) {
        this.subtitle = subtitle;
    }

    public StringFilter getImageUrl() {
        return imageUrl;
    }

    public Optional<StringFilter> optionalImageUrl() {
        return Optional.ofNullable(imageUrl);
    }

    public void setImageUrl(StringFilter imageUrl) {
        this.imageUrl = imageUrl;
    }

    public StringFilter getAcademicYear() {
        return academicYear;
    }

    public Optional<StringFilter> optionalAcademicYear() {
        return Optional.ofNullable(academicYear);
    }

    public void setAcademicYear(StringFilter academicYear) {
        this.academicYear = academicYear;
    }

    public IntegerFilter getSortOrder() {
        return sortOrder;
    }

    public Optional<IntegerFilter> optionalSortOrder() {
        return Optional.ofNullable(sortOrder);
    }

    public void setSortOrder(IntegerFilter sortOrder) {
        this.sortOrder = sortOrder;
    }

    public BooleanFilter getIsActive() {
        return isActive;
    }

    public Optional<BooleanFilter> optionalIsActive() {
        return Optional.ofNullable(isActive);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SchoolHighlightCriteria)) {
            return false;
        }
        SchoolHighlightCriteria other = (SchoolHighlightCriteria) o;
        return (
            Objects.equals(id, other.id) &&
            Objects.equals(tenantId, other.tenantId) &&
            Objects.equals(personName, other.personName) &&
            Objects.equals(subtitle, other.subtitle) &&
            Objects.equals(imageUrl, other.imageUrl) &&
            Objects.equals(academicYear, other.academicYear) &&
            Objects.equals(sortOrder, other.sortOrder) &&
            Objects.equals(isActive, other.isActive) &&
            Objects.equals(distinct, other.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tenantId, personName, subtitle, imageUrl, academicYear, sortOrder, isActive, distinct);
    }
}
