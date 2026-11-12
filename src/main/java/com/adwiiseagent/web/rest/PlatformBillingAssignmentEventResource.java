package com.adwiiseagent.web.rest;

import com.adwiiseagent.errors.BadRequestAlertException;
import com.adwiiseagent.repository.PlatformBillingAssignmentEventRepository;
import com.adwiiseagent.service.PlatformBillingAssignmentEventQueryService;
import com.adwiiseagent.service.PlatformBillingAssignmentEventService;
import com.adwiiseagent.service.criteria.PlatformBillingAssignmentEventCriteria;
import com.adwiiseagent.service.dto.PlatformBillingAssignmentEventDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
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
@RequestMapping("/api/platform-billing-assignment-events")
public class PlatformBillingAssignmentEventResource {

    private static final String ENTITY_NAME = "platformBillingAssignmentEvent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlatformBillingAssignmentEventService service;
    private final PlatformBillingAssignmentEventQueryService queryService;
    private final PlatformBillingAssignmentEventRepository repository;

    public PlatformBillingAssignmentEventResource(
        PlatformBillingAssignmentEventService service,
        PlatformBillingAssignmentEventQueryService queryService,
        PlatformBillingAssignmentEventRepository repository
    ) {
        this.service = service;
        this.queryService = queryService;
        this.repository = repository;
    }

    @PostMapping("")
    public ResponseEntity<PlatformBillingAssignmentEventDTO> create(@Valid @RequestBody PlatformBillingAssignmentEventDTO dto)
        throws URISyntaxException {
        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new assignment event cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlatformBillingAssignmentEventDTO result = service.save(dto);
        return ResponseEntity
            .created(new URI("/api/platform-billing-assignment-events/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PlatformBillingAssignmentEventDTO> partialUpdate(
        @PathVariable Long id,
        @NotNull @RequestBody PlatformBillingAssignmentEventDTO dto
    ) {
        if (dto.getId() == null) dto.setId(id);
        if (!Objects.equals(id, dto.getId()) || !repository.existsById(id)) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        Optional<PlatformBillingAssignmentEventDTO> result = service.partialUpdate(dto);
        return ResponseUtil.wrapOrNotFound(result, HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, id.toString()));
    }

    @GetMapping("")
    public ResponseEntity<List<PlatformBillingAssignmentEventDTO>> getAll(
        PlatformBillingAssignmentEventCriteria criteria,
        @ParameterObject Pageable pageable
    ) {
        Page<PlatformBillingAssignmentEventDTO> page = queryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlatformBillingAssignmentEventDTO> get(@PathVariable Long id) {
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
