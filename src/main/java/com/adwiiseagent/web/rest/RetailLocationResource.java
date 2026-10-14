package com.adwiiseagent.web.rest;

import com.adwiiseagent.errors.BadRequestAlertException;
import com.adwiiseagent.repository.RetailLocationRepository;
import com.adwiiseagent.service.RetailLocationQueryService;
import com.adwiiseagent.service.RetailLocationService;
import com.adwiiseagent.service.criteria.RetailLocationCriteria;
import com.adwiiseagent.service.dto.RetailLocationDTO;
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
@RequestMapping("/api/retail-locations")
public class RetailLocationResource {

    private static final Logger LOG = LoggerFactory.getLogger(RetailLocationResource.class);
    private static final String ENTITY_NAME = "retailLocation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RetailLocationService retailLocationService;
    private final RetailLocationRepository retailLocationRepository;
    private final RetailLocationQueryService retailLocationQueryService;

    public RetailLocationResource(
        RetailLocationService retailLocationService,
        RetailLocationRepository retailLocationRepository,
        RetailLocationQueryService retailLocationQueryService
    ) {
        this.retailLocationService = retailLocationService;
        this.retailLocationRepository = retailLocationRepository;
        this.retailLocationQueryService = retailLocationQueryService;
    }

    @PostMapping("")
    public ResponseEntity<RetailLocationDTO> create(@Valid @RequestBody RetailLocationDTO dto) throws URISyntaxException {
        LOG.debug("REST request to save RetailLocation : {}", dto);
        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new retailLocation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (dto.getTenantId() == null || dto.getTenantId().isBlank()) {
            throw new BadRequestAlertException("tenantId is required", ENTITY_NAME, "tenantidrequired");
        }
        RetailLocationDTO result = retailLocationService.save(dto);
        return ResponseEntity
            .created(new URI("/api/retail-locations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RetailLocationDTO> update(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RetailLocationDTO dto
    ) {
        LOG.debug("REST request to update RetailLocation : {}, {}", id, dto);
        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!retailLocationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        RetailLocationDTO result = retailLocationService.update(dto);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dto.getId().toString()))
            .body(result);
    }

    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RetailLocationDTO> partialUpdate(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RetailLocationDTO dto
    ) {
        LOG.debug("REST request to partial update RetailLocation : {}, {}", id, dto);
        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!retailLocationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        Optional<RetailLocationDTO> result = retailLocationService.partialUpdate(dto);
        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dto.getId().toString())
        );
    }

    @GetMapping("")
    public ResponseEntity<List<RetailLocationDTO>> getAll(
        RetailLocationCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get RetailLocation by criteria: {}", criteria);
        Page<RetailLocationDTO> page = retailLocationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count(RetailLocationCriteria criteria) {
        return ResponseEntity.ok().body(retailLocationQueryService.countByCriteria(criteria));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RetailLocationDTO> get(@PathVariable("id") Long id) {
        LOG.debug("REST request to get RetailLocation : {}", id);
        return ResponseUtil.wrapOrNotFound(retailLocationService.findOne(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete RetailLocation : {}", id);
        retailLocationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
