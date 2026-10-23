package com.adwiiseagent.web.rest;

import com.adwiiseagent.domain.TeamMember;
import com.adwiiseagent.errors.BadRequestAlertException;
import com.adwiiseagent.repository.TeamMemberAchievementRepository;
import com.adwiiseagent.repository.TeamMemberRepository;
import com.adwiiseagent.service.TeamMemberAchievementQueryService;
import com.adwiiseagent.service.TeamMemberAchievementService;
import com.adwiiseagent.service.criteria.TeamMemberAchievementCriteria;
import com.adwiiseagent.service.dto.TeamMemberAchievementDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

@RestController
@RequestMapping("/api/team-member-achievements")
public class TeamMemberAchievementResource {

    private static final Logger LOG = LoggerFactory.getLogger(TeamMemberAchievementResource.class);
    private static final String ENTITY_NAME = "teamMemberAchievement";
    private static final Set<String> CATEGORIES = Set.of("AWARD", "HONOR", "TROPHY", "CAP", "OTHER");

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TeamMemberAchievementService teamMemberAchievementService;
    private final TeamMemberAchievementRepository teamMemberAchievementRepository;
    private final TeamMemberAchievementQueryService teamMemberAchievementQueryService;
    private final TeamMemberRepository teamMemberRepository;

    public TeamMemberAchievementResource(
        TeamMemberAchievementService teamMemberAchievementService,
        TeamMemberAchievementRepository teamMemberAchievementRepository,
        TeamMemberAchievementQueryService teamMemberAchievementQueryService,
        TeamMemberRepository teamMemberRepository
    ) {
        this.teamMemberAchievementService = teamMemberAchievementService;
        this.teamMemberAchievementRepository = teamMemberAchievementRepository;
        this.teamMemberAchievementQueryService = teamMemberAchievementQueryService;
        this.teamMemberRepository = teamMemberRepository;
    }

    private void validatePlayer(TeamMemberAchievementDTO dto) {
        if (dto.getTeamMemberId() == null) {
            throw new BadRequestAlertException("teamMemberId is required", ENTITY_NAME, "teammemberidrequired");
        }
        TeamMember member = teamMemberRepository
            .findById(dto.getTeamMemberId())
            .orElseThrow(() -> new BadRequestAlertException("Team member not found", ENTITY_NAME, "teammembernotfound"));
        if (dto.getTenantId() != null && !dto.getTenantId().isBlank() && !Objects.equals(member.getTenantId(), dto.getTenantId())) {
            throw new BadRequestAlertException("teamMemberId does not belong to the specified tenant", ENTITY_NAME, "teammembertenantmismatch");
        }
        if (dto.getCategory() != null && !CATEGORIES.contains(dto.getCategory())) {
            throw new BadRequestAlertException("category must be AWARD, HONOR, TROPHY, CAP, or OTHER", ENTITY_NAME, "categoryinvalid");
        }
    }

    @PostMapping("")
    public ResponseEntity<TeamMemberAchievementDTO> create(@Valid @RequestBody TeamMemberAchievementDTO dto) throws URISyntaxException {
        LOG.debug("REST request to save TeamMemberAchievement : {}", dto);
        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new achievement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (dto.getTenantId() == null || dto.getTenantId().isBlank()) {
            throw new BadRequestAlertException("tenantId is required", ENTITY_NAME, "tenantidrequired");
        }
        validatePlayer(dto);
        dto = teamMemberAchievementService.save(dto);
        return ResponseEntity.created(new URI("/api/team-member-achievements/" + dto.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, dto.getId().toString()))
            .body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeamMemberAchievementDTO> update(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TeamMemberAchievementDTO dto
    ) {
        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!teamMemberAchievementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        validatePlayer(dto);
        dto = teamMemberAchievementService.update(dto);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dto.getId().toString()))
            .body(dto);
    }

    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TeamMemberAchievementDTO> partialUpdate(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TeamMemberAchievementDTO dto
    ) {
        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!teamMemberAchievementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        if (dto.getTeamMemberId() != null || dto.getCategory() != null) {
            validatePlayer(dto);
        }
        Optional<TeamMemberAchievementDTO> result = teamMemberAchievementService.partialUpdate(dto);
        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dto.getId().toString())
        );
    }

    @GetMapping("")
    public ResponseEntity<List<TeamMemberAchievementDTO>> getAll(
        TeamMemberAchievementCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        Page<TeamMemberAchievementDTO> page = teamMemberAchievementQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count(TeamMemberAchievementCriteria criteria) {
        return ResponseEntity.ok().body(teamMemberAchievementQueryService.countByCriteria(criteria));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamMemberAchievementDTO> get(@PathVariable("id") Long id) {
        return ResponseUtil.wrapOrNotFound(teamMemberAchievementService.findOne(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        teamMemberAchievementService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
