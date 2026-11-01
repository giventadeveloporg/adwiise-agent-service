package com.adwiiseagent.web.rest;

import com.adwiiseagent.errors.BadRequestAlertException;
import com.adwiiseagent.repository.LeagueStandingRepository;
import com.adwiiseagent.service.LeagueStandingQueryService;
import com.adwiiseagent.service.LeagueStandingService;
import com.adwiiseagent.service.criteria.LeagueStandingCriteria;
import com.adwiiseagent.service.dto.LeagueStandingDTO;
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
@RequestMapping("/api/league-standings")
public class LeagueStandingResource {

    private static final String ENTITY_NAME = "leagueStanding";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LeagueStandingService service;
    private final LeagueStandingRepository repository;
    private final LeagueStandingQueryService queryService;

    public LeagueStandingResource(LeagueStandingService service, LeagueStandingRepository repository, LeagueStandingQueryService queryService) {
        this.service = service;
        this.repository = repository;
        this.queryService = queryService;
    }

    @PostMapping("")
    public ResponseEntity<LeagueStandingDTO> create(@Valid @RequestBody LeagueStandingDTO dto) throws URISyntaxException {
        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new leagueStanding cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (dto.getTenantId() == null || dto.getTenantId().isBlank()) {
            throw new BadRequestAlertException("tenantId is required", ENTITY_NAME, "tenantidrequired");
        }
        dto = service.save(dto);
        return ResponseEntity.created(new URI("/api/league-standings/" + dto.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, dto.getId().toString()))
            .body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LeagueStandingDTO> update(@PathVariable("id") Long id, @Valid @RequestBody LeagueStandingDTO dto) {
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
    public ResponseEntity<LeagueStandingDTO> partialUpdate(@PathVariable("id") Long id, @NotNull @RequestBody LeagueStandingDTO dto) {
        if (dto.getId() == null) {
            dto.setId(id);
        }
        if (!Objects.equals(id, dto.getId()) || !repository.existsById(id)) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        Optional<LeagueStandingDTO> result = service.partialUpdate(dto);
        return ResponseUtil.wrapOrNotFound(result, HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dto.getId().toString()));
    }

    @GetMapping("")
    public ResponseEntity<List<LeagueStandingDTO>> getAll(LeagueStandingCriteria criteria, @org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        Page<LeagueStandingDTO> page = queryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count(LeagueStandingCriteria criteria) {
        return ResponseEntity.ok(queryService.countByCriteria(criteria));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LeagueStandingDTO> get(@PathVariable("id") Long id) {
        return ResponseUtil.wrapOrNotFound(service.findOne(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
