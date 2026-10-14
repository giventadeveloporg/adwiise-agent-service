package com.adwiiseagent.web.rest;

import com.adwiiseagent.errors.BadRequestAlertException;
import com.adwiiseagent.repository.RetailUserLocationAssignmentRepository;
import com.adwiiseagent.service.RetailUserLocationAssignmentQueryService;
import com.adwiiseagent.service.RetailUserLocationAssignmentService;
import com.adwiiseagent.service.criteria.RetailUserLocationAssignmentCriteria;
import com.adwiiseagent.service.dto.RetailUserLocationAssignmentDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
@RequestMapping("/api/retail-user-location-assignments")
public class RetailUserLocationAssignmentResource {

    private static final Logger LOG = LoggerFactory.getLogger(RetailUserLocationAssignmentResource.class);
    private static final String ENTITY_NAME = "retailUserLocationAssignment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RetailUserLocationAssignmentService retailUserLocationAssignmentService;
    private final RetailUserLocationAssignmentRepository retailUserLocationAssignmentRepository;
    private final RetailUserLocationAssignmentQueryService retailUserLocationAssignmentQueryService;

    public RetailUserLocationAssignmentResource(
        RetailUserLocationAssignmentService retailUserLocationAssignmentService,
        RetailUserLocationAssignmentRepository retailUserLocationAssignmentRepository,
        RetailUserLocationAssignmentQueryService retailUserLocationAssignmentQueryService
    ) {
        this.retailUserLocationAssignmentService = retailUserLocationAssignmentService;
        this.retailUserLocationAssignmentRepository = retailUserLocationAssignmentRepository;
        this.retailUserLocationAssignmentQueryService = retailUserLocationAssignmentQueryService;
    }

    @PostMapping("")
    public ResponseEntity<RetailUserLocationAssignmentDTO> create(@Valid @RequestBody RetailUserLocationAssignmentDTO dto)
        throws URISyntaxException {
        LOG.debug("REST request to save RetailUserLocationAssignment : {}", dto);
        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new retailUserLocationAssignment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (dto.getTenantId() == null || dto.getTenantId().isBlank()) {
            throw new BadRequestAlertException("tenantId is required", ENTITY_NAME, "tenantidrequired");
        }
        RetailUserLocationAssignmentDTO result = retailUserLocationAssignmentService.save(dto);
        return ResponseEntity
            .created(new URI("/api/retail-user-location-assignments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RetailUserLocationAssignmentDTO> update(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RetailUserLocationAssignmentDTO dto
    ) {
        LOG.debug("REST request to update RetailUserLocationAssignment : {}, {}", id, dto);
        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!retailUserLocationAssignmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        RetailUserLocationAssignmentDTO result = retailUserLocationAssignmentService.update(dto);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dto.getId().toString()))
            .body(result);
    }

    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RetailUserLocationAssignmentDTO> partialUpdate(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RetailUserLocationAssignmentDTO dto
    ) {
        LOG.debug("REST request to partial update RetailUserLocationAssignment : {}, {}", id, dto);
        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!retailUserLocationAssignmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        Optional<RetailUserLocationAssignmentDTO> result = retailUserLocationAssignmentService.partialUpdate(dto);
        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dto.getId().toString())
        );
    }

    @GetMapping("")
    public ResponseEntity<List<RetailUserLocationAssignmentDTO>> getAll(
        RetailUserLocationAssignmentCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get RetailUserLocationAssignment by criteria: {}", criteria);
        Page<RetailUserLocationAssignmentDTO> page = retailUserLocationAssignmentQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count(RetailUserLocationAssignmentCriteria criteria) {
        return ResponseEntity.ok().body(retailUserLocationAssignmentQueryService.countByCriteria(criteria));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RetailUserLocationAssignmentDTO> get(@PathVariable("id") Long id) {
        LOG.debug("REST request to get RetailUserLocationAssignment : {}", id);
        return ResponseUtil.wrapOrNotFound(retailUserLocationAssignmentService.findOne(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete RetailUserLocationAssignment : {}", id);
        retailUserLocationAssignmentService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
