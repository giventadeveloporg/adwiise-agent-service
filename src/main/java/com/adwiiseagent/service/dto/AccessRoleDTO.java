package com.adwiiseagent.service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;

public class AccessRoleDTO implements Serializable {

    private Long id;

    @Size(max = 255)
    private String tenantId;

    @NotNull
    @Size(max = 64)
    private String code;

    @NotNull
    @Size(max = 255)
    private String label;

    private Integer sortOrder;

    private Boolean isActive;

    private Boolean grantsAdmin;

    private Boolean platformSuper;

    @Size(max = 16)
    private String locationScope;

    private Instant createdAt;

    private Instant updatedAt;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
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

    public Boolean getGrantsAdmin() {
        return grantsAdmin;
    }

    public void setGrantsAdmin(Boolean grantsAdmin) {
        this.grantsAdmin = grantsAdmin;
    }

    public Boolean getPlatformSuper() {
        return platformSuper;
    }

    public void setPlatformSuper(Boolean platformSuper) {
        this.platformSuper = platformSuper;
    }

    public String getLocationScope() {
        return locationScope;
    }

    public void setLocationScope(String locationScope) {
        this.locationScope = locationScope;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
