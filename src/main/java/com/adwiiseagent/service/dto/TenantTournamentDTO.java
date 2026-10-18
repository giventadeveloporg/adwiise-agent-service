package com.adwiiseagent.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for {@link com.adwiiseagent.domain.TenantTournament}.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TenantTournamentDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String tenantId;

    @NotNull
    @Size(max = 255)
    private String title;

    @NotNull
    @Size(max = 40)
    private String shortLabel;

    @Min(1990)
    @Max(2100)
    private Integer editionYear;

    @NotNull
    @Size(max = 100)
    @Pattern(regexp = "^[a-z0-9-]+$", message = "Slug must contain only lowercase letters, numbers, and hyphens")
    private String slug;

    private Boolean showInHeader;

    @NotNull
    @Size(max = 30)
    private String status;

    private LocalDate startDate;

    private LocalDate endDate;

    private Boolean isPublished;

    private String welcomeText;

    private String aboutText;

    private String detailsText;

    @Size(max = 64)
    private String ageGroupLabel;

    @Size(max = 255)
    private String chiefGuestName;

    @Size(max = 1024)
    private String chiefGuestImageUrl;

    @Size(max = 1024)
    private String heroImageUrl;

    @Size(max = 1024)
    private String fixturesImageUrl;

    @Size(max = 255)
    private String registrationEmail;

    @Size(max = 1024)
    private String externalResultsUrl;

    @Size(max = 128)
    private String externalResultsLabel;

    @Size(max = 1024)
    private String legacyPageUrl;

    @Size(max = 255)
    private String winnerName;

    @Size(max = 255)
    private String runnerUpName;

    @Size(max = 255)
    private String thirdPlaceName;

    @NotNull
    private Instant createdAt;

    @NotNull
    private Instant updatedAt;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortLabel() {
        return shortLabel;
    }

    public void setShortLabel(String shortLabel) {
        this.shortLabel = shortLabel;
    }

    public Integer getEditionYear() {
        return editionYear;
    }

    public void setEditionYear(Integer editionYear) {
        this.editionYear = editionYear;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Boolean getShowInHeader() {
        return showInHeader;
    }

    public void setShowInHeader(Boolean showInHeader) {
        this.showInHeader = showInHeader;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Boolean getIsPublished() {
        return isPublished;
    }

    public void setIsPublished(Boolean isPublished) {
        this.isPublished = isPublished;
    }

    public String getWelcomeText() {
        return welcomeText;
    }

    public void setWelcomeText(String welcomeText) {
        this.welcomeText = welcomeText;
    }

    public String getAboutText() {
        return aboutText;
    }

    public void setAboutText(String aboutText) {
        this.aboutText = aboutText;
    }

    public String getDetailsText() {
        return detailsText;
    }

    public void setDetailsText(String detailsText) {
        this.detailsText = detailsText;
    }

    public String getAgeGroupLabel() {
        return ageGroupLabel;
    }

    public void setAgeGroupLabel(String ageGroupLabel) {
        this.ageGroupLabel = ageGroupLabel;
    }

    public String getChiefGuestName() {
        return chiefGuestName;
    }

    public void setChiefGuestName(String chiefGuestName) {
        this.chiefGuestName = chiefGuestName;
    }

    public String getChiefGuestImageUrl() {
        return chiefGuestImageUrl;
    }

    public void setChiefGuestImageUrl(String chiefGuestImageUrl) {
        this.chiefGuestImageUrl = chiefGuestImageUrl;
    }

    public String getHeroImageUrl() {
        return heroImageUrl;
    }

    public void setHeroImageUrl(String heroImageUrl) {
        this.heroImageUrl = heroImageUrl;
    }

    public String getFixturesImageUrl() {
        return fixturesImageUrl;
    }

    public void setFixturesImageUrl(String fixturesImageUrl) {
        this.fixturesImageUrl = fixturesImageUrl;
    }

    public String getRegistrationEmail() {
        return registrationEmail;
    }

    public void setRegistrationEmail(String registrationEmail) {
        this.registrationEmail = registrationEmail;
    }

    public String getExternalResultsUrl() {
        return externalResultsUrl;
    }

    public void setExternalResultsUrl(String externalResultsUrl) {
        this.externalResultsUrl = externalResultsUrl;
    }

    public String getExternalResultsLabel() {
        return externalResultsLabel;
    }

    public void setExternalResultsLabel(String externalResultsLabel) {
        this.externalResultsLabel = externalResultsLabel;
    }

    public String getLegacyPageUrl() {
        return legacyPageUrl;
    }

    public void setLegacyPageUrl(String legacyPageUrl) {
        this.legacyPageUrl = legacyPageUrl;
    }

    public String getWinnerName() {
        return winnerName;
    }

    public void setWinnerName(String winnerName) {
        this.winnerName = winnerName;
    }

    public String getRunnerUpName() {
        return runnerUpName;
    }

    public void setRunnerUpName(String runnerUpName) {
        this.runnerUpName = runnerUpName;
    }

    public String getThirdPlaceName() {
        return thirdPlaceName;
    }

    public void setThirdPlaceName(String thirdPlaceName) {
        this.thirdPlaceName = thirdPlaceName;
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
        if (this == o) return true;
        if (!(o instanceof TenantTournamentDTO that)) return false;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(tenantId, that.tenantId) &&
            Objects.equals(title, that.title) &&
            Objects.equals(editionYear, that.editionYear) &&
            Objects.equals(slug, that.slug) &&
            Objects.equals(showInHeader, that.showInHeader) &&
            Objects.equals(status, that.status) &&
            Objects.equals(startDate, that.startDate) &&
            Objects.equals(endDate, that.endDate) &&
            Objects.equals(isPublished, that.isPublished) &&
            Objects.equals(welcomeText, that.welcomeText) &&
            Objects.equals(aboutText, that.aboutText) &&
            Objects.equals(detailsText, that.detailsText) &&
            Objects.equals(ageGroupLabel, that.ageGroupLabel) &&
            Objects.equals(chiefGuestName, that.chiefGuestName) &&
            Objects.equals(chiefGuestImageUrl, that.chiefGuestImageUrl) &&
            Objects.equals(heroImageUrl, that.heroImageUrl) &&
            Objects.equals(fixturesImageUrl, that.fixturesImageUrl) &&
            Objects.equals(registrationEmail, that.registrationEmail) &&
            Objects.equals(externalResultsUrl, that.externalResultsUrl) &&
            Objects.equals(externalResultsLabel, that.externalResultsLabel) &&
            Objects.equals(legacyPageUrl, that.legacyPageUrl) &&
            Objects.equals(winnerName, that.winnerName) &&
            Objects.equals(runnerUpName, that.runnerUpName) &&
            Objects.equals(thirdPlaceName, that.thirdPlaceName) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(updatedAt, that.updatedAt)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            tenantId,
            title,
            editionYear,
            slug,
            showInHeader,
            status,
            startDate,
            endDate,
            isPublished,
            welcomeText,
            aboutText,
            detailsText,
            ageGroupLabel,
            chiefGuestName,
            chiefGuestImageUrl,
            heroImageUrl,
            fixturesImageUrl,
            registrationEmail,
            externalResultsUrl,
            externalResultsLabel,
            legacyPageUrl,
            winnerName,
            runnerUpName,
            thirdPlaceName,
            createdAt,
            updatedAt
        );
    }
}
