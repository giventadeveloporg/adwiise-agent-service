package com.adwiiseagent.web.rest;

import com.adwiiseagent.errors.BadRequestAlertException;
import com.adwiiseagent.repository.TeamGroupMembershipRepository;
import com.adwiiseagent.service.TeamGroupMembershipQueryService;
import com.adwiiseagent.service.TeamGroupMembershipService;
import com.adwiiseagent.service.criteria.TeamGroupMembershipCriteria;
import com.adwiiseagent.service.dto.TeamGroupMembershipDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
@RequestMapping("/api/team-group-memberships")
public class TeamGroupMembershipResource {

    private static final String ENTITY_NAME = "teamGroupMembership";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TeamGroupMembershipService service;
    private final TeamGroupMembershipRepository repository;
    private final TeamGroupMembershipQueryService queryService;

    public TeamGroupMembershipResource(TeamGroupMembershipService service, TeamGroupMembershipRepository repository, TeamGroupMembershipQueryService queryService) {
        this.service = service;
        this.repository = repository;
        this.queryService = queryService;
    }

    @PostMapping("")
    public ResponseEntity<TeamGroupMembershipDTO> create(@Valid @RequestBody TeamGroupMembershipDTO dto) throws URISyntaxException {
        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new teamGroupMembership cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (dto.getTenantId() == null || dto.getTenantId().isBlank()) {
            throw new BadRequestAlertException("tenantId is required", ENTITY_NAME, "tenantidrequired");
        }
        dto = service.save(dto);
        return ResponseEntity.created(new URI("/api/team-group-memberships/" + dto.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, dto.getId().toString()))
            .body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeamGroupMembershipDTO> update(@PathVariable("id") Long id, @Valid @RequestBody TeamGroupMembershipDTO dto) {
        if (dto.getId() == null || !Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        dto = service.update(dto);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dto.getId().toString()))
            .body(dto);
    }

    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TeamGroupMembershipDTO> partialUpdate(@PathVariable("id") Long id, @NotNull @RequestBody TeamGroupMembershipDTO dto) {
        if (dto.getId() == null) {
            dto.setId(id);
        }
        if (!Objects.equals(id, dto.getId()) || !repository.existsById(id)) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        Optional<TeamGroupMembershipDTO> result = service.partialUpdate(dto);
        return ResponseUtil.wrapOrNotFound(result, HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dto.getId().toString()));
    }

    @GetMapping("")
    public ResponseEntity<List<TeamGroupMembershipDTO>> getAll(TeamGroupMembershipCriteria criteria, @org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        Page<TeamGroupMembershipDTO> page = queryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count(TeamGroupMembershipCriteria criteria) {
        return ResponseEntity.ok(queryService.countByCriteria(criteria));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamGroupMembershipDTO> get(@PathVariable("id") Long id) {
        return ResponseUtil.wrapOrNotFound(service.findOne(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
