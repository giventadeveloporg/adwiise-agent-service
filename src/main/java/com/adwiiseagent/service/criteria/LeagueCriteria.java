package com.adwiiseagent.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

@ParameterObject
public class LeagueCriteria implements Serializable, Criteria {

    private Boolean distinct;

    private LongFilter id;
    private StringFilter tenantId;
    private StringFilter name;
    private StringFilter slug;
    private StringFilter seasonLabel;
    private StringFilter description;
    private StringFilter logoUrl;
    private StringFilter status;
    private BooleanFilter isPublished;
    private BooleanFilter showOnHomepage;
    private IntegerFilter homepageDisplayOrder;
    private StringFilter standingsSource;
    private IntegerFilter pointsForWin;
    private IntegerFilter pointsForDraw;
    private IntegerFilter pointsForLoss;

    public LeagueCriteria() {}

    public LeagueCriteria(LeagueCriteria other) {
        this.distinct = other.distinct;
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.tenantId = other.optionalTenantId().map(StringFilter::copy).orElse(null);
        this.name = other.optionalName().map(StringFilter::copy).orElse(null);
        this.slug = other.optionalSlug().map(StringFilter::copy).orElse(null);
        this.seasonLabel = other.optionalSeasonLabel().map(StringFilter::copy).orElse(null);
        this.description = other.optionalDescription().map(StringFilter::copy).orElse(null);
        this.logoUrl = other.optionalLogoUrl().map(StringFilter::copy).orElse(null);
        this.status = other.optionalStatus().map(StringFilter::copy).orElse(null);
        this.isPublished = other.optionalIsPublished().map(BooleanFilter::copy).orElse(null);
        this.showOnHomepage = other.optionalShowOnHomepage().map(BooleanFilter::copy).orElse(null);
        this.homepageDisplayOrder = other.optionalHomepageDisplayOrder().map(IntegerFilter::copy).orElse(null);
        this.standingsSource = other.optionalStandingsSource().map(StringFilter::copy).orElse(null);
        this.pointsForWin = other.optionalPointsForWin().map(IntegerFilter::copy).orElse(null);
        this.pointsForDraw = other.optionalPointsForDraw().map(IntegerFilter::copy).orElse(null);
        this.pointsForLoss = other.optionalPointsForLoss().map(IntegerFilter::copy).orElse(null);
    }

    @Override
    public LeagueCriteria copy() { return new LeagueCriteria(this); }

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

    public StringFilter getName() { return name; }
    public Optional<StringFilter> optionalName() { return Optional.ofNullable(name); }
    public StringFilter name() { if (name == null) setName(new StringFilter()); return name; }
    public void setName(StringFilter name) { this.name = name; }

    public StringFilter getSlug() { return slug; }
    public Optional<StringFilter> optionalSlug() { return Optional.ofNullable(slug); }
    public StringFilter slug() { if (slug == null) setSlug(new StringFilter()); return slug; }
    public void setSlug(StringFilter slug) { this.slug = slug; }

    public StringFilter getSeasonLabel() { return seasonLabel; }
    public Optional<StringFilter> optionalSeasonLabel() { return Optional.ofNullable(seasonLabel); }
    public StringFilter seasonLabel() { if (seasonLabel == null) setSeasonLabel(new StringFilter()); return seasonLabel; }
    public void setSeasonLabel(StringFilter seasonLabel) { this.seasonLabel = seasonLabel; }

    public StringFilter getDescription() { return description; }
    public Optional<StringFilter> optionalDescription() { return Optional.ofNullable(description); }
    public StringFilter description() { if (description == null) setDescription(new StringFilter()); return description; }
    public void setDescription(StringFilter description) { this.description = description; }

    public StringFilter getLogoUrl() { return logoUrl; }
    public Optional<StringFilter> optionalLogoUrl() { return Optional.ofNullable(logoUrl); }
    public StringFilter logoUrl() { if (logoUrl == null) setLogoUrl(new StringFilter()); return logoUrl; }
    public void setLogoUrl(StringFilter logoUrl) { this.logoUrl = logoUrl; }

    public StringFilter getStatus() { return status; }
    public Optional<StringFilter> optionalStatus() { return Optional.ofNullable(status); }
    public StringFilter status() { if (status == null) setStatus(new StringFilter()); return status; }
    public void setStatus(StringFilter status) { this.status = status; }

    public BooleanFilter getIsPublished() { return isPublished; }
    public Optional<BooleanFilter> optionalIsPublished() { return Optional.ofNullable(isPublished); }
    public BooleanFilter isPublished() { if (isPublished == null) setIsPublished(new BooleanFilter()); return isPublished; }
    public void setIsPublished(BooleanFilter isPublished) { this.isPublished = isPublished; }

    public BooleanFilter getShowOnHomepage() { return showOnHomepage; }
    public Optional<BooleanFilter> optionalShowOnHomepage() { return Optional.ofNullable(showOnHomepage); }
    public BooleanFilter showOnHomepage() { if (showOnHomepage == null) setShowOnHomepage(new BooleanFilter()); return showOnHomepage; }
    public void setShowOnHomepage(BooleanFilter showOnHomepage) { this.showOnHomepage = showOnHomepage; }

    public IntegerFilter getHomepageDisplayOrder() { return homepageDisplayOrder; }
    public Optional<IntegerFilter> optionalHomepageDisplayOrder() { return Optional.ofNullable(homepageDisplayOrder); }
    public IntegerFilter homepageDisplayOrder() { if (homepageDisplayOrder == null) setHomepageDisplayOrder(new IntegerFilter()); return homepageDisplayOrder; }
    public void setHomepageDisplayOrder(IntegerFilter homepageDisplayOrder) { this.homepageDisplayOrder = homepageDisplayOrder; }

    public StringFilter getStandingsSource() { return standingsSource; }
    public Optional<StringFilter> optionalStandingsSource() { return Optional.ofNullable(standingsSource); }
    public StringFilter standingsSource() { if (standingsSource == null) setStandingsSource(new StringFilter()); return standingsSource; }
    public void setStandingsSource(StringFilter standingsSource) { this.standingsSource = standingsSource; }

    public IntegerFilter getPointsForWin() { return pointsForWin; }
    public Optional<IntegerFilter> optionalPointsForWin() { return Optional.ofNullable(pointsForWin); }
    public IntegerFilter pointsForWin() { if (pointsForWin == null) setPointsForWin(new IntegerFilter()); return pointsForWin; }
    public void setPointsForWin(IntegerFilter pointsForWin) { this.pointsForWin = pointsForWin; }

    public IntegerFilter getPointsForDraw() { return pointsForDraw; }
    public Optional<IntegerFilter> optionalPointsForDraw() { return Optional.ofNullable(pointsForDraw); }
    public IntegerFilter pointsForDraw() { if (pointsForDraw == null) setPointsForDraw(new IntegerFilter()); return pointsForDraw; }
    public void setPointsForDraw(IntegerFilter pointsForDraw) { this.pointsForDraw = pointsForDraw; }

    public IntegerFilter getPointsForLoss() { return pointsForLoss; }
    public Optional<IntegerFilter> optionalPointsForLoss() { return Optional.ofNullable(pointsForLoss); }
    public IntegerFilter pointsForLoss() { if (pointsForLoss == null) setPointsForLoss(new IntegerFilter()); return pointsForLoss; }
    public void setPointsForLoss(IntegerFilter pointsForLoss) { this.pointsForLoss = pointsForLoss; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LeagueCriteria that)) return false;
        return Objects.equals(distinct, that.distinct) &&
            Objects.equals(id, that.id) &&
            Objects.equals(tenantId, that.tenantId) &&
            Objects.equals(name, that.name) &&
            Objects.equals(slug, that.slug) &&
            Objects.equals(seasonLabel, that.seasonLabel) &&
            Objects.equals(description, that.description) &&
            Objects.equals(logoUrl, that.logoUrl) &&
            Objects.equals(status, that.status) &&
            Objects.equals(isPublished, that.isPublished) &&
            Objects.equals(showOnHomepage, that.showOnHomepage) &&
            Objects.equals(homepageDisplayOrder, that.homepageDisplayOrder) &&
            Objects.equals(standingsSource, that.standingsSource) &&
            Objects.equals(pointsForWin, that.pointsForWin) &&
            Objects.equals(pointsForDraw, that.pointsForDraw) &&
            Objects.equals(pointsForLoss, that.pointsForLoss);
    }

    @Override
    public int hashCode() { return Objects.hash(distinct, id, tenantId, name, slug, seasonLabel, description, logoUrl, status, isPublished, showOnHomepage, homepageDisplayOrder, standingsSource, pointsForWin, pointsForDraw, pointsForLoss); }
}
