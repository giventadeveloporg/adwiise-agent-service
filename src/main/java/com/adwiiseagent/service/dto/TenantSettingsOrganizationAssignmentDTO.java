package com.adwiiseagent.service.dto;

import java.io.Serializable;

/** Moves a tenant settings row onto another organization. */
public class TenantSettingsOrganizationAssignmentDTO implements Serializable {

    private Long organizationId;

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }
}
