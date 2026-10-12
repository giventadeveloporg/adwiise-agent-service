package com.eventsitemanager.web.rest;

import com.eventsitemanager.errors.BadRequestAlertException;
import com.eventsitemanager.repository.RetailDepartmentDayRepository;
import com.eventsitemanager.service.RetailDepartmentDayQueryService;
import com.eventsitemanager.service.RetailDepartmentDayService;
import com.eventsitemanager.service.criteria.RetailDepartmentDayCriteria;
import com.eventsitemanager.service.dto.RetailDepartmentDayDTO;
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
@RequestMapping("/api/retail-department-days")
public class RetailDepartmentDayResource {

    private static final Logger LOG = LoggerFactory.getLogger(RetailDepartmentDayResource.class);
    private static final String ENTITY_NAME = "retailDepartmentDay";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RetailDepartmentDayService retailDepartmentDayService;
    private final RetailDepartmentDayRepository retailDepartmentDayRepository;
    private final RetailDepartmentDayQueryService retailDepartmentDayQueryService;

    public RetailDepartmentDayResource(
        RetailDepartmentDayService retailDepartmentDayService,
        RetailDepartmentDayRepository retailDepartmentDayRepository,
        RetailDepartmentDayQueryService retailDepartmentDayQueryService
    ) {
        this.retailDepartmentDayService = retailDepartmentDayService;
        this.retailDepartmentDayRepository = retailDepartmentDayRepository;
        this.retailDepartmentDayQueryService = retailDepartmentDayQueryService;
    }

    @PostMapping("")
    public ResponseEntity<RetailDepartmentDayDTO> create(@Valid @RequestBody RetailDepartmentDayDTO dto) throws URISyntaxException {
        LOG.debug("REST request to save RetailDepartmentDay : {}", dto);
        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new retailDepartmentDay cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (dto.getTenantId() == null || dto.getTenantId().isBlank()) {
            throw new BadRequestAlertException("tenantId is required", ENTITY_NAME, "tenantidrequired");
        }
        RetailDepartmentDayDTO result = retailDepartmentDayService.save(dto);
        return ResponseEntity
            .created(new URI("/api/retail-department-days/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RetailDepartmentDayDTO> update(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RetailDepartmentDayDTO dto
    ) {
        LOG.debug("REST request to update RetailDepartmentDay : {}, {}", id, dto);
        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!retailDepartmentDayRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        RetailDepartmentDayDTO result = retailDepartmentDayService.update(dto);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dto.getId().toString()))
            .body(result);
    }

    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RetailDepartmentDayDTO> partialUpdate(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RetailDepartmentDayDTO dto
    ) {
        LOG.debug("REST request to partial update RetailDepartmentDay : {}, {}", id, dto);
        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!retailDepartmentDayRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        Optional<RetailDepartmentDayDTO> result = retailDepartmentDayService.partialUpdate(dto);
        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dto.getId().toString())
        );
    }

    @GetMapping("")
    public ResponseEntity<List<RetailDepartmentDayDTO>> getAll(
        RetailDepartmentDayCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get RetailDepartmentDay by criteria: {}", criteria);
        Page<RetailDepartmentDayDTO> page = retailDepartmentDayQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count(RetailDepartmentDayCriteria criteria) {
        return ResponseEntity.ok().body(retailDepartmentDayQueryService.countByCriteria(criteria));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RetailDepartmentDayDTO> get(@PathVariable("id") Long id) {
        LOG.debug("REST request to get RetailDepartmentDay : {}", id);
        return ResponseUtil.wrapOrNotFound(retailDepartmentDayService.findOne(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete RetailDepartmentDay : {}", id);
        retailDepartmentDayService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
