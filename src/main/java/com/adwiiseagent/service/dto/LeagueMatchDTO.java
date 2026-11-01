package com.adwiiseagent.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.time.Instant;

public class LeagueMatchDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String tenantId;

    @NotNull
    private Long leagueId;

    @NotNull
    private Long homeClubId;

    @NotNull
    private Long awayClubId;

    private Instant playedAt;

    @Size(max = 255)
    private String venue;

    private Integer homeScore;

    private Integer awayScore;

    @NotNull
    @Size(max = 32)
    private String status;

    @Size(max = 1000)
    private String notes;

    private Instant createdAt;

    private Instant updatedAt;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }

    public Long getLeagueId() { return leagueId; }
    public void setLeagueId(Long leagueId) { this.leagueId = leagueId; }

    public Long getHomeClubId() { return homeClubId; }
    public void setHomeClubId(Long homeClubId) { this.homeClubId = homeClubId; }

    public Long getAwayClubId() { return awayClubId; }
    public void setAwayClubId(Long awayClubId) { this.awayClubId = awayClubId; }

    public Instant getPlayedAt() { return playedAt; }
    public void setPlayedAt(Instant playedAt) { this.playedAt = playedAt; }

    public String getVenue() { return venue; }
    public void setVenue(String venue) { this.venue = venue; }

    public Integer getHomeScore() { return homeScore; }
    public void setHomeScore(Integer homeScore) { this.homeScore = homeScore; }

    public Integer getAwayScore() { return awayScore; }
    public void setAwayScore(Integer awayScore) { this.awayScore = awayScore; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof LeagueMatchDTO other)) return false;
        return id != null && id.equals(other.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }
}
