package com.adwiiseagent.web.rest;

import com.adwiiseagent.errors.BadRequestAlertException;
import com.adwiiseagent.repository.LeagueClubRepository;
import com.adwiiseagent.service.LeagueClubQueryService;
import com.adwiiseagent.service.LeagueClubService;
import com.adwiiseagent.service.criteria.LeagueClubCriteria;
import com.adwiiseagent.service.dto.LeagueClubDTO;
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
@RequestMapping("/api/league-clubs")
public class LeagueClubResource {

    private static final String ENTITY_NAME = "leagueClub";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LeagueClubService service;
    private final LeagueClubRepository repository;
    private final LeagueClubQueryService queryService;

    public LeagueClubResource(LeagueClubService service, LeagueClubRepository repository, LeagueClubQueryService queryService) {
        this.service = service;
        this.repository = repository;
        this.queryService = queryService;
    }

    @PostMapping("")
    public ResponseEntity<LeagueClubDTO> create(@Valid @RequestBody LeagueClubDTO dto) throws URISyntaxException {
        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new leagueClub cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (dto.getTenantId() == null || dto.getTenantId().isBlank()) {
            throw new BadRequestAlertException("tenantId is required", ENTITY_NAME, "tenantidrequired");
        }
        dto = service.save(dto);
        return ResponseEntity.created(new URI("/api/league-clubs/" + dto.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, dto.getId().toString()))
            .body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LeagueClubDTO> update(@PathVariable("id") Long id, @Valid @RequestBody LeagueClubDTO dto) {
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
    public ResponseEntity<LeagueClubDTO> partialUpdate(@PathVariable("id") Long id, @NotNull @RequestBody LeagueClubDTO dto) {
        if (dto.getId() == null) {
            dto.setId(id);
        }
        if (!Objects.equals(id, dto.getId()) || !repository.existsById(id)) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        Optional<LeagueClubDTO> result = service.partialUpdate(dto);
        return ResponseUtil.wrapOrNotFound(result, HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dto.getId().toString()));
    }

    @GetMapping("")
    public ResponseEntity<List<LeagueClubDTO>> getAll(LeagueClubCriteria criteria, @org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        Page<LeagueClubDTO> page = queryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count(LeagueClubCriteria criteria) {
        return ResponseEntity.ok(queryService.countByCriteria(criteria));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LeagueClubDTO> get(@PathVariable("id") Long id) {
        return ResponseUtil.wrapOrNotFound(service.findOne(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
