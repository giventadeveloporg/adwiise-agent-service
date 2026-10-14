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
@Table(name = "retail_fuel_day")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RetailFuelDay implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "retailFuelDaySeq")
    @SequenceGenerator(name = "retailFuelDaySeq", sequenceName = "public.retail_fuel_day_id_seq", allocationSize = 1)
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

    @Column(name = "fuel_gallons_sold", precision = 21, scale = 2)
    private BigDecimal fuelGallonsSold;

    @Column(name = "fuel_revenue_usd", precision = 21, scale = 2)
    private BigDecimal fuelRevenueUsd;

    @Column(name = "fuel_margin_cents_per_gallon", precision = 8, scale = 2)
    private BigDecimal fuelMarginCentsPerGallon;

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

    public BigDecimal getFuelGallonsSold() {
        return fuelGallonsSold;
    }

    public void setFuelGallonsSold(BigDecimal fuelGallonsSold) {
        this.fuelGallonsSold = fuelGallonsSold;
    }

    public BigDecimal getFuelRevenueUsd() {
        return fuelRevenueUsd;
    }

    public void setFuelRevenueUsd(BigDecimal fuelRevenueUsd) {
        this.fuelRevenueUsd = fuelRevenueUsd;
    }

    public BigDecimal getFuelMarginCentsPerGallon() {
        return fuelMarginCentsPerGallon;
    }

    public void setFuelMarginCentsPerGallon(BigDecimal fuelMarginCentsPerGallon) {
        this.fuelMarginCentsPerGallon = fuelMarginCentsPerGallon;
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
