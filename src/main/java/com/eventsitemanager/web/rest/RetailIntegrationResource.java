package com.eventsitemanager.web.rest;

import com.eventsitemanager.errors.BadRequestAlertException;
import com.eventsitemanager.repository.RetailIntegrationRepository;
import com.eventsitemanager.service.RetailIntegrationQueryService;
import com.eventsitemanager.service.RetailIntegrationService;
import com.eventsitemanager.service.criteria.RetailIntegrationCriteria;
import com.eventsitemanager.service.dto.RetailIntegrationDTO;
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
@RequestMapping("/api/retail-integrations")
public class RetailIntegrationResource {

    private static final Logger LOG = LoggerFactory.getLogger(RetailIntegrationResource.class);
    private static final String ENTITY_NAME = "retailIntegration";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RetailIntegrationService retailIntegrationService;
    private final RetailIntegrationRepository retailIntegrationRepository;
    private final RetailIntegrationQueryService retailIntegrationQueryService;

    public RetailIntegrationResource(
        RetailIntegrationService retailIntegrationService,
        RetailIntegrationRepository retailIntegrationRepository,
        RetailIntegrationQueryService retailIntegrationQueryService
    ) {
        this.retailIntegrationService = retailIntegrationService;
        this.retailIntegrationRepository = retailIntegrationRepository;
        this.retailIntegrationQueryService = retailIntegrationQueryService;
    }

    @PostMapping("")
    public ResponseEntity<RetailIntegrationDTO> create(@Valid @RequestBody RetailIntegrationDTO dto) throws URISyntaxException {
        LOG.debug("REST request to save RetailIntegration : {}", dto);
        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new retailIntegration cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (dto.getTenantId() == null || dto.getTenantId().isBlank()) {
            throw new BadRequestAlertException("tenantId is required", ENTITY_NAME, "tenantidrequired");
        }
        RetailIntegrationDTO result = retailIntegrationService.save(dto);
        return ResponseEntity
            .created(new URI("/api/retail-integrations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RetailIntegrationDTO> update(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RetailIntegrationDTO dto
    ) {
        LOG.debug("REST request to update RetailIntegration : {}, {}", id, dto);
        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!retailIntegrationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        RetailIntegrationDTO result = retailIntegrationService.update(dto);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dto.getId().toString()))
            .body(result);
    }

    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RetailIntegrationDTO> partialUpdate(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RetailIntegrationDTO dto
    ) {
        LOG.debug("REST request to partial update RetailIntegration : {}, {}", id, dto);
        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!retailIntegrationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        Optional<RetailIntegrationDTO> result = retailIntegrationService.partialUpdate(dto);
        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dto.getId().toString())
        );
    }

    @GetMapping("")
    public ResponseEntity<List<RetailIntegrationDTO>> getAll(
        RetailIntegrationCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get RetailIntegration by criteria: {}", criteria);
        Page<RetailIntegrationDTO> page = retailIntegrationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count(RetailIntegrationCriteria criteria) {
        return ResponseEntity.ok().body(retailIntegrationQueryService.countByCriteria(criteria));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RetailIntegrationDTO> get(@PathVariable("id") Long id) {
        LOG.debug("REST request to get RetailIntegration : {}", id);
        return ResponseUtil.wrapOrNotFound(retailIntegrationService.findOne(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete RetailIntegration : {}", id);
        retailIntegrationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
