package com.adwiiseagent.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

@ParameterObject
public class LeagueStandingCriteria implements Serializable, Criteria {

    private Boolean distinct;

    private LongFilter id;
    private StringFilter tenantId;
    private LongFilter leagueId;
    private LongFilter leagueClubId;
    private IntegerFilter played;
    private IntegerFilter won;
    private IntegerFilter drawn;
    private IntegerFilter lost;
    private IntegerFilter goalsFor;
    private IntegerFilter goalsAgainst;
    private IntegerFilter points;
    private IntegerFilter position;

    public LeagueStandingCriteria() {}

    public LeagueStandingCriteria(LeagueStandingCriteria other) {
        this.distinct = other.distinct;
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.tenantId = other.optionalTenantId().map(StringFilter::copy).orElse(null);
        this.leagueId = other.optionalLeagueId().map(LongFilter::copy).orElse(null);
        this.leagueClubId = other.optionalLeagueClubId().map(LongFilter::copy).orElse(null);
        this.played = other.optionalPlayed().map(IntegerFilter::copy).orElse(null);
        this.won = other.optionalWon().map(IntegerFilter::copy).orElse(null);
        this.drawn = other.optionalDrawn().map(IntegerFilter::copy).orElse(null);
        this.lost = other.optionalLost().map(IntegerFilter::copy).orElse(null);
        this.goalsFor = other.optionalGoalsFor().map(IntegerFilter::copy).orElse(null);
        this.goalsAgainst = other.optionalGoalsAgainst().map(IntegerFilter::copy).orElse(null);
        this.points = other.optionalPoints().map(IntegerFilter::copy).orElse(null);
        this.position = other.optionalPosition().map(IntegerFilter::copy).orElse(null);
    }

    @Override
    public LeagueStandingCriteria copy() { return new LeagueStandingCriteria(this); }

    public Boolean getDistinct() { return distinct; }
    public Optional<Boolean> optionalDistinct() { return Optional.ofNullable(distinct); }
    public void setDistinct(Boolean distinct) { this.distinct = distinct; }

    public LongFilter getId() { return id; }
    public Optional<LongFilter> optionalId() { return Optional.ofNullable(id); }
    public LongFilter id() { if (id == null) setId(new LongFilter()); return id; }
    public void setId(LongFilter id) { this.id = id; }

    public StringFilter getTenantId() { return tenantId; }
    public Optional<StringFilter> optionalTenantId() { return Optional.ofNullable(tenantId); }
    public StringFilter tenantId() { if (tenantId == null) setTenantId(new StringFilter()); return tenantId; }
    public void setTenantId(StringFilter tenantId) { this.tenantId = tenantId; }

    public LongFilter getLeagueId() { return leagueId; }
    public Optional<LongFilter> optionalLeagueId() { return Optional.ofNullable(leagueId); }
    public LongFilter leagueId() { if (leagueId == null) setLeagueId(new LongFilter()); return leagueId; }
    public void setLeagueId(LongFilter leagueId) { this.leagueId = leagueId; }

    public LongFilter getLeagueClubId() { return leagueClubId; }
    public Optional<LongFilter> optionalLeagueClubId() { return Optional.ofNullable(leagueClubId); }
    public LongFilter leagueClubId() { if (leagueClubId == null) setLeagueClubId(new LongFilter()); return leagueClubId; }
    public void setLeagueClubId(LongFilter leagueClubId) { this.leagueClubId = leagueClubId; }

    public IntegerFilter getPlayed() { return played; }
    public Optional<IntegerFilter> optionalPlayed() { return Optional.ofNullable(played); }
    public IntegerFilter played() { if (played == null) setPlayed(new IntegerFilter()); return played; }
    public void setPlayed(IntegerFilter played) { this.played = played; }

    public IntegerFilter getWon() { return won; }
    public Optional<IntegerFilter> optionalWon() { return Optional.ofNullable(won); }
    public IntegerFilter won() { if (won == null) setWon(new IntegerFilter()); return won; }
    public void setWon(IntegerFilter won) { this.won = won; }

    public IntegerFilter getDrawn() { return drawn; }
    public Optional<IntegerFilter> optionalDrawn() { return Optional.ofNullable(drawn); }
    public IntegerFilter drawn() { if (drawn == null) setDrawn(new IntegerFilter()); return drawn; }
    public void setDrawn(IntegerFilter drawn) { this.drawn = drawn; }

    public IntegerFilter getLost() { return lost; }
    public Optional<IntegerFilter> optionalLost() { return Optional.ofNullable(lost); }
    public IntegerFilter lost() { if (lost == null) setLost(new IntegerFilter()); return lost; }
    public void setLost(IntegerFilter lost) { this.lost = lost; }

    public IntegerFilter getGoalsFor() { return goalsFor; }
    public Optional<IntegerFilter> optionalGoalsFor() { return Optional.ofNullable(goalsFor); }
    public IntegerFilter goalsFor() { if (goalsFor == null) setGoalsFor(new IntegerFilter()); return goalsFor; }
    public void setGoalsFor(IntegerFilter goalsFor) { this.goalsFor = goalsFor; }

    public IntegerFilter getGoalsAgainst() { return goalsAgainst; }
    public Optional<IntegerFilter> optionalGoalsAgainst() { return Optional.ofNullable(goalsAgainst); }
    public IntegerFilter goalsAgainst() { if (goalsAgainst == null) setGoalsAgainst(new IntegerFilter()); return goalsAgainst; }
    public void setGoalsAgainst(IntegerFilter goalsAgainst) { this.goalsAgainst = goalsAgainst; }

    public IntegerFilter getPoints() { return points; }
    public Optional<IntegerFilter> optionalPoints() { return Optional.ofNullable(points); }
    public IntegerFilter points() { if (points == null) setPoints(new IntegerFilter()); return points; }
    public void setPoints(IntegerFilter points) { this.points = points; }

    public IntegerFilter getPosition() { return position; }
    public Optional<IntegerFilter> optionalPosition() { return Optional.ofNullable(position); }
    public IntegerFilter position() { if (position == null) setPosition(new IntegerFilter()); return position; }
    public void setPosition(IntegerFilter position) { this.position = position; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LeagueStandingCriteria that)) return false;
        return Objects.equals(distinct, that.distinct) &&
            Objects.equals(id, that.id) &&
            Objects.equals(tenantId, that.tenantId) &&
            Objects.equals(leagueId, that.leagueId) &&
            Objects.equals(leagueClubId, that.leagueClubId) &&
            Objects.equals(played, that.played) &&
            Objects.equals(won, that.won) &&
            Objects.equals(drawn, that.drawn) &&
            Objects.equals(lost, that.lost) &&
            Objects.equals(goalsFor, that.goalsFor) &&
            Objects.equals(goalsAgainst, that.goalsAgainst) &&
            Objects.equals(points, that.points) &&
            Objects.equals(position, that.position);
    }

    @Override
    public int hashCode() { return Objects.hash(distinct, id, tenantId, leagueId, leagueClubId, played, won, drawn, lost, goalsFor, goalsAgainst, points, position); }
}
