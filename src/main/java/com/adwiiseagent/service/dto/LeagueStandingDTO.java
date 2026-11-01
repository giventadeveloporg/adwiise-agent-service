package com.adwiiseagent.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.time.Instant;

public class LeagueStandingDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String tenantId;

    @NotNull
    private Long leagueId;

    @NotNull
    private Long leagueClubId;

    @NotNull
    private Integer played;

    @NotNull
    private Integer won;

    @NotNull
    private Integer drawn;

    @NotNull
    private Integer lost;

    @NotNull
    private Integer goalsFor;

    @NotNull
    private Integer goalsAgainst;

    @NotNull
    private Integer points;

    private Integer position;

    private Instant createdAt;

    private Instant updatedAt;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }

    public Long getLeagueId() { return leagueId; }
    public void setLeagueId(Long leagueId) { this.leagueId = leagueId; }

    public Long getLeagueClubId() { return leagueClubId; }
    public void setLeagueClubId(Long leagueClubId) { this.leagueClubId = leagueClubId; }

    public Integer getPlayed() { return played; }
    public void setPlayed(Integer played) { this.played = played; }

    public Integer getWon() { return won; }
    public void setWon(Integer won) { this.won = won; }

    public Integer getDrawn() { return drawn; }
    public void setDrawn(Integer drawn) { this.drawn = drawn; }

    public Integer getLost() { return lost; }
    public void setLost(Integer lost) { this.lost = lost; }

    public Integer getGoalsFor() { return goalsFor; }
    public void setGoalsFor(Integer goalsFor) { this.goalsFor = goalsFor; }

    public Integer getGoalsAgainst() { return goalsAgainst; }
    public void setGoalsAgainst(Integer goalsAgainst) { this.goalsAgainst = goalsAgainst; }

    public Integer getPoints() { return points; }
    public void setPoints(Integer points) { this.points = points; }

    public Integer getPosition() { return position; }
    public void setPosition(Integer position) { this.position = position; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof LeagueStandingDTO other)) return false;
        return id != null && id.equals(other.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }
}
