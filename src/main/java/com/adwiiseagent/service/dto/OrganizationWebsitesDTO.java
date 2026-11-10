package com.adwiiseagent.service.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A client organization and the websites that point at it.
 */
public class OrganizationWebsitesDTO implements Serializable {

    private TenantOrganizationDTO client;
    private List<TenantOrganizationDTO> websites = new ArrayList<>();
    private boolean viewedIsClient;

    public TenantOrganizationDTO getClient() {
        return client;
    }

    public void setClient(TenantOrganizationDTO client) {
        this.client = client;
    }

    public List<TenantOrganizationDTO> getWebsites() {
        return websites;
    }

    public void setWebsites(List<TenantOrganizationDTO> websites) {
        this.websites = websites;
    }

    public boolean isViewedIsClient() {
        return viewedIsClient;
    }

    public void setViewedIsClient(boolean viewedIsClient) {
        this.viewedIsClient = viewedIsClient;
    }
}
