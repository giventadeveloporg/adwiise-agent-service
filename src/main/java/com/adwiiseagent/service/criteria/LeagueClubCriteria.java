package com.adwiiseagent.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

@ParameterObject
public class LeagueClubCriteria implements Serializable, Criteria {

    private Boolean distinct;

    private LongFilter id;
    private StringFilter tenantId;
    private LongFilter leagueId;
    private StringFilter name;
    private StringFilter shortName;
    private StringFilter logoUrl;
    private BooleanFilter isHomeClub;
    private IntegerFilter displayOrder;

    public LeagueClubCriteria() {}

    public LeagueClubCriteria(LeagueClubCriteria other) {
        this.distinct = other.distinct;
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.tenantId = other.optionalTenantId().map(StringFilter::copy).orElse(null);
        this.leagueId = other.optionalLeagueId().map(LongFilter::copy).orElse(null);
        this.name = other.optionalName().map(StringFilter::copy).orElse(null);
        this.shortName = other.optionalShortName().map(StringFilter::copy).orElse(null);
        this.logoUrl = other.optionalLogoUrl().map(StringFilter::copy).orElse(null);
        this.isHomeClub = other.optionalIsHomeClub().map(BooleanFilter::copy).orElse(null);
        this.displayOrder = other.optionalDisplayOrder().map(IntegerFilter::copy).orElse(null);
    }

    @Override
    public LeagueClubCriteria copy() { return new LeagueClubCriteria(this); }

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

    public StringFilter getName() { return name; }
    public Optional<StringFilter> optionalName() { return Optional.ofNullable(name); }
    public StringFilter name() { if (name == null) setName(new StringFilter()); return name; }
    public void setName(StringFilter name) { this.name = name; }

    public StringFilter getShortName() { return shortName; }
    public Optional<StringFilter> optionalShortName() { return Optional.ofNullable(shortName); }
    public StringFilter shortName() { if (shortName == null) setShortName(new StringFilter()); return shortName; }
    public void setShortName(StringFilter shortName) { this.shortName = shortName; }

    public StringFilter getLogoUrl() { return logoUrl; }
    public Optional<StringFilter> optionalLogoUrl() { return Optional.ofNullable(logoUrl); }
    public StringFilter logoUrl() { if (logoUrl == null) setLogoUrl(new StringFilter()); return logoUrl; }
    public void setLogoUrl(StringFilter logoUrl) { this.logoUrl = logoUrl; }

    public BooleanFilter getIsHomeClub() { return isHomeClub; }
    public Optional<BooleanFilter> optionalIsHomeClub() { return Optional.ofNullable(isHomeClub); }
    public BooleanFilter isHomeClub() { if (isHomeClub == null) setIsHomeClub(new BooleanFilter()); return isHomeClub; }
    public void setIsHomeClub(BooleanFilter isHomeClub) { this.isHomeClub = isHomeClub; }

    public IntegerFilter getDisplayOrder() { return displayOrder; }
    public Optional<IntegerFilter> optionalDisplayOrder() { return Optional.ofNullable(displayOrder); }
    public IntegerFilter displayOrder() { if (displayOrder == null) setDisplayOrder(new IntegerFilter()); return displayOrder; }
    public void setDisplayOrder(IntegerFilter displayOrder) { this.displayOrder = displayOrder; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LeagueClubCriteria that)) return false;
        return Objects.equals(distinct, that.distinct) &&
            Objects.equals(id, that.id) &&
            Objects.equals(tenantId, that.tenantId) &&
            Objects.equals(leagueId, that.leagueId) &&
            Objects.equals(name, that.name) &&
            Objects.equals(shortName, that.shortName) &&
            Objects.equals(logoUrl, that.logoUrl) &&
            Objects.equals(isHomeClub, that.isHomeClub) &&
            Objects.equals(displayOrder, that.displayOrder);
    }

    @Override
    public int hashCode() { return Objects.hash(distinct, id, tenantId, leagueId, name, shortName, logoUrl, isHomeClub, displayOrder); }
}
