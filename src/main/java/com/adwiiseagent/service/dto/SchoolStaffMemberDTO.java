package com.adwiiseagent.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.adwiiseagent.domain.SchoolStaffMember} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SchoolStaffMemberDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String tenantId;

    @NotNull
    @Size(max = 32)
    private String staffCategory;

    @NotNull
    @Size(max = 16)
    private String stage;

    @NotNull
    @Size(max = 255)
    private String firstName;

    @NotNull
    @Size(max = 255)
    private String lastName;

    @Size(max = 255)
    private String designation;

    @Size(max = 512)
    private String qualification;

    @Size(max = 255)
    private String subjectName;

    @Size(max = 255)
    private String classTeacherOf;

    private Long departmentId;

    @Size(max = 255)
    private String email;

    @Size(max = 50)
    private String phone;

    @Size(max = 1024)
    private String profileImageUrl;

    @Size(max = 2048)
    private String bio;

    private Integer priorityOrder;

    private Boolean isActive;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getStaffCategory() {
        return staffCategory;
    }

    public void setStaffCategory(String staffCategory) {
        this.staffCategory = staffCategory;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getClassTeacherOf() {
        return classTeacherOf;
    }

    public void setClassTeacherOf(String classTeacherOf) {
        this.classTeacherOf = classTeacherOf;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Integer getPriorityOrder() {
        return priorityOrder;
    }

    public void setPriorityOrder(Integer priorityOrder) {
        this.priorityOrder = priorityOrder;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SchoolStaffMemberDTO)) {
            return false;
        }
        SchoolStaffMemberDTO other = (SchoolStaffMemberDTO) o;
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
            Objects.equals(isActive, other.isActive)
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
            isActive
        );
    }

    @Override
    public String toString() {
        return "SchoolStaffMemberDTO{id=" + id + "}";
    }
}
