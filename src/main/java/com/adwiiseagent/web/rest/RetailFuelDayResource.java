package com.adwiiseagent.web.rest;

import com.adwiiseagent.errors.BadRequestAlertException;
import com.adwiiseagent.repository.RetailFuelDayRepository;
import com.adwiiseagent.service.RetailFuelDayQueryService;
import com.adwiiseagent.service.RetailFuelDayService;
import com.adwiiseagent.service.criteria.RetailFuelDayCriteria;
import com.adwiiseagent.service.dto.RetailFuelDayDTO;
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
@RequestMapping("/api/retail-fuel-days")
public class RetailFuelDayResource {

    private static final Logger LOG = LoggerFactory.getLogger(RetailFuelDayResource.class);
    private static final String ENTITY_NAME = "retailFuelDay";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RetailFuelDayService retailFuelDayService;
    private final RetailFuelDayRepository retailFuelDayRepository;
    private final RetailFuelDayQueryService retailFuelDayQueryService;

    public RetailFuelDayResource(
        RetailFuelDayService retailFuelDayService,
        RetailFuelDayRepository retailFuelDayRepository,
        RetailFuelDayQueryService retailFuelDayQueryService
    ) {
        this.retailFuelDayService = retailFuelDayService;
        this.retailFuelDayRepository = retailFuelDayRepository;
        this.retailFuelDayQueryService = retailFuelDayQueryService;
    }

    @PostMapping("")
    public ResponseEntity<RetailFuelDayDTO> create(@Valid @RequestBody RetailFuelDayDTO dto) throws URISyntaxException {
        LOG.debug("REST request to save RetailFuelDay : {}", dto);
        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new retailFuelDay cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (dto.getTenantId() == null || dto.getTenantId().isBlank()) {
            throw new BadRequestAlertException("tenantId is required", ENTITY_NAME, "tenantidrequired");
        }
        RetailFuelDayDTO result = retailFuelDayService.save(dto);
        return ResponseEntity
            .created(new URI("/api/retail-fuel-days/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RetailFuelDayDTO> update(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RetailFuelDayDTO dto
    ) {
        LOG.debug("REST request to update RetailFuelDay : {}, {}", id, dto);
        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!retailFuelDayRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        RetailFuelDayDTO result = retailFuelDayService.update(dto);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dto.getId().toString()))
            .body(result);
    }

    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RetailFuelDayDTO> partialUpdate(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RetailFuelDayDTO dto
    ) {
        LOG.debug("REST request to partial update RetailFuelDay : {}, {}", id, dto);
        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!retailFuelDayRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        Optional<RetailFuelDayDTO> result = retailFuelDayService.partialUpdate(dto);
        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dto.getId().toString())
        );
    }

    @GetMapping("")
    public ResponseEntity<List<RetailFuelDayDTO>> getAll(
        RetailFuelDayCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get RetailFuelDay by criteria: {}", criteria);
        Page<RetailFuelDayDTO> page = retailFuelDayQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count(RetailFuelDayCriteria criteria) {
        return ResponseEntity.ok().body(retailFuelDayQueryService.countByCriteria(criteria));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RetailFuelDayDTO> get(@PathVariable("id") Long id) {
        LOG.debug("REST request to get RetailFuelDay : {}", id);
        return ResponseUtil.wrapOrNotFound(retailFuelDayService.findOne(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete RetailFuelDay : {}", id);
        retailFuelDayService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
