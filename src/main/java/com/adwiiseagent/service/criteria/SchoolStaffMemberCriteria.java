package com.adwiiseagent.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.adwiiseagent.domain.SchoolStaffMember} entity.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SchoolStaffMemberCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;
    private StringFilter tenantId;
    private StringFilter staffCategory;
    private StringFilter stage;
    private StringFilter firstName;
    private StringFilter lastName;
    private StringFilter designation;
    private StringFilter qualification;
    private StringFilter subjectName;
    private StringFilter classTeacherOf;
    private LongFilter departmentId;
    private StringFilter email;
    private StringFilter phone;
    private StringFilter profileImageUrl;
    private StringFilter bio;
    private IntegerFilter priorityOrder;
    private BooleanFilter isActive;
    private Boolean distinct;

    public SchoolStaffMemberCriteria() {}

    public SchoolStaffMemberCriteria(SchoolStaffMemberCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.tenantId = other.optionalTenantId().map(StringFilter::copy).orElse(null);
        this.staffCategory = other.optionalStaffCategory().map(StringFilter::copy).orElse(null);
        this.stage = other.optionalStage().map(StringFilter::copy).orElse(null);
        this.firstName = other.optionalFirstName().map(StringFilter::copy).orElse(null);
        this.lastName = other.optionalLastName().map(StringFilter::copy).orElse(null);
        this.designation = other.optionalDesignation().map(StringFilter::copy).orElse(null);
        this.qualification = other.optionalQualification().map(StringFilter::copy).orElse(null);
        this.subjectName = other.optionalSubjectName().map(StringFilter::copy).orElse(null);
        this.classTeacherOf = other.optionalClassTeacherOf().map(StringFilter::copy).orElse(null);
        this.departmentId = other.optionalDepartmentId().map(LongFilter::copy).orElse(null);
        this.email = other.optionalEmail().map(StringFilter::copy).orElse(null);
        this.phone = other.optionalPhone().map(StringFilter::copy).orElse(null);
        this.profileImageUrl = other.optionalProfileImageUrl().map(StringFilter::copy).orElse(null);
        this.bio = other.optionalBio().map(StringFilter::copy).orElse(null);
        this.priorityOrder = other.optionalPriorityOrder().map(IntegerFilter::copy).orElse(null);
        this.isActive = other.optionalIsActive().map(BooleanFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public SchoolStaffMemberCriteria copy() {
        return new SchoolStaffMemberCriteria(this);
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

    public StringFilter getStaffCategory() {
        return staffCategory;
    }

    public Optional<StringFilter> optionalStaffCategory() {
        return Optional.ofNullable(staffCategory);
    }

    public void setStaffCategory(StringFilter staffCategory) {
        this.staffCategory = staffCategory;
    }

    public StringFilter getStage() {
        return stage;
    }

    public Optional<StringFilter> optionalStage() {
        return Optional.ofNullable(stage);
    }

    public void setStage(StringFilter stage) {
        this.stage = stage;
    }

    public StringFilter getFirstName() {
        return firstName;
    }

    public Optional<StringFilter> optionalFirstName() {
        return Optional.ofNullable(firstName);
    }

    public void setFirstName(StringFilter firstName) {
        this.firstName = firstName;
    }

    public StringFilter getLastName() {
        return lastName;
    }

    public Optional<StringFilter> optionalLastName() {
        return Optional.ofNullable(lastName);
    }

    public void setLastName(StringFilter lastName) {
        this.lastName = lastName;
    }

    public StringFilter getDesignation() {
        return designation;
    }

    public Optional<StringFilter> optionalDesignation() {
        return Optional.ofNullable(designation);
    }

    public void setDesignation(StringFilter designation) {
        this.designation = designation;
    }

    public StringFilter getQualification() {
        return qualification;
    }

    public Optional<StringFilter> optionalQualification() {
        return Optional.ofNullable(qualification);
    }

    public void setQualification(StringFilter qualification) {
        this.qualification = qualification;
    }

    public StringFilter getSubjectName() {
        return subjectName;
    }

    public Optional<StringFilter> optionalSubjectName() {
        return Optional.ofNullable(subjectName);
    }

    public void setSubjectName(StringFilter subjectName) {
        this.subjectName = subjectName;
    }

    public StringFilter getClassTeacherOf() {
        return classTeacherOf;
    }

    public Optional<StringFilter> optionalClassTeacherOf() {
        return Optional.ofNullable(classTeacherOf);
    }

    public void setClassTeacherOf(StringFilter classTeacherOf) {
        this.classTeacherOf = classTeacherOf;
    }

    public LongFilter getDepartmentId() {
        return departmentId;
    }

    public Optional<LongFilter> optionalDepartmentId() {
        return Optional.ofNullable(departmentId);
    }

    public void setDepartmentId(LongFilter departmentId) {
        this.departmentId = departmentId;
    }

    public StringFilter getEmail() {
        return email;
    }

    public Optional<StringFilter> optionalEmail() {
        return Optional.ofNullable(email);
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getPhone() {
        return phone;
    }

    public Optional<StringFilter> optionalPhone() {
        return Optional.ofNullable(phone);
    }

    public void setPhone(StringFilter phone) {
        this.phone = phone;
    }

    public StringFilter getProfileImageUrl() {
        return profileImageUrl;
    }

    public Optional<StringFilter> optionalProfileImageUrl() {
        return Optional.ofNullable(profileImageUrl);
    }

    public void setProfileImageUrl(StringFilter profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public StringFilter getBio() {
        return bio;
    }

    public Optional<StringFilter> optionalBio() {
        return Optional.ofNullable(bio);
    }

    public void setBio(StringFilter bio) {
        this.bio = bio;
    }

    public IntegerFilter getPriorityOrder() {
        return priorityOrder;
    }

    public Optional<IntegerFilter> optionalPriorityOrder() {
        return Optional.ofNullable(priorityOrder);
    }

    public void setPriorityOrder(IntegerFilter priorityOrder) {
        this.priorityOrder = priorityOrder;
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
        if (!(o instanceof SchoolStaffMemberCriteria)) {
            return false;
        }
        SchoolStaffMemberCriteria other = (SchoolStaffMemberCriteria) o;
        return (
            Objects.equals(id, other.id) &&
            Objects.equals(tenantId, other.tenantId) &&
            Objects.equals(staffCategory, other.staffCategory) &&
            Objects.equals(stage, other.stage) &&
            Objects.equals(firstName, other.firstName) &&
            Objects.equals(lastName, other.lastName) &&
            Objects.equals(designation, other.designation) &&
            Objects.equals(qualification, other.qualification) &&
            Objects.equals(subjectName, other.subjectName) &&
            Objects.equals(classTeacherOf, other.classTeacherOf) &&
            Objects.equals(departmentId, other.departmentId) &&
            Objects.equals(email, other.email) &&
            Objects.equals(phone, other.phone) &&
            Objects.equals(profileImageUrl, other.profileImageUrl) &&
            Objects.equals(bio, other.bio) &&
            Objects.equals(priorityOrder, other.priorityOrder) &&
            Objects.equals(isActive, other.isActive) &&
            Objects.equals(distinct, other.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            tenantId,
            staffCategory,
            stage,
            firstName,
            lastName,
            designation,
            qualification,
            subjectName,
            classTeacherOf,
            departmentId,
            email,
            phone,
            profileImageUrl,
            bio,
            priorityOrder,
            isActive,
            distinct
        );
    }
}
