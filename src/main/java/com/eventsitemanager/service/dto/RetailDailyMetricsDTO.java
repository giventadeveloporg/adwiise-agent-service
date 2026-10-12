package com.eventsitemanager.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;

@SuppressWarnings("common-java:DuplicatedBlocks")
public class RetailDailyMetricsDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull
    @Size(max = 255)
    private String tenantId;

    @NotNull
    private Long locationId;

    @NotNull
    private LocalDate metricDate;

    private BigDecimal salesUsd;

    private Integer transactionsCount;

    private BigDecimal laborHours;

    private BigDecimal laborCostUsd;

    private BigDecimal wasteCostUsd;

    private BigDecimal shrinkCostUsd;

    private BigDecimal expectedProfitUsd;

    private BigDecimal actualProfitUsd;

    private String metricsJson;

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

    public LocalDate getMetricDate() {
        return metricDate;
    }

    public void setMetricDate(LocalDate metricDate) {
        this.metricDate = metricDate;
    }

    public BigDecimal getSalesUsd() {
        return salesUsd;
    }

    public void setSalesUsd(BigDecimal salesUsd) {
        this.salesUsd = salesUsd;
    }

    public Integer getTransactionsCount() {
        return transactionsCount;
    }

    public void setTransactionsCount(Integer transactionsCount) {
        this.transactionsCount = transactionsCount;
    }

    public BigDecimal getLaborHours() {
        return laborHours;
    }

    public void setLaborHours(BigDecimal laborHours) {
        this.laborHours = laborHours;
    }

    public BigDecimal getLaborCostUsd() {
        return laborCostUsd;
    }

    public void setLaborCostUsd(BigDecimal laborCostUsd) {
        this.laborCostUsd = laborCostUsd;
    }

    public BigDecimal getWasteCostUsd() {
        return wasteCostUsd;
    }

    public void setWasteCostUsd(BigDecimal wasteCostUsd) {
        this.wasteCostUsd = wasteCostUsd;
    }

    public BigDecimal getShrinkCostUsd() {
        return shrinkCostUsd;
    }

    public void setShrinkCostUsd(BigDecimal shrinkCostUsd) {
        this.shrinkCostUsd = shrinkCostUsd;
    }

    public BigDecimal getExpectedProfitUsd() {
        return expectedProfitUsd;
    }

    public void setExpectedProfitUsd(BigDecimal expectedProfitUsd) {
        this.expectedProfitUsd = expectedProfitUsd;
    }

    public BigDecimal getActualProfitUsd() {
        return actualProfitUsd;
    }

    public void setActualProfitUsd(BigDecimal actualProfitUsd) {
        this.actualProfitUsd = actualProfitUsd;
    }

    public String getMetricsJson() {
        return metricsJson;
    }

    public void setMetricsJson(String metricsJson) {
        this.metricsJson = metricsJson;
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
