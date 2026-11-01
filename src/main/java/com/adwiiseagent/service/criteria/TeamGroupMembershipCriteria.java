package com.adwiiseagent.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

@ParameterObject
public class TeamGroupMembershipCriteria implements Serializable, Criteria {

    private Boolean distinct;

    private LongFilter id;
    private StringFilter tenantId;
    private LongFilter teamGroupId;
    private LongFilter teamMemberId;
    private IntegerFilter jerseyNumber;
    private StringFilter position;
    private BooleanFilter isActive;

    public TeamGroupMembershipCriteria() {}

    public TeamGroupMembershipCriteria(TeamGroupMembershipCriteria other) {
        this.distinct = other.distinct;
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.tenantId = other.optionalTenantId().map(StringFilter::copy).orElse(null);
        this.teamGroupId = other.optionalTeamGroupId().map(LongFilter::copy).orElse(null);
        this.teamMemberId = other.optionalTeamMemberId().map(LongFilter::copy).orElse(null);
        this.jerseyNumber = other.optionalJerseyNumber().map(IntegerFilter::copy).orElse(null);
        this.position = other.optionalPosition().map(StringFilter::copy).orElse(null);
        this.isActive = other.optionalIsActive().map(BooleanFilter::copy).orElse(null);
    }

    @Override
    public TeamGroupMembershipCriteria copy() { return new TeamGroupMembershipCriteria(this); }

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

    public LongFilter getTeamGroupId() { return teamGroupId; }
    public Optional<LongFilter> optionalTeamGroupId() { return Optional.ofNullable(teamGroupId); }
    public LongFilter teamGroupId() { if (teamGroupId == null) setTeamGroupId(new LongFilter()); return teamGroupId; }
    public void setTeamGroupId(LongFilter teamGroupId) { this.teamGroupId = teamGroupId; }

    public LongFilter getTeamMemberId() { return teamMemberId; }
    public Optional<LongFilter> optionalTeamMemberId() { return Optional.ofNullable(teamMemberId); }
    public LongFilter teamMemberId() { if (teamMemberId == null) setTeamMemberId(new LongFilter()); return teamMemberId; }
    public void setTeamMemberId(LongFilter teamMemberId) { this.teamMemberId = teamMemberId; }

    public IntegerFilter getJerseyNumber() { return jerseyNumber; }
    public Optional<IntegerFilter> optionalJerseyNumber() { return Optional.ofNullable(jerseyNumber); }
    public IntegerFilter jerseyNumber() { if (jerseyNumber == null) setJerseyNumber(new IntegerFilter()); return jerseyNumber; }
    public void setJerseyNumber(IntegerFilter jerseyNumber) { this.jerseyNumber = jerseyNumber; }

    public StringFilter getPosition() { return position; }
    public Optional<StringFilter> optionalPosition() { return Optional.ofNullable(position); }
    public StringFilter position() { if (position == null) setPosition(new StringFilter()); return position; }
    public void setPosition(StringFilter position) { this.position = position; }

    public BooleanFilter getIsActive() { return isActive; }
    public Optional<BooleanFilter> optionalIsActive() { return Optional.ofNullable(isActive); }
    public BooleanFilter isActive() { if (isActive == null) setIsActive(new BooleanFilter()); return isActive; }
    public void setIsActive(BooleanFilter isActive) { this.isActive = isActive; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TeamGroupMembershipCriteria that)) return false;
        return Objects.equals(distinct, that.distinct) &&
            Objects.equals(id, that.id) &&
            Objects.equals(tenantId, that.tenantId) &&
            Objects.equals(teamGroupId, that.teamGroupId) &&
            Objects.equals(teamMemberId, that.teamMemberId) &&
            Objects.equals(jerseyNumber, that.jerseyNumber) &&
            Objects.equals(position, that.position) &&
            Objects.equals(isActive, that.isActive);
    }

    @Override
    public int hashCode() { return Objects.hash(distinct, id, tenantId, teamGroupId, teamMemberId, jerseyNumber, position, isActive); }
}
