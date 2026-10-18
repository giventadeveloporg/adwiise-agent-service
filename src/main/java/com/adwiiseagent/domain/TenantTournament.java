package com.adwiiseagent.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * One branded tournament hosted by a tenant. The menu uses shortLabel, not a year.
 * Separate from event-bound competitions.
 */
@Entity
@Table(
    name = "tenant_tournament",
    uniqueConstraints = {
        @UniqueConstraint(name = "ux_tenant_tournament__tenant_slug", columnNames = { "tenant_id", "slug" }),
        @UniqueConstraint(name = "ux_tenant_tournament__tenant_short_label", columnNames = { "tenant_id", "short_label" }),
    }
)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TenantTournament implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tenantTournamentSeq")
    @SequenceGenerator(name = "tenantTournamentSeq", sequenceName = "public.tenant_tournament_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "tenant_id", length = 255, nullable = false)
    private String tenantId;

    @NotNull
    @Size(max = 255)
    @Column(name = "title", length = 255, nullable = false)
    private String title;

    @NotNull
    @Size(max = 40)
    @Column(name = "short_label", length = 40, nullable = false)
    private String shortLabel;

    @Column(name = "edition_year")
    private Integer editionYear;

    @NotNull
    @Size(max = 100)
    @Column(name = "slug", length = 100, nullable = false)
    private String slug;

    @NotNull
    @Column(name = "show_in_header", nullable = false)
    private Boolean showInHeader = false;

    @NotNull
    @Size(max = 30)
    @Column(name = "status", length = 30, nullable = false)
    private String status = "UPCOMING";

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @NotNull
    @Column(name = "is_published", nullable = false)
    private Boolean isPublished = false;

    @Column(name = "welcome_text", columnDefinition = "TEXT")
    private String welcomeText;

    @Column(name = "about_text", columnDefinition = "TEXT")
    private String aboutText;

    @Column(name = "details_text", columnDefinition = "TEXT")
    private String detailsText;

    @Size(max = 64)
    @Column(name = "age_group_label", length = 64)
    private String ageGroupLabel;

    @Size(max = 255)
    @Column(name = "chief_guest_name", length = 255)
    private String chiefGuestName;

    @Size(max = 1024)
    @Column(name = "chief_guest_image_url", length = 1024)
    private String chiefGuestImageUrl;

    @Size(max = 1024)
    @Column(name = "hero_image_url", length = 1024)
    private String heroImageUrl;

    @Size(max = 1024)
    @Column(name = "fixtures_image_url", length = 1024)
    private String fixturesImageUrl;

    @Size(max = 255)
    @Column(name = "registration_email", length = 255)
    private String registrationEmail;

    @Size(max = 1024)
    @Column(name = "external_results_url", length = 1024)
    private String externalResultsUrl;

    @Size(max = 128)
    @Column(name = "external_results_label", length = 128)
    private String externalResultsLabel;

    @Size(max = 1024)
    @Column(name = "legacy_page_url", length = 1024)
    private String legacyPageUrl;

    @Size(max = 255)
    @Column(name = "winner_name", length = 255)
    private String winnerName;

    @Size(max = 255)
    @Column(name = "runner_up_name", length = 255)
    private String runnerUpName;

    @Size(max = 255)
    @Column(name = "third_place_name", length = 255)
    private String thirdPlaceName;

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
        if (showInHeader == null) {
            showInHeader = false;
        }
        if (isPublished == null) {
            isPublished = false;
        }
        if (status == null || status.isBlank()) {
            status = "UPCOMING";
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }

    public Long getId() {
        return this.id;
    }

    public TenantTournament id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTenantId() {
        return this.tenantId;
    }

    public TenantTournament tenantId(String tenantId) {
        this.setTenantId(tenantId);
        return this;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getTitle() {
        return this.title;
    }

    public TenantTournament title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortLabel() {
        return this.shortLabel;
    }

    public TenantTournament shortLabel(String shortLabel) {
        this.setShortLabel(shortLabel);
        return this;
    }

    public void setShortLabel(String shortLabel) {
        this.shortLabel = shortLabel;
    }

    public Integer getEditionYear() {
        return this.editionYear;
    }

    public TenantTournament editionYear(Integer editionYear) {
        this.setEditionYear(editionYear);
        return this;
    }

    public void setEditionYear(Integer editionYear) {
        this.editionYear = editionYear;
    }

    public String getSlug() {
        return this.slug;
    }

    public TenantTournament slug(String slug) {
        this.setSlug(slug);
        return this;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Boolean getShowInHeader() {
        return this.showInHeader;
    }

    public TenantTournament showInHeader(Boolean showInHeader) {
        this.setShowInHeader(showInHeader);
        return this;
    }

    public void setShowInHeader(Boolean showInHeader) {
        this.showInHeader = showInHeader;
    }

    public String getStatus() {
        return this.status;
    }

    public TenantTournament status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public TenantTournament startDate(LocalDate startDate) {
        this.setStartDate(startDate);
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public TenantTournament endDate(LocalDate endDate) {
        this.setEndDate(endDate);
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Boolean getIsPublished() {
        return this.isPublished;
    }

    public TenantTournament isPublished(Boolean isPublished) {
        this.setIsPublished(isPublished);
        return this;
    }

    public void setIsPublished(Boolean isPublished) {
        this.isPublished = isPublished;
    }

    public String getWelcomeText() {
        return this.welcomeText;
    }

    public TenantTournament welcomeText(String welcomeText) {
        this.setWelcomeText(welcomeText);
        return this;
    }

    public void setWelcomeText(String welcomeText) {
        this.welcomeText = welcomeText;
    }

    public String getAboutText() {
        return this.aboutText;
    }

    public TenantTournament aboutText(String aboutText) {
        this.setAboutText(aboutText);
        return this;
    }

    public void setAboutText(String aboutText) {
        this.aboutText = aboutText;
    }

    public String getDetailsText() {
        return this.detailsText;
    }

    public TenantTournament detailsText(String detailsText) {
        this.setDetailsText(detailsText);
        return this;
    }

    public void setDetailsText(String detailsText) {
        this.detailsText = detailsText;
    }

    public String getAgeGroupLabel() {
        return this.ageGroupLabel;
    }

    public TenantTournament ageGroupLabel(String ageGroupLabel) {
        this.setAgeGroupLabel(ageGroupLabel);
        return this;
    }

    public void setAgeGroupLabel(String ageGroupLabel) {
        this.ageGroupLabel = ageGroupLabel;
    }

    public String getChiefGuestName() {
        return this.chiefGuestName;
    }

    public TenantTournament chiefGuestName(String chiefGuestName) {
        this.setChiefGuestName(chiefGuestName);
        return this;
    }

    public void setChiefGuestName(String chiefGuestName) {
        this.chiefGuestName = chiefGuestName;
    }

    public String getChiefGuestImageUrl() {
        return this.chiefGuestImageUrl;
    }

    public TenantTournament chiefGuestImageUrl(String chiefGuestImageUrl) {
        this.setChiefGuestImageUrl(chiefGuestImageUrl);
        return this;
    }

    public void setChiefGuestImageUrl(String chiefGuestImageUrl) {
        this.chiefGuestImageUrl = chiefGuestImageUrl;
    }

    public String getHeroImageUrl() {
        return this.heroImageUrl;
    }

    public TenantTournament heroImageUrl(String heroImageUrl) {
        this.setHeroImageUrl(heroImageUrl);
        return this;
    }

    public void setHeroImageUrl(String heroImageUrl) {
        this.heroImageUrl = heroImageUrl;
    }

    public String getFixturesImageUrl() {
        return this.fixturesImageUrl;
    }

    public TenantTournament fixturesImageUrl(String fixturesImageUrl) {
        this.setFixturesImageUrl(fixturesImageUrl);
        return this;
    }

    public void setFixturesImageUrl(String fixturesImageUrl) {
        this.fixturesImageUrl = fixturesImageUrl;
    }

    public String getRegistrationEmail() {
        return this.registrationEmail;
    }

    public TenantTournament registrationEmail(String registrationEmail) {
        this.setRegistrationEmail(registrationEmail);
        return this;
    }

    public void setRegistrationEmail(String registrationEmail) {
        this.registrationEmail = registrationEmail;
    }

    public String getExternalResultsUrl() {
        return this.externalResultsUrl;
    }

    public TenantTournament externalResultsUrl(String externalResultsUrl) {
        this.setExternalResultsUrl(externalResultsUrl);
        return this;
    }

    public void setExternalResultsUrl(String externalResultsUrl) {
        this.externalResultsUrl = externalResultsUrl;
    }

    public String getExternalResultsLabel() {
        return this.externalResultsLabel;
    }

    public TenantTournament externalResultsLabel(String externalResultsLabel) {
        this.setExternalResultsLabel(externalResultsLabel);
        return this;
    }

    public void setExternalResultsLabel(String externalResultsLabel) {
        this.externalResultsLabel = externalResultsLabel;
    }

    public String getLegacyPageUrl() {
        return this.legacyPageUrl;
    }

    public TenantTournament legacyPageUrl(String legacyPageUrl) {
        this.setLegacyPageUrl(legacyPageUrl);
        return this;
    }

    public void setLegacyPageUrl(String legacyPageUrl) {
        this.legacyPageUrl = legacyPageUrl;
    }

    public String getWinnerName() {
        return this.winnerName;
    }

    public TenantTournament winnerName(String winnerName) {
        this.setWinnerName(winnerName);
        return this;
    }

    public void setWinnerName(String winnerName) {
        this.winnerName = winnerName;
    }

    public String getRunnerUpName() {
        return this.runnerUpName;
    }

    public TenantTournament runnerUpName(String runnerUpName) {
        this.setRunnerUpName(runnerUpName);
        return this;
    }

    public void setRunnerUpName(String runnerUpName) {
        this.runnerUpName = runnerUpName;
    }

    public String getThirdPlaceName() {
        return this.thirdPlaceName;
    }

    public TenantTournament thirdPlaceName(String thirdPlaceName) {
        this.setThirdPlaceName(thirdPlaceName);
        return this;
    }

    public void setThirdPlaceName(String thirdPlaceName) {
        this.thirdPlaceName = thirdPlaceName;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public TenantTournament createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public TenantTournament updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TenantTournament)) {
            return false;
        }
        return getId() != null && getId().equals(((TenantTournament) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return (
            "TenantTournament{id=" +
            getId() +
            ", tenantId='" +
            getTenantId() +
            "', shortLabel='" +
            getShortLabel() +
            "', title='" +
            getTitle() +
            "', slug='" +
            getSlug() +
            "', status='" +
            getStatus() +
            "'}"
        );
    }
}
