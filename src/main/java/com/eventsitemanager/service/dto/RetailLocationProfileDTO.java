package com.eventsitemanager.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;

@SuppressWarnings("common-java:DuplicatedBlocks")
public class RetailLocationProfileDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull
    @Size(max = 255)
    private String tenantId;

    @NotNull
    private Long locationId;

    private Integer fuelDispenserCount;

    @NotNull
    private Boolean hasCarWash;

    @NotNull
    private Boolean hasLottery;

    @NotNull
    private Boolean hasFoodservice;

    private LocalDate licenseExpiresOn;

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

    public Integer getFuelDispenserCount() {
        return fuelDispenserCount;
    }

    public void setFuelDispenserCount(Integer fuelDispenserCount) {
        this.fuelDispenserCount = fuelDispenserCount;
    }

    public Boolean getHasCarWash() {
        return hasCarWash;
    }

    public void setHasCarWash(Boolean hasCarWash) {
        this.hasCarWash = hasCarWash;
    }

    public Boolean getHasLottery() {
        return hasLottery;
    }

    public void setHasLottery(Boolean hasLottery) {
        this.hasLottery = hasLottery;
    }

    public Boolean getHasFoodservice() {
        return hasFoodservice;
    }

    public void setHasFoodservice(Boolean hasFoodservice) {
        this.hasFoodservice = hasFoodservice;
    }

    public LocalDate getLicenseExpiresOn() {
        return licenseExpiresOn;
    }

    public void setLicenseExpiresOn(LocalDate licenseExpiresOn) {
        this.licenseExpiresOn = licenseExpiresOn;
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
