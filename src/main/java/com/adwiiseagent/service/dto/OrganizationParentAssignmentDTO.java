package com.adwiiseagent.service.dto;

import java.io.Serializable;

/** Moves a website under a client organization. A null parent detaches the website. */
public class OrganizationParentAssignmentDTO implements Serializable {

    private Long parentOrganizationId;

    public Long getParentOrganizationId() {
        return parentOrganizationId;
    }

    public void setParentOrganizationId(Long parentOrganizationId) {
        this.parentOrganizationId = parentOrganizationId;
    }
}
