package com.adwiiseagent.web.rest;

import com.adwiiseagent.errors.BadRequestAlertException;
import com.adwiiseagent.repository.RetailLocationProfileRepository;
import com.adwiiseagent.service.RetailLocationProfileQueryService;
import com.adwiiseagent.service.RetailLocationProfileService;
import com.adwiiseagent.service.criteria.RetailLocationProfileCriteria;
import com.adwiiseagent.service.dto.RetailLocationProfileDTO;
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
@RequestMapping("/api/retail-location-profiles")
public class RetailLocationProfileResource {

    private static final Logger LOG = LoggerFactory.getLogger(RetailLocationProfileResource.class);
    private static final String ENTITY_NAME = "retailLocationProfile";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RetailLocationProfileService retailLocationProfileService;
    private final RetailLocationProfileRepository retailLocationProfileRepository;
    private final RetailLocationProfileQueryService retailLocationProfileQueryService;

    public RetailLocationProfileResource(
        RetailLocationProfileService retailLocationProfileService,
        RetailLocationProfileRepository retailLocationProfileRepository,
        RetailLocationProfileQueryService retailLocationProfileQueryService
    ) {
        this.retailLocationProfileService = retailLocationProfileService;
        this.retailLocationProfileRepository = retailLocationProfileRepository;
        this.retailLocationProfileQueryService = retailLocationProfileQueryService;
    }

    @PostMapping("")
    public ResponseEntity<RetailLocationProfileDTO> create(@Valid @RequestBody RetailLocationProfileDTO dto) throws URISyntaxException {
        LOG.debug("REST request to save RetailLocationProfile : {}", dto);
        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new retailLocationProfile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (dto.getTenantId() == null || dto.getTenantId().isBlank()) {
            throw new BadRequestAlertException("tenantId is required", ENTITY_NAME, "tenantidrequired");
        }
        RetailLocationProfileDTO result = retailLocationProfileService.save(dto);
        return ResponseEntity
            .created(new URI("/api/retail-location-profiles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RetailLocationProfileDTO> update(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RetailLocationProfileDTO dto
    ) {
        LOG.debug("REST request to update RetailLocationProfile : {}, {}", id, dto);
        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!retailLocationProfileRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        RetailLocationProfileDTO result = retailLocationProfileService.update(dto);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dto.getId().toString()))
            .body(result);
    }

    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RetailLocationProfileDTO> partialUpdate(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RetailLocationProfileDTO dto
    ) {
        LOG.debug("REST request to partial update RetailLocationProfile : {}, {}", id, dto);
        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!retailLocationProfileRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        Optional<RetailLocationProfileDTO> result = retailLocationProfileService.partialUpdate(dto);
        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dto.getId().toString())
        );
    }

    @GetMapping("")
    public ResponseEntity<List<RetailLocationProfileDTO>> getAll(
        RetailLocationProfileCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get RetailLocationProfile by criteria: {}", criteria);
        Page<RetailLocationProfileDTO> page = retailLocationProfileQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count(RetailLocationProfileCriteria criteria) {
        return ResponseEntity.ok().body(retailLocationProfileQueryService.countByCriteria(criteria));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RetailLocationProfileDTO> get(@PathVariable("id") Long id) {
        LOG.debug("REST request to get RetailLocationProfile : {}", id);
        return ResponseUtil.wrapOrNotFound(retailLocationProfileService.findOne(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete RetailLocationProfile : {}", id);
        retailLocationProfileService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
