package com.adwiiseagent.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.adwiiseagent.domain.TeamMember} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TeamMemberDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String tenantId;

    @NotNull
    private Long teamGroupId;

    private Long userProfileId;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String title;

    private String designation;

    @Size(max = 2048)
    private String bio;

    private String email;

    private String profileImageUrl;

    @Size(max = 4096)
    private String expertise;

    private String imageBackground;

    private String imageStyle;

    private String department;

    private LocalDate joinDate;

    private Boolean isActive;

    private String linkedinUrl;

    private String twitterUrl;

    private Integer priorityOrder;

    private String websiteUrl;

    private Integer jerseyNumber;

    @Size(max = 128)
    private String position;

    @Size(max = 128)
    private String lineupSubtitle;

    @Size(max = 128)
    private String instrument;

    @Size(max = 128)
    private String vocalRole;

    @Size(max = 100)
    private String nationality;

    private LocalDate dateOfBirth;

    @Size(max = 16)
    private String preferredFoot;

    private Integer heightCm;

    @Size(max = 500)
    private String instagramUrl;

    @Size(max = 100)
    private String preferredName;

    @Size(max = 255)
    private String spokenLanguages;

    private Integer weightKg;

    @Size(max = 32)
    private String bodyBuild;

    @Size(max = 255)
    private String clubName;

    @Size(max = 128)
    private String leagueDivision;

    @Size(max = 255)
    private String secondaryPositions;

    @Size(max = 32)
    private String seasonLabel;

    private Integer seasonAppearances;

    private Integer seasonGoals;

    private Integer seasonAssists;

    private Integer seasonMinutes;

    private Integer seasonWins;

    private Integer seasonDraws;

    private Integer seasonLosses;

    private Double passAccuracyPct;

    private Double shotAccuracyPct;

    private Double topSpeedKmh;

    private Integer careerAppearances;

    private Integer careerGoals;

    private Integer careerAssists;

    private Double gpa;

    @Size(max = 64)
    private String academicScore;

    @Size(max = 255)
    private String coachName;

    @Size(max = 255)
    private String coachEmail;

    @Size(max = 40)
    private String coachPhone;

    @Size(max = 8000)
    private String recentMatches;

    private String sportProfiles;

    /** Selected sports_category ids. Omitted (null) leaves the current links unchanged. */
    private java.util.List<Long> sportCategoryIds;

    /** Labels for the selected sports, in catalog sort order. Read-only on save. */
    private java.util.List<String> sportLabels;

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

    public Long getTeamGroupId() {
        return teamGroupId;
    }

    public void setTeamGroupId(Long teamGroupId) {
        this.teamGroupId = teamGroupId;
    }

    public Long getUserProfileId() {
        return userProfileId;
    }

    public void setUserProfileId(Long userProfileId) {
        this.userProfileId = userProfileId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getExpertise() {
        return expertise;
    }

    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }

    public String getImageBackground() {
        return imageBackground;
    }

    public void setImageBackground(String imageBackground) {
        this.imageBackground = imageBackground;
    }

    public String getImageStyle() {
        return imageStyle;
    }

    public void setImageStyle(String imageStyle) {
        this.imageStyle = imageStyle;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public LocalDate getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(LocalDate joinDate) {
        this.joinDate = joinDate;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getLinkedinUrl() {
        return linkedinUrl;
    }

    public void setLinkedinUrl(String linkedinUrl) {
        this.linkedinUrl = linkedinUrl;
    }

    public String getTwitterUrl() {
        return twitterUrl;
    }

    public void setTwitterUrl(String twitterUrl) {
        this.twitterUrl = twitterUrl;
    }

    public Integer getPriorityOrder() {
        return priorityOrder;
    }

    public void setPriorityOrder(Integer priorityOrder) {
        this.priorityOrder = priorityOrder;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public Integer getJerseyNumber() {
        return jerseyNumber;
    }

    public void setJerseyNumber(Integer jerseyNumber) {
        this.jerseyNumber = jerseyNumber;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getLineupSubtitle() {
        return lineupSubtitle;
    }

    public void setLineupSubtitle(String lineupSubtitle) {
        this.lineupSubtitle = lineupSubtitle;
    }

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public String getVocalRole() {
        return vocalRole;
    }

    public void setVocalRole(String vocalRole) {
        this.vocalRole = vocalRole;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPreferredFoot() {
        return preferredFoot;
    }

    public void setPreferredFoot(String preferredFoot) {
        this.preferredFoot = preferredFoot;
    }

    public Integer getHeightCm() {
        return heightCm;
    }

    public void setHeightCm(Integer heightCm) {
        this.heightCm = heightCm;
    }

    public String getInstagramUrl() {
        return instagramUrl;
    }

    public void setInstagramUrl(String instagramUrl) {
        this.instagramUrl = instagramUrl;
    }

    public String getPreferredName() {
        return preferredName;
    }

    public void setPreferredName(String preferredName) {
        this.preferredName = preferredName;
    }

    public String getSpokenLanguages() {
        return spokenLanguages;
    }

    public void setSpokenLanguages(String spokenLanguages) {
        this.spokenLanguages = spokenLanguages;
    }

    public Integer getWeightKg() {
        return weightKg;
    }

    public void setWeightKg(Integer weightKg) {
        this.weightKg = weightKg;
    }

    public String getBodyBuild() {
        return bodyBuild;
    }

    public void setBodyBuild(String bodyBuild) {
        this.bodyBuild = bodyBuild;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getLeagueDivision() {
        return leagueDivision;
    }

    public void setLeagueDivision(String leagueDivision) {
        this.leagueDivision = leagueDivision;
    }

    public String getSecondaryPositions() {
        return secondaryPositions;
    }

    public void setSecondaryPositions(String secondaryPositions) {
        this.secondaryPositions = secondaryPositions;
    }

    public String getSeasonLabel() {
        return seasonLabel;
    }

    public void setSeasonLabel(String seasonLabel) {
        this.seasonLabel = seasonLabel;
    }

    public Integer getSeasonAppearances() {
        return seasonAppearances;
    }

    public void setSeasonAppearances(Integer seasonAppearances) {
        this.seasonAppearances = seasonAppearances;
    }

    public Integer getSeasonGoals() {
        return seasonGoals;
    }

    public void setSeasonGoals(Integer seasonGoals) {
        this.seasonGoals = seasonGoals;
    }

    public Integer getSeasonAssists() {
        return seasonAssists;
    }

    public void setSeasonAssists(Integer seasonAssists) {
        this.seasonAssists = seasonAssists;
    }

    public Integer getSeasonMinutes() {
        return seasonMinutes;
    }

    public void setSeasonMinutes(Integer seasonMinutes) {
        this.seasonMinutes = seasonMinutes;
    }

    public Integer getSeasonWins() {
        return seasonWins;
    }

    public void setSeasonWins(Integer seasonWins) {
        this.seasonWins = seasonWins;
    }

    public Integer getSeasonDraws() {
        return seasonDraws;
    }

    public void setSeasonDraws(Integer seasonDraws) {
        this.seasonDraws = seasonDraws;
    }

    public Integer getSeasonLosses() {
        return seasonLosses;
    }

    public void setSeasonLosses(Integer seasonLosses) {
        this.seasonLosses = seasonLosses;
    }

    public Double getPassAccuracyPct() {
        return passAccuracyPct;
    }

    public void setPassAccuracyPct(Double passAccuracyPct) {
        this.passAccuracyPct = passAccuracyPct;
    }

    public Double getShotAccuracyPct() {
        return shotAccuracyPct;
    }

    public void setShotAccuracyPct(Double shotAccuracyPct) {
        this.shotAccuracyPct = shotAccuracyPct;
    }

    public Double getTopSpeedKmh() {
        return topSpeedKmh;
    }

    public void setTopSpeedKmh(Double topSpeedKmh) {
        this.topSpeedKmh = topSpeedKmh;
    }

    public Integer getCareerAppearances() {
        return careerAppearances;
    }

    public void setCareerAppearances(Integer careerAppearances) {
        this.careerAppearances = careerAppearances;
    }

    public Integer getCareerGoals() {
        return careerGoals;
    }

    public void setCareerGoals(Integer careerGoals) {
        this.careerGoals = careerGoals;
    }

    public Integer getCareerAssists() {
        return careerAssists;
    }

    public void setCareerAssists(Integer careerAssists) {
        this.careerAssists = careerAssists;
    }

    public Double getGpa() {
        return gpa;
    }

    public void setGpa(Double gpa) {
        this.gpa = gpa;
    }

    public String getAcademicScore() {
        return academicScore;
    }

    public void setAcademicScore(String academicScore) {
        this.academicScore = academicScore;
    }

    public String getCoachName() {
        return coachName;
    }

    public void setCoachName(String coachName) {
        this.coachName = coachName;
    }

    public String getCoachEmail() {
        return coachEmail;
    }

    public void setCoachEmail(String coachEmail) {
        this.coachEmail = coachEmail;
    }

    public String getCoachPhone() {
        return coachPhone;
    }

    public void setCoachPhone(String coachPhone) {
        this.coachPhone = coachPhone;
    }

    public String getRecentMatches() {
        return recentMatches;
    }

    public void setRecentMatches(String recentMatches) {
        this.recentMatches = recentMatches;
    }

    public String getSportProfiles() {
        return sportProfiles;
    }

    public void setSportProfiles(String sportProfiles) {
        this.sportProfiles = sportProfiles;
    }

    public java.util.List<Long> getSportCategoryIds() {
        return sportCategoryIds;
    }

    public void setSportCategoryIds(java.util.List<Long> sportCategoryIds) {
        this.sportCategoryIds = sportCategoryIds;
    }

    public java.util.List<String> getSportLabels() {
        return sportLabels;
    }

    public void setSportLabels(java.util.List<String> sportLabels) {
        this.sportLabels = sportLabels;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TeamMemberDTO)) {
            return false;
        }

        TeamMemberDTO teamMemberDTO = (TeamMemberDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, teamMemberDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TeamMemberDTO{" +
            "id=" + getId() +
            ", tenantId='" + getTenantId() + "'" +
            ", teamGroupId=" + getTeamGroupId() +
            ", userProfileId=" + getUserProfileId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", title='" + getTitle() + "'" +
            ", designation='" + getDesignation() + "'" +
            ", bio='" + getBio() + "'" +
            ", email='" + getEmail() + "'" +
            ", profileImageUrl='" + getProfileImageUrl() + "'" +
            ", expertise='" + getExpertise() + "'" +
            ", imageBackground='" + getImageBackground() + "'" +
            ", imageStyle='" + getImageStyle() + "'" +
            ", department='" + getDepartment() + "'" +
            ", joinDate='" + getJoinDate() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", linkedinUrl='" + getLinkedinUrl() + "'" +
            ", twitterUrl='" + getTwitterUrl() + "'" +
            ", priorityOrder=" + getPriorityOrder() +
            ", websiteUrl='" + getWebsiteUrl() + "'" +
            ", jerseyNumber=" + getJerseyNumber() +
            ", position='" + getPosition() + "'" +
            ", lineupSubtitle='" + getLineupSubtitle() + "'" +
            ", instrument='" + getInstrument() + "'" +
            ", vocalRole='" + getVocalRole() + "'" +
            ", nationality='" + getNationality() + "'" +
            ", dateOfBirth='" + getDateOfBirth() + "'" +
            ", preferredFoot='" + getPreferredFoot() + "'" +
            ", heightCm=" + getHeightCm() +
            ", instagramUrl='" + getInstagramUrl() + "'" +
            ", preferredName='" + getPreferredName() + "'" +
            ", spokenLanguages='" + getSpokenLanguages() + "'" +
            ", weightKg=" + getWeightKg() +
            ", bodyBuild='" + getBodyBuild() + "'" +
            ", clubName='" + getClubName() + "'" +
            ", leagueDivision='" + getLeagueDivision() + "'" +
            ", secondaryPositions='" + getSecondaryPositions() + "'" +
            ", seasonLabel='" + getSeasonLabel() + "'" +
            ", seasonAppearances=" + getSeasonAppearances() +
            ", seasonGoals=" + getSeasonGoals() +
            ", seasonAssists=" + getSeasonAssists() +
            ", seasonMinutes=" + getSeasonMinutes() +
            ", seasonWins=" + getSeasonWins() +
            ", seasonDraws=" + getSeasonDraws() +
            ", seasonLosses=" + getSeasonLosses() +
            ", passAccuracyPct=" + getPassAccuracyPct() +
            ", shotAccuracyPct=" + getShotAccuracyPct() +
            ", topSpeedKmh=" + getTopSpeedKmh() +
            ", careerAppearances=" + getCareerAppearances() +
            ", careerGoals=" + getCareerGoals() +
            ", careerAssists=" + getCareerAssists() +
            ", gpa=" + getGpa() +
            ", academicScore='" + getAcademicScore() + "'" +
            ", coachName='" + getCoachName() + "'" +
            ", coachEmail='" + getCoachEmail() + "'" +
            ", coachPhone='" + getCoachPhone() + "'" +
            "}";
    }
}
