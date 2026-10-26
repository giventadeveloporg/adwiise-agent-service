package com.adwiiseagent.service.dto;

import java.io.Serializable;

public class TeamMemberSportDTO implements Serializable {

    private Long id;
    private String tenantId;
    private Long teamMemberId;
    private Long sportsCategoryId;
    private String code;
    private String label;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }
    public Long getTeamMemberId() { return teamMemberId; }
    public void setTeamMemberId(Long teamMemberId) { this.teamMemberId = teamMemberId; }
    public Long getSportsCategoryId() { return sportsCategoryId; }
    public void setSportsCategoryId(Long sportsCategoryId) { this.sportsCategoryId = sportsCategoryId; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }
}
