package com.adwiiseagent.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.time.Instant;

public class TeamGroupMembershipDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String tenantId;

    @NotNull
    private Long teamGroupId;

    @NotNull
    private Long teamMemberId;

    private Integer jerseyNumber;

    @Size(max = 128)
    private String position;

    @NotNull
    private Boolean isActive;

    private Instant createdAt;

    private Instant updatedAt;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }

    public Long getTeamGroupId() { return teamGroupId; }
    public void setTeamGroupId(Long teamGroupId) { this.teamGroupId = teamGroupId; }

    public Long getTeamMemberId() { return teamMemberId; }
    public void setTeamMemberId(Long teamMemberId) { this.teamMemberId = teamMemberId; }

    public Integer getJerseyNumber() { return jerseyNumber; }
    public void setJerseyNumber(Integer jerseyNumber) { this.jerseyNumber = jerseyNumber; }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof TeamGroupMembershipDTO other)) return false;
        return id != null && id.equals(other.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }
}
