package com.adwiiseagent.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;

@SuppressWarnings("common-java:DuplicatedBlocks")
public class RetailRecommendationDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull
    @Size(max = 255)
    private String tenantId;

    private Long locationId;

    @NotNull
    private LocalDate recommendationDate;

    @NotNull
    @Size(max = 32)
    private String category;

    @NotNull
    @Size(max = 500)
    private String title;

    private String detail;

    private BigDecimal estimatedImpactUsd;

    private Integer priority;

    private BigDecimal confidencePct;

    private String explanation;

    @NotNull
    @Size(max = 32)
    private String status;

    @Size(max = 2000)
    private String ownerFeedback;

    @Size(max = 128)
    private String sourceModelRunId;

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

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public LocalDate getRecommendationDate() {
        return recommendationDate;
    }

    public void setRecommendationDate(LocalDate recommendationDate) {
        this.recommendationDate = recommendationDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public BigDecimal getEstimatedImpactUsd() {
        return estimatedImpactUsd;
    }

    public void setEstimatedImpactUsd(BigDecimal estimatedImpactUsd) {
        this.estimatedImpactUsd = estimatedImpactUsd;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public BigDecimal getConfidencePct() {
        return confidencePct;
    }

    public void setConfidencePct(BigDecimal confidencePct) {
        this.confidencePct = confidencePct;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOwnerFeedback() {
        return ownerFeedback;
    }

    public void setOwnerFeedback(String ownerFeedback) {
        this.ownerFeedback = ownerFeedback;
    }

    public String getSourceModelRunId() {
        return sourceModelRunId;
    }

    public void setSourceModelRunId(String sourceModelRunId) {
        this.sourceModelRunId = sourceModelRunId;
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
