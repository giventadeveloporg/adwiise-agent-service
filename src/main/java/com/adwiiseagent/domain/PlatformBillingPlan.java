package com.adwiiseagent.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Entity
@Table(name = "platform_billing_plan")
public class PlatformBillingPlan implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "platformBillingPlanSeq")
    @SequenceGenerator(name = "platformBillingPlanSeq", sequenceName = "public.platform_billing_plan_id_seq", allocationSize = 1)
    private Long id;

    @Size(max = 255)
    @Column(name = "tenant_id", length = 255)
    private String tenantId;

    @NotNull
    @Size(max = 32)
    @Column(name = "product", length = 32, nullable = false)
    private String product;

    @NotNull
    @Size(max = 255)
    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @NotNull
    @Size(max = 64)
    @Column(name = "code", length = 64, nullable = false)
    private String code;

    @NotNull
    @Size(max = 20)
    @Column(name = "billing_mode", length = 20, nullable = false)
    private String billingMode;

    @Size(max = 20)
    @Column(name = "billing_interval", length = 20)
    private String billingInterval;

    @NotNull
    @Column(name = "list_price_usd", precision = 21, scale = 2, nullable = false)
    private BigDecimal listPriceUsd;

    @NotNull
    @Column(name = "published", nullable = false)
    private Boolean published = Boolean.FALSE;

    @Size(max = 2000)
    @Column(name = "feature_summary", length = 2000)
    private String featureSummary;

    @Column(name = "graduated_tiers_json")
    private String graduatedTiersJson;

    @Size(max = 255)
    @Column(name = "stripe_product_id", length = 255)
    private String stripeProductId;

    @Size(max = 255)
    @Column(name = "stripe_price_id", length = 255)
    private String stripePriceId;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private ZonedDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        ZonedDateTime now = ZonedDateTime.now();
        if (createdAt == null) {
            createdAt = now;
        }
        if (updatedAt == null) {
            updatedAt = now;
        }
        if (published == null) {
            published = Boolean.FALSE;
        }
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = ZonedDateTime.now();
    }

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
