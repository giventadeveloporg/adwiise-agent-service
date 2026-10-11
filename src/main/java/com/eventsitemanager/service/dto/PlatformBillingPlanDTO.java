package com.eventsitemanager.service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

public class PlatformBillingPlanDTO implements Serializable {

    private Long id;
    private String tenantId;

    @NotNull
    @Size(max = 32)
    private String product;

    @NotNull
    @Size(max = 255)
    private String name;

    @NotNull
    @Size(max = 64)
    private String code;

    @NotNull
    @Size(max = 20)
    private String billingMode;

    private String billingInterval;

    @NotNull
    private BigDecimal listPriceUsd;

    private Boolean published;
    private String featureSummary;
    private String graduatedTiersJson;
    private String stripeProductId;
    private String stripePriceId;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

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

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBillingMode() {
        return billingMode;
    }

    public void setBillingMode(String billingMode) {
        this.billingMode = billingMode;
    }

    public String getBillingInterval() {
        return billingInterval;
    }

    public void setBillingInterval(String billingInterval) {
        this.billingInterval = billingInterval;
    }

    public BigDecimal getListPriceUsd() {
        return listPriceUsd;
    }

    public void setListPriceUsd(BigDecimal listPriceUsd) {
        this.listPriceUsd = listPriceUsd;
    }

    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    public String getFeatureSummary() {
        return featureSummary;
    }

    public void setFeatureSummary(String featureSummary) {
        this.featureSummary = featureSummary;
    }

    public String getGraduatedTiersJson() {
        return graduatedTiersJson;
    }

    public void setGraduatedTiersJson(String graduatedTiersJson) {
        this.graduatedTiersJson = graduatedTiersJson;
    }

    public String getStripeProductId() {
        return stripeProductId;
    }

    public void setStripeProductId(String stripeProductId) {
        this.stripeProductId = stripeProductId;
    }

    public String getStripePriceId() {
        return stripePriceId;
    }

    public void setStripePriceId(String stripePriceId) {
        this.stripePriceId = stripePriceId;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
