package com.adwiiseagent.web.rest;

import com.adwiiseagent.errors.BadRequestAlertException;
import com.adwiiseagent.repository.RetailUserRegionAssignmentRepository;
import com.adwiiseagent.service.RetailUserRegionAssignmentQueryService;
import com.adwiiseagent.service.RetailUserRegionAssignmentService;
import com.adwiiseagent.service.criteria.RetailUserRegionAssignmentCriteria;
import com.adwiiseagent.service.dto.RetailUserRegionAssignmentDTO;
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
@RequestMapping("/api/retail-user-region-assignments")
public class RetailUserRegionAssignmentResource {

    private static final Logger LOG = LoggerFactory.getLogger(RetailUserRegionAssignmentResource.class);
    private static final String ENTITY_NAME = "retailUserRegionAssignment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RetailUserRegionAssignmentService retailUserRegionAssignmentService;
    private final RetailUserRegionAssignmentRepository retailUserRegionAssignmentRepository;
    private final RetailUserRegionAssignmentQueryService retailUserRegionAssignmentQueryService;

    public RetailUserRegionAssignmentResource(
        RetailUserRegionAssignmentService retailUserRegionAssignmentService,
        RetailUserRegionAssignmentRepository retailUserRegionAssignmentRepository,
        RetailUserRegionAssignmentQueryService retailUserRegionAssignmentQueryService
    ) {
        this.retailUserRegionAssignmentService = retailUserRegionAssignmentService;
        this.retailUserRegionAssignmentRepository = retailUserRegionAssignmentRepository;
        this.retailUserRegionAssignmentQueryService = retailUserRegionAssignmentQueryService;
    }

    @PostMapping("")
    public ResponseEntity<RetailUserRegionAssignmentDTO> create(@Valid @RequestBody RetailUserRegionAssignmentDTO dto)
        throws URISyntaxException {
        LOG.debug("REST request to save RetailUserRegionAssignment : {}", dto);
        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new retailUserRegionAssignment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (dto.getTenantId() == null || dto.getTenantId().isBlank()) {
            throw new BadRequestAlertException("tenantId is required", ENTITY_NAME, "tenantidrequired");
        }
        RetailUserRegionAssignmentDTO result = retailUserRegionAssignmentService.save(dto);
        return ResponseEntity
            .created(new URI("/api/retail-user-region-assignments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RetailUserRegionAssignmentDTO> update(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RetailUserRegionAssignmentDTO dto
    ) {
        LOG.debug("REST request to update RetailUserRegionAssignment : {}, {}", id, dto);
        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!retailUserRegionAssignmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        RetailUserRegionAssignmentDTO result = retailUserRegionAssignmentService.update(dto);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dto.getId().toString()))
            .body(result);
    }

    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RetailUserRegionAssignmentDTO> partialUpdate(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RetailUserRegionAssignmentDTO dto
    ) {
        LOG.debug("REST request to partial update RetailUserRegionAssignment : {}, {}", id, dto);
        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!retailUserRegionAssignmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        Optional<RetailUserRegionAssignmentDTO> result = retailUserRegionAssignmentService.partialUpdate(dto);
        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dto.getId().toString())
        );
    }

    @GetMapping("")
    public ResponseEntity<List<RetailUserRegionAssignmentDTO>> getAll(
        RetailUserRegionAssignmentCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get RetailUserRegionAssignment by criteria: {}", criteria);
        Page<RetailUserRegionAssignmentDTO> page = retailUserRegionAssignmentQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count(RetailUserRegionAssignmentCriteria criteria) {
        return ResponseEntity.ok().body(retailUserRegionAssignmentQueryService.countByCriteria(criteria));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RetailUserRegionAssignmentDTO> get(@PathVariable("id") Long id) {
        LOG.debug("REST request to get RetailUserRegionAssignment : {}", id);
        return ResponseUtil.wrapOrNotFound(retailUserRegionAssignmentService.findOne(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete RetailUserRegionAssignment : {}", id);
        retailUserRegionAssignmentService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
