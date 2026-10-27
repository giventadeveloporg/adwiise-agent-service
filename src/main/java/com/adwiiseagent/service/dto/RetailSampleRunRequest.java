package com.adwiiseagent.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * On-demand retail file run forwarded to the batch job.
 */
public class RetailSampleRunRequest {

    @JsonProperty("tenantId")
    private String tenantId;

    @JsonProperty("retailerType")
    private String retailerType;

    @JsonProperty("siteType")
    private String siteType = "RETAIL";

    @JsonProperty("metricDate")
    private String metricDate;

    @JsonProperty("stationId")
    private Integer stationId = 1;

    @JsonProperty("source")
    private String source = "sample";

    @JsonProperty("post")
    private Boolean post = false;

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getRetailerType() {
        return retailerType;
    }

    public void setRetailerType(String retailerType) {
        this.retailerType = retailerType;
    }

    public String getSiteType() {
        return siteType;
    }

    public void setSiteType(String siteType) {
        this.siteType = siteType;
    }

    public String getMetricDate() {
        return metricDate;
    }

    public void setMetricDate(String metricDate) {
        this.metricDate = metricDate;
    }

    public Integer getStationId() {
        return stationId;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Boolean getPost() {
        return post;
    }

    public void setPost(Boolean post) {
        this.post = post;
    }
}
