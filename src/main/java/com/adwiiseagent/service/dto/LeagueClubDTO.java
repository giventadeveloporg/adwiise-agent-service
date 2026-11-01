package com.adwiiseagent.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.time.Instant;

public class LeagueClubDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String tenantId;

    @NotNull
    private Long leagueId;

    @NotNull
    @Size(max = 255)
    private String name;

    @Size(max = 64)
    private String shortName;

    @Size(max = 500)
    private String logoUrl;

    @NotNull
    private Boolean isHomeClub;

    private Integer displayOrder;

    private Instant createdAt;

    private Instant updatedAt;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }

    public Long getLeagueId() { return leagueId; }
    public void setLeagueId(Long leagueId) { this.leagueId = leagueId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getShortName() { return shortName; }
    public void setShortName(String shortName) { this.shortName = shortName; }

    public String getLogoUrl() { return logoUrl; }
    public void setLogoUrl(String logoUrl) { this.logoUrl = logoUrl; }

    public Boolean getIsHomeClub() { return isHomeClub; }
    public void setIsHomeClub(Boolean isHomeClub) { this.isHomeClub = isHomeClub; }

    public Integer getDisplayOrder() { return displayOrder; }
    public void setDisplayOrder(Integer displayOrder) { this.displayOrder = displayOrder; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof LeagueClubDTO other)) return false;
        return id != null && id.equals(other.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }
}
