package com.adwiiseagent.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TeamMember.
 */
@Entity
@Table(name = "team_members")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TeamMember implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "teamMembersSeq")
    @SequenceGenerator(name = "teamMembersSeq", sequenceName = "public.team_members_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "tenant_id", length = 255, nullable = false)
    private String tenantId;

    @NotNull
    @Column(name = "team_group_id", nullable = false)
    private Long teamGroupId;

    @Column(name = "user_profile_id")
    private Long userProfileId;

    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "designation")
    private String designation;

    @Size(max = 2048)
    @Column(name = "bio", length = 2048)
    private String bio;

    @Column(name = "email")
    private String email;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @Size(max = 500)
    @Column(name = "expertise", length = 500)
    private String expertise;

    @Column(name = "image_background")
    private String imageBackground;

    @Column(name = "image_style")
    private String imageStyle;

    @Column(name = "department")
    private String department;

    @Column(name = "join_date")
    private LocalDate joinDate;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "linkedin_url")
    private String linkedinUrl;

    @Column(name = "twitter_url")
    private String twitterUrl;

    @Column(name = "priority_order")
    private Integer priorityOrder;

    @Column(name = "website_url")
    private String websiteUrl;

    @Column(name = "jersey_number")
    private Integer jerseyNumber;

    @Size(max = 128)
    @Column(name = "position", length = 128)
    private String position;

    @Size(max = 128)
    @Column(name = "lineup_subtitle", length = 128)
    private String lineupSubtitle;

    @Size(max = 128)
    @Column(name = "instrument", length = 128)
    private String instrument;

    @Size(max = 128)
    @Column(name = "vocal_role", length = 128)
    private String vocalRole;

    @Size(max = 100)
    @Column(name = "nationality", length = 100)
    private String nationality;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Size(max = 16)
    @Column(name = "preferred_foot", length = 16)
    private String preferredFoot;

    @Column(name = "height_cm")
    private Integer heightCm;

    @Size(max = 500)
    @Column(name = "instagram_url", length = 500)
    private String instagramUrl;

    @Size(max = 100)
    @Column(name = "preferred_name", length = 100)
    private String preferredName;

    @Size(max = 255)
    @Column(name = "spoken_languages", length = 255)
    private String spokenLanguages;

    @Column(name = "weight_kg")
    private Integer weightKg;

    @Size(max = 32)
    @Column(name = "body_build", length = 32)
    private String bodyBuild;

    @Size(max = 255)
    @Column(name = "club_name", length = 255)
    private String clubName;

    @Size(max = 128)
    @Column(name = "league_division", length = 128)
    private String leagueDivision;

    @Size(max = 255)
    @Column(name = "secondary_positions", length = 255)
    private String secondaryPositions;

    @Size(max = 32)
    @Column(name = "season_label", length = 32)
    private String seasonLabel;

    @Column(name = "season_appearances")
    private Integer seasonAppearances;

    @Column(name = "season_goals")
    private Integer seasonGoals;

    @Column(name = "season_assists")
    private Integer seasonAssists;

    @Column(name = "season_minutes")
    private Integer seasonMinutes;

    @Column(name = "season_wins")
    private Integer seasonWins;

    @Column(name = "season_draws")
    private Integer seasonDraws;

    @Column(name = "season_losses")
    private Integer seasonLosses;

    @Column(name = "pass_accuracy_pct")
    private Double passAccuracyPct;

    @Column(name = "shot_accuracy_pct")
    private Double shotAccuracyPct;

    @Column(name = "top_speed_kmh")
    private Double topSpeedKmh;

    @Column(name = "career_appearances")
    private Integer careerAppearances;

    @Column(name = "career_goals")
    private Integer careerGoals;

    @Column(name = "career_assists")
    private Integer careerAssists;

    @Column(name = "gpa")
    private Double gpa;

    @Size(max = 64)
    @Column(name = "academic_score", length = 64)
    private String academicScore;

    @Size(max = 255)
    @Column(name = "coach_name", length = 255)
    private String coachName;

    @Size(max = 255)
    @Column(name = "coach_email", length = 255)
    private String coachEmail;

    @Size(max = 40)
    @Column(name = "coach_phone", length = 40)
    private String coachPhone;

    @Size(max = 8000)
    @Column(name = "recent_matches", length = 8000)
    private String recentMatches;

    @Column(name = "sport_profiles", columnDefinition = "text")
    private String sportProfiles;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TeamMember id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTenantId() {
        return this.tenantId;
    }

    public TeamMember tenantId(String tenantId) {
        this.setTenantId(tenantId);
        return this;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public Long getTeamGroupId() {
        return this.teamGroupId;
    }

    public TeamMember teamGroupId(Long teamGroupId) {
        this.setTeamGroupId(teamGroupId);
        return this;
    }

    public void setTeamGroupId(Long teamGroupId) {
        this.teamGroupId = teamGroupId;
    }

    public Long getUserProfileId() {
        return this.userProfileId;
    }

    public TeamMember userProfileId(Long userProfileId) {
        this.setUserProfileId(userProfileId);
        return this;
    }

    public void setUserProfileId(Long userProfileId) {
        this.userProfileId = userProfileId;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public TeamMember firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public TeamMember lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTitle() {
        return this.title;
    }

    public TeamMember title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesignation() {
        return this.designation;
    }

    public TeamMember designation(String designation) {
        this.setDesignation(designation);
        return this;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getBio() {
        return this.bio;
    }

    public TeamMember bio(String bio) {
        this.setBio(bio);
        return this;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getEmail() {
        return this.email;
    }

    public TeamMember email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfileImageUrl() {
        return this.profileImageUrl;
    }

    public TeamMember profileImageUrl(String profileImageUrl) {
        this.setProfileImageUrl(profileImageUrl);
        return this;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getExpertise() {
        return this.expertise;
    }

    public TeamMember expertise(String expertise) {
        this.setExpertise(expertise);
        return this;
    }

    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }

    public String getImageBackground() {
        return this.imageBackground;
    }

    public TeamMember imageBackground(String imageBackground) {
        this.setImageBackground(imageBackground);
        return this;
    }

    public void setImageBackground(String imageBackground) {
        this.imageBackground = imageBackground;
    }

    public String getImageStyle() {
        return this.imageStyle;
    }

    public TeamMember imageStyle(String imageStyle) {
        this.setImageStyle(imageStyle);
        return this;
    }

    public void setImageStyle(String imageStyle) {
        this.imageStyle = imageStyle;
    }

    public String getDepartment() {
        return this.department;
    }

    public TeamMember department(String department) {
        this.setDepartment(department);
        return this;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public LocalDate getJoinDate() {
        return this.joinDate;
    }

    public TeamMember joinDate(LocalDate joinDate) {
        this.setJoinDate(joinDate);
        return this;
    }

    public void setJoinDate(LocalDate joinDate) {
        this.joinDate = joinDate;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public TeamMember isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getLinkedinUrl() {
        return this.linkedinUrl;
    }

    public TeamMember linkedinUrl(String linkedinUrl) {
        this.setLinkedinUrl(linkedinUrl);
        return this;
    }

    public void setLinkedinUrl(String linkedinUrl) {
        this.linkedinUrl = linkedinUrl;
    }

    public String getTwitterUrl() {
        return this.twitterUrl;
    }

    public TeamMember twitterUrl(String twitterUrl) {
        this.setTwitterUrl(twitterUrl);
        return this;
    }

    public void setTwitterUrl(String twitterUrl) {
        this.twitterUrl = twitterUrl;
    }

    public Integer getPriorityOrder() {
        return this.priorityOrder;
    }

    public TeamMember priorityOrder(Integer priorityOrder) {
        this.setPriorityOrder(priorityOrder);
        return this;
    }

    public void setPriorityOrder(Integer priorityOrder) {
        this.priorityOrder = priorityOrder;
    }

    public String getWebsiteUrl() {
        return this.websiteUrl;
    }

    public TeamMember websiteUrl(String websiteUrl) {
        this.setWebsiteUrl(websiteUrl);
        return this;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public Integer getJerseyNumber() {
        return this.jerseyNumber;
    }

    public TeamMember jerseyNumber(Integer jerseyNumber) {
        this.setJerseyNumber(jerseyNumber);
        return this;
    }

    public void setJerseyNumber(Integer jerseyNumber) {
        this.jerseyNumber = jerseyNumber;
    }

    public String getPosition() {
        return this.position;
    }

    public TeamMember position(String position) {
        this.setPosition(position);
        return this;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getLineupSubtitle() {
        return this.lineupSubtitle;
    }

    public TeamMember lineupSubtitle(String lineupSubtitle) {
        this.setLineupSubtitle(lineupSubtitle);
        return this;
    }

    public void setLineupSubtitle(String lineupSubtitle) {
        this.lineupSubtitle = lineupSubtitle;
    }

    public String getInstrument() {
        return this.instrument;
    }

    public TeamMember instrument(String instrument) {
        this.setInstrument(instrument);
        return this;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public String getVocalRole() {
        return this.vocalRole;
    }

    public TeamMember vocalRole(String vocalRole) {
        this.setVocalRole(vocalRole);
        return this;
    }

    public void setVocalRole(String vocalRole) {
        this.vocalRole = vocalRole;
    }

    public String getNationality() {
        return this.nationality;
    }

    public TeamMember nationality(String nationality) {
        this.setNationality(nationality);
        return this;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public LocalDate getDateOfBirth() {
        return this.dateOfBirth;
    }

    public TeamMember dateOfBirth(LocalDate dateOfBirth) {
        this.setDateOfBirth(dateOfBirth);
        return this;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPreferredFoot() {
        return this.preferredFoot;
    }

    public TeamMember preferredFoot(String preferredFoot) {
        this.setPreferredFoot(preferredFoot);
        return this;
    }

    public void setPreferredFoot(String preferredFoot) {
        this.preferredFoot = preferredFoot;
    }

    public Integer getHeightCm() {
        return this.heightCm;
    }

    public TeamMember heightCm(Integer heightCm) {
        this.setHeightCm(heightCm);
        return this;
    }

    public void setHeightCm(Integer heightCm) {
        this.heightCm = heightCm;
    }

    public String getInstagramUrl() {
        return this.instagramUrl;
    }

    public TeamMember instagramUrl(String instagramUrl) {
        this.setInstagramUrl(instagramUrl);
        return this;
    }

    public void setInstagramUrl(String instagramUrl) {
        this.instagramUrl = instagramUrl;
    }

    public String getPreferredName() {
        return this.preferredName;
    }

    public TeamMember preferredName(String preferredName) {
        this.setPreferredName(preferredName);
        return this;
    }

    public void setPreferredName(String preferredName) {
        this.preferredName = preferredName;
    }

    public String getSpokenLanguages() {
        return this.spokenLanguages;
    }

    public TeamMember spokenLanguages(String spokenLanguages) {
        this.setSpokenLanguages(spokenLanguages);
        return this;
    }

    public void setSpokenLanguages(String spokenLanguages) {
        this.spokenLanguages = spokenLanguages;
    }

    public Integer getWeightKg() {
        return this.weightKg;
    }

    public TeamMember weightKg(Integer weightKg) {
        this.setWeightKg(weightKg);
        return this;
    }

    public void setWeightKg(Integer weightKg) {
        this.weightKg = weightKg;
    }

    public String getBodyBuild() {
        return this.bodyBuild;
    }

    public TeamMember bodyBuild(String bodyBuild) {
        this.setBodyBuild(bodyBuild);
        return this;
    }

    public void setBodyBuild(String bodyBuild) {
        this.bodyBuild = bodyBuild;
    }

    public String getClubName() {
        return this.clubName;
    }

    public TeamMember clubName(String clubName) {
        this.setClubName(clubName);
        return this;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getLeagueDivision() {
        return this.leagueDivision;
    }

    public TeamMember leagueDivision(String leagueDivision) {
        this.setLeagueDivision(leagueDivision);
        return this;
    }

    public void setLeagueDivision(String leagueDivision) {
        this.leagueDivision = leagueDivision;
    }

    public String getSecondaryPositions() {
        return this.secondaryPositions;
    }

    public TeamMember secondaryPositions(String secondaryPositions) {
        this.setSecondaryPositions(secondaryPositions);
        return this;
    }

    public void setSecondaryPositions(String secondaryPositions) {
        this.secondaryPositions = secondaryPositions;
    }

    public String getSeasonLabel() {
        return this.seasonLabel;
    }

    public TeamMember seasonLabel(String seasonLabel) {
        this.setSeasonLabel(seasonLabel);
        return this;
    }

    public void setSeasonLabel(String seasonLabel) {
        this.seasonLabel = seasonLabel;
    }

    public Integer getSeasonAppearances() {
        return this.seasonAppearances;
    }

    public TeamMember seasonAppearances(Integer seasonAppearances) {
        this.setSeasonAppearances(seasonAppearances);
        return this;
    }

    public void setSeasonAppearances(Integer seasonAppearances) {
        this.seasonAppearances = seasonAppearances;
    }

    public Integer getSeasonGoals() {
        return this.seasonGoals;
    }

    public TeamMember seasonGoals(Integer seasonGoals) {
        this.setSeasonGoals(seasonGoals);
        return this;
    }

    public void setSeasonGoals(Integer seasonGoals) {
        this.seasonGoals = seasonGoals;
    }

    public Integer getSeasonAssists() {
        return this.seasonAssists;
    }

    public TeamMember seasonAssists(Integer seasonAssists) {
        this.setSeasonAssists(seasonAssists);
        return this;
    }

    public void setSeasonAssists(Integer seasonAssists) {
        this.seasonAssists = seasonAssists;
    }

    public Integer getSeasonMinutes() {
        return this.seasonMinutes;
    }

    public TeamMember seasonMinutes(Integer seasonMinutes) {
        this.setSeasonMinutes(seasonMinutes);
        return this;
    }

    public void setSeasonMinutes(Integer seasonMinutes) {
        this.seasonMinutes = seasonMinutes;
    }

    public Integer getSeasonWins() {
        return this.seasonWins;
    }

    public TeamMember seasonWins(Integer seasonWins) {
        this.setSeasonWins(seasonWins);
        return this;
    }

    public void setSeasonWins(Integer seasonWins) {
        this.seasonWins = seasonWins;
    }

    public Integer getSeasonDraws() {
        return this.seasonDraws;
    }

    public TeamMember seasonDraws(Integer seasonDraws) {
        this.setSeasonDraws(seasonDraws);
        return this;
    }

    public void setSeasonDraws(Integer seasonDraws) {
        this.seasonDraws = seasonDraws;
    }

    public Integer getSeasonLosses() {
        return this.seasonLosses;
    }

    public TeamMember seasonLosses(Integer seasonLosses) {
        this.setSeasonLosses(seasonLosses);
        return this;
    }

    public void setSeasonLosses(Integer seasonLosses) {
        this.seasonLosses = seasonLosses;
    }

    public Double getPassAccuracyPct() {
        return this.passAccuracyPct;
    }

    public TeamMember passAccuracyPct(Double passAccuracyPct) {
        this.setPassAccuracyPct(passAccuracyPct);
        return this;
    }

    public void setPassAccuracyPct(Double passAccuracyPct) {
        this.passAccuracyPct = passAccuracyPct;
    }

    public Double getShotAccuracyPct() {
        return this.shotAccuracyPct;
    }

    public TeamMember shotAccuracyPct(Double shotAccuracyPct) {
        this.setShotAccuracyPct(shotAccuracyPct);
        return this;
    }

    public void setShotAccuracyPct(Double shotAccuracyPct) {
        this.shotAccuracyPct = shotAccuracyPct;
    }

    public Double getTopSpeedKmh() {
        return this.topSpeedKmh;
    }

    public TeamMember topSpeedKmh(Double topSpeedKmh) {
        this.setTopSpeedKmh(topSpeedKmh);
        return this;
    }

    public void setTopSpeedKmh(Double topSpeedKmh) {
        this.topSpeedKmh = topSpeedKmh;
    }

    public Integer getCareerAppearances() {
        return this.careerAppearances;
    }

    public TeamMember careerAppearances(Integer careerAppearances) {
        this.setCareerAppearances(careerAppearances);
        return this;
    }

    public void setCareerAppearances(Integer careerAppearances) {
        this.careerAppearances = careerAppearances;
    }

    public Integer getCareerGoals() {
        return this.careerGoals;
    }

    public TeamMember careerGoals(Integer careerGoals) {
        this.setCareerGoals(careerGoals);
        return this;
    }

    public void setCareerGoals(Integer careerGoals) {
        this.careerGoals = careerGoals;
    }

    public Integer getCareerAssists() {
        return this.careerAssists;
    }

    public TeamMember careerAssists(Integer careerAssists) {
        this.setCareerAssists(careerAssists);
        return this;
    }

    public void setCareerAssists(Integer careerAssists) {
        this.careerAssists = careerAssists;
    }

    public Double getGpa() {
        return this.gpa;
    }

    public TeamMember gpa(Double gpa) {
        this.setGpa(gpa);
        return this;
    }

    public void setGpa(Double gpa) {
        this.gpa = gpa;
    }

    public String getAcademicScore() {
        return this.academicScore;
    }

    public TeamMember academicScore(String academicScore) {
        this.setAcademicScore(academicScore);
        return this;
    }

    public void setAcademicScore(String academicScore) {
        this.academicScore = academicScore;
    }

    public String getCoachName() {
        return this.coachName;
    }

    public TeamMember coachName(String coachName) {
        this.setCoachName(coachName);
        return this;
    }

    public void setCoachName(String coachName) {
        this.coachName = coachName;
    }

    public String getCoachEmail() {
        return this.coachEmail;
    }

    public TeamMember coachEmail(String coachEmail) {
        this.setCoachEmail(coachEmail);
        return this;
    }

    public void setCoachEmail(String coachEmail) {
        this.coachEmail = coachEmail;
    }

    public String getCoachPhone() {
        return this.coachPhone;
    }

    public TeamMember coachPhone(String coachPhone) {
        this.setCoachPhone(coachPhone);
        return this;
    }

    public void setCoachPhone(String coachPhone) {
        this.coachPhone = coachPhone;
    }

    public String getRecentMatches() {
        return this.recentMatches;
    }

    public TeamMember recentMatches(String recentMatches) {
        this.setRecentMatches(recentMatches);
        return this;
    }

    public void setRecentMatches(String recentMatches) {
        this.recentMatches = recentMatches;
    }

    public String getSportProfiles() {
        return this.sportProfiles;
    }

    public TeamMember sportProfiles(String sportProfiles) {
        this.setSportProfiles(sportProfiles);
        return this;
    }

    public void setSportProfiles(String sportProfiles) {
        this.sportProfiles = sportProfiles;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TeamMember)) {
            return false;
        }
        return getId() != null && getId().equals(((TeamMember) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TeamMember{" +
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
