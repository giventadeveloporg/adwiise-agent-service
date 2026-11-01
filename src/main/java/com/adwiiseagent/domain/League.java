package com.adwiiseagent.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "league")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class League implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "leagueSeq")
    @SequenceGenerator(name = "leagueSeq", sequenceName = "public.league_id_seq", allocationSize = 1)
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "tenant_id", length = 255, nullable = false)
    private String tenantId;

    @NotNull
    @Size(max = 255)
    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @NotNull
    @Size(max = 100)
    @Column(name = "slug", length = 100, nullable = false)
    private String slug;

    @Size(max = 64)
    @Column(name = "season_label", length = 64)
    private String seasonLabel;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Size(max = 500)
    @Column(name = "logo_url", length = 500)
    private String logoUrl;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @NotNull
    @Size(max = 32)
    @Column(name = "status", length = 32, nullable = false)
    private String status;

    @NotNull
    @Column(name = "is_published", nullable = false)
    private Boolean isPublished;

    @NotNull
    @Column(name = "show_on_homepage", nullable = false)
    private Boolean showOnHomepage;

    @Column(name = "homepage_display_order")
    private Integer homepageDisplayOrder;

    @NotNull
    @Size(max = 32)
    @Column(name = "standings_source", length = 32, nullable = false)
    private String standingsSource;

    @NotNull
    @Column(name = "points_for_win", nullable = false)
    private Integer pointsForWin;

    @NotNull
    @Column(name = "points_for_draw", nullable = false)
    private Integer pointsForDraw;

    @NotNull
    @Column(name = "points_for_loss", nullable = false)
    private Integer pointsForLoss;

    @NotNull
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @PrePersist
    protected void onCreate() {
        Instant now = Instant.now();
        createdAt = now;
        updatedAt = now;
        if (status == null || status.isBlank()) {
            status = "DRAFT";
        }
        if (standingsSource == null || standingsSource.isBlank()) {
            standingsSource = "CALCULATED";
        }
        if (isPublished == null) {
            isPublished = false;
        }
        if (showOnHomepage == null) {
            showOnHomepage = false;
        }
        if (pointsForWin == null) {
            pointsForWin = 3;
        }
        if (pointsForDraw == null) {
            pointsForDraw = 1;
        }
        if (pointsForLoss == null) {
            pointsForLoss = 0;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getSeasonLabel() {
        return seasonLabel;
    }

    public void setSeasonLabel(String seasonLabel) {
        this.seasonLabel = seasonLabel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getIsPublished() {
        return isPublished;
    }

    public void setIsPublished(Boolean isPublished) {
        this.isPublished = isPublished;
    }

    public Boolean getShowOnHomepage() {
        return showOnHomepage;
    }

    public void setShowOnHomepage(Boolean showOnHomepage) {
        this.showOnHomepage = showOnHomepage;
    }

    public Integer getHomepageDisplayOrder() {
        return homepageDisplayOrder;
    }

    public void setHomepageDisplayOrder(Integer homepageDisplayOrder) {
        this.homepageDisplayOrder = homepageDisplayOrder;
    }

    public String getStandingsSource() {
        return standingsSource;
    }

    public void setStandingsSource(String standingsSource) {
        this.standingsSource = standingsSource;
    }

    public Integer getPointsForWin() {
        return pointsForWin;
    }

    public void setPointsForWin(Integer pointsForWin) {
        this.pointsForWin = pointsForWin;
    }

    public Integer getPointsForDraw() {
        return pointsForDraw;
    }

    public void setPointsForDraw(Integer pointsForDraw) {
        this.pointsForDraw = pointsForDraw;
    }

    public Integer getPointsForLoss() {
        return pointsForLoss;
    }

    public void setPointsForLoss(Integer pointsForLoss) {
        this.pointsForLoss = pointsForLoss;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof League)) {
            return false;
        }
        return id != null && id.equals(((League) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
