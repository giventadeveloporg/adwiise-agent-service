package com.adwiiseagent.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "league_match")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class LeagueMatch implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "leagueMatchSeq")
    @SequenceGenerator(name = "leagueMatchSeq", sequenceName = "public.league_match_id_seq", allocationSize = 1)
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "tenant_id", length = 255, nullable = false)
    private String tenantId;

    @NotNull
    @Column(name = "league_id", nullable = false)
    private Long leagueId;

    @NotNull
    @Column(name = "home_club_id", nullable = false)
    private Long homeClubId;

    @NotNull
    @Column(name = "away_club_id", nullable = false)
    private Long awayClubId;

    @Column(name = "played_at")
    private Instant playedAt;

    @Size(max = 255)
    @Column(name = "venue", length = 255)
    private String venue;

    @Column(name = "home_score")
    private Integer homeScore;

    @Column(name = "away_score")
    private Integer awayScore;

    @NotNull
    @Size(max = 32)
    @Column(name = "status", length = 32, nullable = false)
    private String status;

    @Size(max = 1000)
    @Column(name = "notes", length = 1000)
    private String notes;

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
            status = "SCHEDULED";
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

    public Long getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(Long leagueId) {
        this.leagueId = leagueId;
    }

    public Long getHomeClubId() {
        return homeClubId;
    }

    public void setHomeClubId(Long homeClubId) {
        this.homeClubId = homeClubId;
    }

    public Long getAwayClubId() {
        return awayClubId;
    }

    public void setAwayClubId(Long awayClubId) {
        this.awayClubId = awayClubId;
    }

    public Instant getPlayedAt() {
        return playedAt;
    }

    public void setPlayedAt(Instant playedAt) {
        this.playedAt = playedAt;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public Integer getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(Integer homeScore) {
        this.homeScore = homeScore;
    }

    public Integer getAwayScore() {
        return awayScore;
    }

    public void setAwayScore(Integer awayScore) {
        this.awayScore = awayScore;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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
        if (!(o instanceof LeagueMatch)) {
            return false;
        }
        return id != null && id.equals(((LeagueMatch) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
