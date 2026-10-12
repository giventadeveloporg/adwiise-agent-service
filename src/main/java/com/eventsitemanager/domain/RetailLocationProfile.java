package com.eventsitemanager.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "retail_location_profile")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RetailLocationProfile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "retailLocationProfileSeq")
    @SequenceGenerator(name = "retailLocationProfileSeq", sequenceName = "public.retail_location_profile_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "tenant_id", length = 255, nullable = false)
    private String tenantId;

    @NotNull
    @Column(name = "location_id", nullable = false)
    private Long locationId;

    @Column(name = "fuel_dispenser_count")
    private Integer fuelDispenserCount;

    @NotNull
    @Column(name = "has_car_wash", nullable = false)
    private Boolean hasCarWash;

    @NotNull
    @Column(name = "has_lottery", nullable = false)
    private Boolean hasLottery;

    @NotNull
    @Column(name = "has_foodservice", nullable = false)
    private Boolean hasFoodservice;

    @Column(name = "license_expires_on")
    private LocalDate licenseExpiresOn;

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
