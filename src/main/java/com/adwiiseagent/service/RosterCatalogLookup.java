package com.adwiiseagent.service;

import com.adwiiseagent.domain.AchievementCategory;
import com.adwiiseagent.domain.SportsCategory;
import com.adwiiseagent.domain.TeamMember;
import com.adwiiseagent.domain.TeamMemberSport;
import com.adwiiseagent.errors.BadRequestAlertException;
import com.adwiiseagent.repository.AchievementCategoryRepository;
import com.adwiiseagent.repository.SportsCategoryRepository;
import com.adwiiseagent.repository.TeamMemberRepository;
import com.adwiiseagent.repository.TeamMemberSportRepository;
import com.adwiiseagent.repository.TeamTypeRepository;
import com.adwiiseagent.service.dto.TeamMemberDTO;
import com.adwiiseagent.service.dto.TeamMemberSportDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RosterCatalogLookup {

    private final SportsCategoryRepository sportsCategoryRepository;
    private final AchievementCategoryRepository achievementCategoryRepository;
    private final TeamTypeRepository teamTypeRepository;
    private final TeamMemberSportRepository teamMemberSportRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final ObjectMapper objectMapper;

    public RosterCatalogLookup(
        SportsCategoryRepository sportsCategoryRepository,
        AchievementCategoryRepository achievementCategoryRepository,
        TeamTypeRepository teamTypeRepository,
        TeamMemberSportRepository teamMemberSportRepository,
        TeamMemberRepository teamMemberRepository,
        ObjectMapper objectMapper
    ) {
        this.sportsCategoryRepository = sportsCategoryRepository;
        this.achievementCategoryRepository = achievementCategoryRepository;
        this.teamTypeRepository = teamTypeRepository;
        this.teamMemberSportRepository = teamMemberSportRepository;
        this.teamMemberRepository = teamMemberRepository;
        this.objectMapper = objectMapper;
    }

    public void requireTeamType(String code, String tenantId) {
        if (code == null || code.isBlank()) {
            throw new BadRequestAlertException("teamType is required", "teamGroup", "teamtyperequired");
        }
        if (teamTypeRepository.findActiveForTenant(code.trim(), tenantId).isEmpty()) {
            throw new BadRequestAlertException("teamType is not an active catalog value", "teamGroup", "teamtypeinvalid");
        }
    }

    public AchievementCategory requireAchievement(String code, String tenantId) {
        if (code == null || code.isBlank()) {
            throw new BadRequestAlertException("category is required", "teamMemberAchievement", "categoryrequired");
        }
        return achievementCategoryRepository
            .findActiveForTenant(code.trim(), tenantId)
            .stream()
            .findFirst()
            .orElseThrow(() -> new BadRequestAlertException("category is not an active catalog value", "teamMemberAchievement", "categoryinvalid"));
    }

    public AchievementCategory requireAchievementById(Long id, String tenantId) {
        AchievementCategory category = achievementCategoryRepository
            .findById(id)
            .orElseThrow(() -> new BadRequestAlertException("Unknown achievement category", "teamMemberAchievement", "categorynotfound"));
        if (!Boolean.TRUE.equals(category.getIsActive())) {
            throw new BadRequestAlertException("category is not active", "teamMemberAchievement", "categoryinactive");
        }
        if (category.getTenantId() != null && !category.getTenantId().equals(tenantId)) {
            throw new BadRequestAlertException("category belongs to another tenant", "teamMemberAchievement", "categorytenant");
        }
        return category;
    }

    public void replaceMemberSports(TeamMember member, List<Long> categoryIds) {
        if (categoryIds == null || member.getId() == null) {
            return;
        }
        teamMemberSportRepository.deleteByTeamMemberId(member.getId());
        LinkedHashSet<Long> uniqueIds = new LinkedHashSet<>(categoryIds);
        List<String> codes = new ArrayList<>();
        for (Long categoryId : uniqueIds) {
            if (categoryId == null) {
                continue;
            }
            SportsCategory category = requireSport(categoryId, member.getTenantId());
            TeamMemberSport link = new TeamMemberSport();
            link.setTenantId(member.getTenantId());
            link.setTeamMemberId(member.getId());
            link.setSportsCategory(category);
            teamMemberSportRepository.save(link);
            codes.add(category.getCode());
        }
        member.setSportProfiles(mergeCategoryCodes(member.getSportProfiles(), codes));
        teamMemberRepository.save(member);
    }

    @Transactional(readOnly = true)
    public void attachSports(List<TeamMemberDTO> members) {
        if (members == null || members.isEmpty()) {
            return;
        }
        List<Long> ids = members.stream().map(TeamMemberDTO::getId).filter(id -> id != null).toList();
        if (ids.isEmpty()) {
            return;
        }
        Map<Long, List<Long>> idMap = new LinkedHashMap<>();
        Map<Long, List<String>> labelMap = new LinkedHashMap<>();
        for (TeamMemberSport link : teamMemberSportRepository.findWithCategoryByTeamMemberIdIn(ids)) {
            SportsCategory category = link.getSportsCategory();
            idMap.computeIfAbsent(link.getTeamMemberId(), key -> new ArrayList<>()).add(category.getId());
            labelMap.computeIfAbsent(link.getTeamMemberId(), key -> new ArrayList<>()).add(category.getLabel());
        }
        for (TeamMemberDTO member : members) {
            member.setSportCategoryIds(idMap.getOrDefault(member.getId(), List.of()));
            member.setSportLabels(labelMap.getOrDefault(member.getId(), List.of()));
        }
    }

    @Transactional(readOnly = true)
    public List<TeamMemberSportDTO> listForMember(Long teamMemberId) {
        return teamMemberSportRepository.findWithCategoryByTeamMemberId(teamMemberId).stream().map(this::toDto).toList();
    }

    private SportsCategory requireSport(Long id, String tenantId) {
        SportsCategory category = sportsCategoryRepository
            .findById(id)
            .orElseThrow(() -> new BadRequestAlertException("Unknown sport", "teamMember", "sportnotfound"));
        if (!Boolean.TRUE.equals(category.getIsActive())) {
            throw new BadRequestAlertException("Sport is not active", "teamMember", "sportinactive");
        }
        if (category.getTenantId() != null && tenantId != null && !category.getTenantId().equals(tenantId)) {
            throw new BadRequestAlertException("Sport belongs to another tenant", "teamMember", "sporttenant");
        }
        return category;
    }

    private TeamMemberSportDTO toDto(TeamMemberSport link) {
        TeamMemberSportDTO dto = new TeamMemberSportDTO();
        dto.setId(link.getId());
        dto.setTenantId(link.getTenantId());
        dto.setTeamMemberId(link.getTeamMemberId());
        SportsCategory category = link.getSportsCategory();
        if (category != null) {
            dto.setSportsCategoryId(category.getId());
            dto.setCode(category.getCode());
            dto.setLabel(category.getLabel());
        }
        return dto;
    }

    private String mergeCategoryCodes(String sportProfiles, List<String> codes) {
        ObjectNode node;
        try {
            if (sportProfiles != null && sportProfiles.trim().startsWith("{")) {
                JsonNode parsed = objectMapper.readTree(sportProfiles);
                node = parsed instanceof ObjectNode objectNode ? objectNode : objectMapper.createObjectNode();
            } else {
                node = objectMapper.createObjectNode();
            }
        } catch (Exception ex) {
            node = objectMapper.createObjectNode();
        }
        ArrayNode categories = node.putArray("categories");
        codes.forEach(categories::add);
        return node.toString();
    }
}
