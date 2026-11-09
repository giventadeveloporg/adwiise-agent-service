package com.adwiiseagent.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.adwiiseagent.domain.SchoolDepartment} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SchoolDepartmentDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String tenantId;

    @NotNull
    @Size(max = 255)
    private String name;

    @NotNull
    @Size(max = 128)
    private String slug;

    @Size(max = 1024)
    private String summary;

    private String body;

    @Size(max = 1024)
    private String imageUrl;

    private Long headStaffId;

    private Integer sortOrder;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getHeadStaffId() {
        return headStaffId;
    }

    public void setHeadStaffId(Long headStaffId) {
        this.headStaffId = headStaffId;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
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
        if (!(o instanceof SchoolDepartmentDTO)) {
            return false;
        }
        SchoolDepartmentDTO other = (SchoolDepartmentDTO) o;
        return (
            Objects.equals(id, other.id) &&
            Objects.equals(tenantId, other.tenantId) &&
            Objects.equals(name, other.name) &&
            Objects.equals(slug, other.slug) &&
            Objects.equals(summary, other.summary) &&
            Objects.equals(body, other.body) &&
            Objects.equals(imageUrl, other.imageUrl) &&
            Objects.equals(headStaffId, other.headStaffId) &&
            Objects.equals(sortOrder, other.sortOrder) &&
            Objects.equals(isActive, other.isActive)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tenantId, name, slug, summary, body, imageUrl, headStaffId, sortOrder, isActive);
    }

    @Override
    public String toString() {
        return "SchoolDepartmentDTO{id=" + id + "}";
    }
}
