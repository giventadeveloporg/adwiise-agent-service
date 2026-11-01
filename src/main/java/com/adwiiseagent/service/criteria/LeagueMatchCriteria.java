package com.adwiiseagent.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

@ParameterObject
public class LeagueMatchCriteria implements Serializable, Criteria {

    private Boolean distinct;

    private LongFilter id;
    private StringFilter tenantId;
    private LongFilter leagueId;
    private LongFilter homeClubId;
    private LongFilter awayClubId;
    private StringFilter venue;
    private IntegerFilter homeScore;
    private IntegerFilter awayScore;
    private StringFilter status;
    private StringFilter notes;

    public LeagueMatchCriteria() {}

    public LeagueMatchCriteria(LeagueMatchCriteria other) {
        this.distinct = other.distinct;
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.tenantId = other.optionalTenantId().map(StringFilter::copy).orElse(null);
        this.leagueId = other.optionalLeagueId().map(LongFilter::copy).orElse(null);
        this.homeClubId = other.optionalHomeClubId().map(LongFilter::copy).orElse(null);
        this.awayClubId = other.optionalAwayClubId().map(LongFilter::copy).orElse(null);
        this.venue = other.optionalVenue().map(StringFilter::copy).orElse(null);
        this.homeScore = other.optionalHomeScore().map(IntegerFilter::copy).orElse(null);
        this.awayScore = other.optionalAwayScore().map(IntegerFilter::copy).orElse(null);
        this.status = other.optionalStatus().map(StringFilter::copy).orElse(null);
        this.notes = other.optionalNotes().map(StringFilter::copy).orElse(null);
    }

    @Override
    public LeagueMatchCriteria copy() { return new LeagueMatchCriteria(this); }

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

    public LongFilter getHomeClubId() { return homeClubId; }
    public Optional<LongFilter> optionalHomeClubId() { return Optional.ofNullable(homeClubId); }
    public LongFilter homeClubId() { if (homeClubId == null) setHomeClubId(new LongFilter()); return homeClubId; }
    public void setHomeClubId(LongFilter homeClubId) { this.homeClubId = homeClubId; }

    public LongFilter getAwayClubId() { return awayClubId; }
    public Optional<LongFilter> optionalAwayClubId() { return Optional.ofNullable(awayClubId); }
    public LongFilter awayClubId() { if (awayClubId == null) setAwayClubId(new LongFilter()); return awayClubId; }
    public void setAwayClubId(LongFilter awayClubId) { this.awayClubId = awayClubId; }

    public StringFilter getVenue() { return venue; }
    public Optional<StringFilter> optionalVenue() { return Optional.ofNullable(venue); }
    public StringFilter venue() { if (venue == null) setVenue(new StringFilter()); return venue; }
    public void setVenue(StringFilter venue) { this.venue = venue; }

    public IntegerFilter getHomeScore() { return homeScore; }
    public Optional<IntegerFilter> optionalHomeScore() { return Optional.ofNullable(homeScore); }
    public IntegerFilter homeScore() { if (homeScore == null) setHomeScore(new IntegerFilter()); return homeScore; }
    public void setHomeScore(IntegerFilter homeScore) { this.homeScore = homeScore; }

    public IntegerFilter getAwayScore() { return awayScore; }
    public Optional<IntegerFilter> optionalAwayScore() { return Optional.ofNullable(awayScore); }
    public IntegerFilter awayScore() { if (awayScore == null) setAwayScore(new IntegerFilter()); return awayScore; }
    public void setAwayScore(IntegerFilter awayScore) { this.awayScore = awayScore; }

    public StringFilter getStatus() { return status; }
    public Optional<StringFilter> optionalStatus() { return Optional.ofNullable(status); }
    public StringFilter status() { if (status == null) setStatus(new StringFilter()); return status; }
    public void setStatus(StringFilter status) { this.status = status; }

    public StringFilter getNotes() { return notes; }
    public Optional<StringFilter> optionalNotes() { return Optional.ofNullable(notes); }
    public StringFilter notes() { if (notes == null) setNotes(new StringFilter()); return notes; }
    public void setNotes(StringFilter notes) { this.notes = notes; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LeagueMatchCriteria that)) return false;
        return Objects.equals(distinct, that.distinct) &&
            Objects.equals(id, that.id) &&
            Objects.equals(tenantId, that.tenantId) &&
            Objects.equals(leagueId, that.leagueId) &&
            Objects.equals(homeClubId, that.homeClubId) &&
            Objects.equals(awayClubId, that.awayClubId) &&
            Objects.equals(venue, that.venue) &&
            Objects.equals(homeScore, that.homeScore) &&
            Objects.equals(awayScore, that.awayScore) &&
            Objects.equals(status, that.status) &&
            Objects.equals(notes, that.notes);
    }

    @Override
    public int hashCode() { return Objects.hash(distinct, id, tenantId, leagueId, homeClubId, awayClubId, venue, homeScore, awayScore, status, notes); }
}
