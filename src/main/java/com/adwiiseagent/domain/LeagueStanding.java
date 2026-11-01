package com.adwiiseagent.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "league_standing")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class LeagueStanding implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "leagueStandingSeq")
    @SequenceGenerator(name = "leagueStandingSeq", sequenceName = "public.league_standing_id_seq", allocationSize = 1)
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "tenant_id", length = 255, nullable = false)
    private String tenantId;

    @NotNull
    @Column(name = "league_id", nullable = false)
    private Long leagueId;

    @NotNull
    @Column(name = "league_club_id", nullable = false)
    private Long leagueClubId;

    @NotNull
    @Column(name = "played", nullable = false)
    private Integer played;

    @NotNull
    @Column(name = "won", nullable = false)
    private Integer won;

    @NotNull
    @Column(name = "drawn", nullable = false)
    private Integer drawn;

    @NotNull
    @Column(name = "lost", nullable = false)
    private Integer lost;

    @NotNull
    @Column(name = "goals_for", nullable = false)
    private Integer goalsFor;

    @NotNull
    @Column(name = "goals_against", nullable = false)
    private Integer goalsAgainst;

    @NotNull
    @Column(name = "points", nullable = false)
    private Integer points;

    @Column(name = "position")
    private Integer position;

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
        if (played == null) {
            played = 0;
        }
        if (won == null) {
            won = 0;
        }
        if (drawn == null) {
            drawn = 0;
        }
        if (lost == null) {
            lost = 0;
        }
        if (goalsFor == null) {
            goalsFor = 0;
        }
        if (goalsAgainst == null) {
            goalsAgainst = 0;
        }
        if (points == null) {
            points = 0;
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

    public Long getLeagueClubId() {
        return leagueClubId;
    }

    public void setLeagueClubId(Long leagueClubId) {
        this.leagueClubId = leagueClubId;
    }

    public Integer getPlayed() {
        return played;
    }

    public void setPlayed(Integer played) {
        this.played = played;
    }

    public Integer getWon() {
        return won;
    }

    public void setWon(Integer won) {
        this.won = won;
    }

    public Integer getDrawn() {
        return drawn;
    }

    public void setDrawn(Integer drawn) {
        this.drawn = drawn;
    }

    public Integer getLost() {
        return lost;
    }

    public void setLost(Integer lost) {
        this.lost = lost;
    }

    public Integer getGoalsFor() {
        return goalsFor;
    }

    public void setGoalsFor(Integer goalsFor) {
        this.goalsFor = goalsFor;
    }

    public Integer getGoalsAgainst() {
        return goalsAgainst;
    }

    public void setGoalsAgainst(Integer goalsAgainst) {
        this.goalsAgainst = goalsAgainst;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
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
        if (!(o instanceof LeagueStanding)) {
            return false;
        }
        return id != null && id.equals(((LeagueStanding) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
