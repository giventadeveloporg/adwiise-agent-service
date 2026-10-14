package com.adwiiseagent.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;

@SuppressWarnings("common-java:DuplicatedBlocks")
public class RetailFuelDayDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull
    @Size(max = 255)
    private String tenantId;

    @NotNull
    private Long locationId;

    @NotNull
    private LocalDate metricDate;

    private BigDecimal fuelGallonsSold;

    private BigDecimal fuelRevenueUsd;

    private BigDecimal fuelMarginCentsPerGallon;

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
