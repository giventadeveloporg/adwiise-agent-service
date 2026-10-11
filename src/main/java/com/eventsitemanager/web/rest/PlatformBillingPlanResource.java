package com.eventsitemanager.web.rest;

import com.eventsitemanager.errors.BadRequestAlertException;
import com.eventsitemanager.repository.PlatformBillingPlanRepository;
import com.eventsitemanager.service.PlatformBillingPlanQueryService;
import com.eventsitemanager.service.PlatformBillingPlanService;
import com.eventsitemanager.service.criteria.PlatformBillingPlanCriteria;
import com.eventsitemanager.service.dto.PlatformBillingPlanDTO;
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
@RequestMapping("/api/platform-billing-plans")
public class PlatformBillingPlanResource {

    private static final String ENTITY_NAME = "platformBillingPlan";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlatformBillingPlanService service;
    private final PlatformBillingPlanQueryService queryService;
    private final PlatformBillingPlanRepository repository;

    public PlatformBillingPlanResource(
        PlatformBillingPlanService service,
        PlatformBillingPlanQueryService queryService,
        PlatformBillingPlanRepository repository
    ) {
        this.service = service;
        this.queryService = queryService;
        this.repository = repository;
    }

    @PostMapping("")
    public ResponseEntity<PlatformBillingPlanDTO> create(@Valid @RequestBody PlatformBillingPlanDTO dto) throws URISyntaxException {
        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new plan cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlatformBillingPlanDTO result = service.save(dto);
        return ResponseEntity
            .created(new URI("/api/platform-billing-plans/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlatformBillingPlanDTO> update(@PathVariable Long id, @Valid @RequestBody PlatformBillingPlanDTO dto) {
        if (dto.getId() == null || !Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        PlatformBillingPlanDTO result = service.update(dto);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .body(result);
    }

    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PlatformBillingPlanDTO> partialUpdate(@PathVariable Long id, @NotNull @RequestBody PlatformBillingPlanDTO dto) {
        if (dto.getId() == null) {
            dto.setId(id);
        }
        if (!Objects.equals(id, dto.getId()) || !repository.existsById(id)) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        Optional<PlatformBillingPlanDTO> result = service.partialUpdate(dto);
        return ResponseUtil.wrapOrNotFound(result, HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, id.toString()));
    }

    @GetMapping("")
    public ResponseEntity<List<PlatformBillingPlanDTO>> getAll(
        PlatformBillingPlanCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        Page<PlatformBillingPlanDTO> page = queryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlatformBillingPlanDTO> get(@PathVariable Long id) {
        return ResponseUtil.wrapOrNotFound(service.findOne(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
