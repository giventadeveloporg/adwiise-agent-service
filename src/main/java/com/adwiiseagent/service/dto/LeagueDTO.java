package com.adwiiseagent.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.time.Instant;
import java.time.LocalDate;

public class LeagueDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String tenantId;

    @NotNull
    @Size(max = 255)
    private String name;

    @Size(max = 100)
    private String slug;

    @Size(max = 64)
    private String seasonLabel;

    private String description;

    @Size(max = 500)
    private String logoUrl;

    private LocalDate startDate;

    private LocalDate endDate;

    @Size(max = 32)
    private String status;

    private Boolean isPublished;

    private Boolean showOnHomepage;

    private Integer homepageDisplayOrder;

    @Size(max = 32)
    private String standingsSource;

    private Integer pointsForWin;

    private Integer pointsForDraw;

    private Integer pointsForLoss;

    private Instant createdAt;

    private Instant updatedAt;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }

    public String getSeasonLabel() { return seasonLabel; }
    public void setSeasonLabel(String seasonLabel) { this.seasonLabel = seasonLabel; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getLogoUrl() { return logoUrl; }
    public void setLogoUrl(String logoUrl) { this.logoUrl = logoUrl; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Boolean getIsPublished() { return isPublished; }
    public void setIsPublished(Boolean isPublished) { this.isPublished = isPublished; }

    public Boolean getShowOnHomepage() { return showOnHomepage; }
    public void setShowOnHomepage(Boolean showOnHomepage) { this.showOnHomepage = showOnHomepage; }

    public Integer getHomepageDisplayOrder() { return homepageDisplayOrder; }
    public void setHomepageDisplayOrder(Integer homepageDisplayOrder) { this.homepageDisplayOrder = homepageDisplayOrder; }

    public String getStandingsSource() { return standingsSource; }
    public void setStandingsSource(String standingsSource) { this.standingsSource = standingsSource; }

    public Integer getPointsForWin() { return pointsForWin; }
    public void setPointsForWin(Integer pointsForWin) { this.pointsForWin = pointsForWin; }

    public Integer getPointsForDraw() { return pointsForDraw; }
    public void setPointsForDraw(Integer pointsForDraw) { this.pointsForDraw = pointsForDraw; }

    public Integer getPointsForLoss() { return pointsForLoss; }
    public void setPointsForLoss(Integer pointsForLoss) { this.pointsForLoss = pointsForLoss; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof LeagueDTO other)) return false;
        return id != null && id.equals(other.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }
}
