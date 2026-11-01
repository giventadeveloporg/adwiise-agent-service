package com.adwiiseagent.web.rest;

import com.adwiiseagent.errors.BadRequestAlertException;
import com.adwiiseagent.repository.LeagueRepository;
import com.adwiiseagent.service.LeagueQueryService;
import com.adwiiseagent.service.LeagueService;
import com.adwiiseagent.service.LeagueTableService;
import com.adwiiseagent.service.criteria.LeagueCriteria;
import com.adwiiseagent.service.dto.LeagueDTO;
import com.adwiiseagent.service.dto.LeagueStandingRowDTO;
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
@RequestMapping("/api/leagues")
public class LeagueResource {

    private static final String ENTITY_NAME = "league";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LeagueService service;
    private final LeagueRepository repository;
    private final LeagueQueryService queryService;
    private final LeagueTableService leagueTableService;

    public LeagueResource(
        LeagueService service,
        LeagueRepository repository,
        LeagueQueryService queryService,
        LeagueTableService leagueTableService
    ) {
        this.service = service;
        this.repository = repository;
        this.queryService = queryService;
        this.leagueTableService = leagueTableService;
    }

    @PostMapping("")
    public ResponseEntity<LeagueDTO> create(@Valid @RequestBody LeagueDTO dto) throws URISyntaxException {
        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new league cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (dto.getTenantId() == null || dto.getTenantId().isBlank()) {
            throw new BadRequestAlertException("tenantId is required", ENTITY_NAME, "tenantidrequired");
        }
        dto = service.save(dto);
        return ResponseEntity.created(new URI("/api/leagues/" + dto.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, dto.getId().toString()))
            .body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LeagueDTO> update(@PathVariable("id") Long id, @Valid @RequestBody LeagueDTO dto) {
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
    public ResponseEntity<LeagueDTO> partialUpdate(@PathVariable("id") Long id, @NotNull @RequestBody LeagueDTO dto) {
        if (dto.getId() == null) {
            dto.setId(id);
        }
        if (!Objects.equals(id, dto.getId()) || !repository.existsById(id)) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        Optional<LeagueDTO> result = service.partialUpdate(dto);
        return ResponseUtil.wrapOrNotFound(result, HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dto.getId().toString()));
    }

    @GetMapping("")
    public ResponseEntity<List<LeagueDTO>> getAll(LeagueCriteria criteria, @org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        Page<LeagueDTO> page = queryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count(LeagueCriteria criteria) {
        return ResponseEntity.ok(queryService.countByCriteria(criteria));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LeagueDTO> get(@PathVariable("id") Long id) {
        return ResponseUtil.wrapOrNotFound(service.findOne(id));
    }

    @GetMapping("/{id}/standings-table")
    public ResponseEntity<List<LeagueStandingRowDTO>> standingsTable(@PathVariable("id") Long id) {
        if (!repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        return ResponseEntity.ok(leagueTableService.table(id));
    }

    @PostMapping("/{id}/standings/copy-calculated")
    public ResponseEntity<List<LeagueStandingRowDTO>> copyCalculated(@PathVariable("id") Long id) {
        if (!repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        return ResponseEntity.ok(leagueTableService.copyCalculated(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
