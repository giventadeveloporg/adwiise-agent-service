package com.adwiiseagent.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "retail_daily_metrics")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RetailDailyMetrics implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "retailDailyMetricsSeq")
    @SequenceGenerator(name = "retailDailyMetricsSeq", sequenceName = "public.retail_daily_metrics_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "tenant_id", length = 255, nullable = false)
    private String tenantId;

    @NotNull
    @Column(name = "location_id", nullable = false)
    private Long locationId;

    @NotNull
    @Column(name = "metric_date", nullable = false)
    private LocalDate metricDate;

    @Column(name = "sales_usd", precision = 21, scale = 2)
    private BigDecimal salesUsd;

    @Column(name = "transactions_count")
    private Integer transactionsCount;

    @Column(name = "labor_hours", precision = 10, scale = 2)
    private BigDecimal laborHours;

    @Column(name = "labor_cost_usd", precision = 21, scale = 2)
    private BigDecimal laborCostUsd;

    @Column(name = "waste_cost_usd", precision = 21, scale = 2)
    private BigDecimal wasteCostUsd;

    @Column(name = "shrink_cost_usd", precision = 21, scale = 2)
    private BigDecimal shrinkCostUsd;

    @Column(name = "expected_profit_usd", precision = 21, scale = 2)
    private BigDecimal expectedProfitUsd;

    @Column(name = "actual_profit_usd", precision = 21, scale = 2)
    private BigDecimal actualProfitUsd;

    @Lob
    @Column(name = "metrics_json")
    private String metricsJson;

    @Size(max = 128)
    @Column(name = "source_model_run_id", length = 128)
    private String sourceModelRunId;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
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
