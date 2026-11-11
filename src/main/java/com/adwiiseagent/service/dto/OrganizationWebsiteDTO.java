package com.adwiiseagent.service.dto;

import java.io.Serializable;

/** One website tenant id stored on tenant_settings for an organization. */
public class OrganizationWebsiteDTO implements Serializable {

    private Long settingsId;
    private String tenantId;
    private Long organizationId;

    public Long getSettingsId() {
        return settingsId;
    }

    public void setSettingsId(Long settingsId) {
        this.settingsId = settingsId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }
}
