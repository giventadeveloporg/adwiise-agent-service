package com.adwiiseagent.web.rest;

import com.adwiiseagent.errors.BadRequestAlertException;
import com.adwiiseagent.repository.RetailDailyMetricsRepository;
import com.adwiiseagent.service.RetailDailyMetricsQueryService;
import com.adwiiseagent.service.RetailDailyMetricsService;
import com.adwiiseagent.service.criteria.RetailDailyMetricsCriteria;
import com.adwiiseagent.service.dto.RetailDailyMetricsDTO;
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
@RequestMapping("/api/retail-daily-metrics")
public class RetailDailyMetricsResource {

    private static final Logger LOG = LoggerFactory.getLogger(RetailDailyMetricsResource.class);
    private static final String ENTITY_NAME = "retailDailyMetrics";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RetailDailyMetricsService retailDailyMetricsService;
    private final RetailDailyMetricsRepository retailDailyMetricsRepository;
    private final RetailDailyMetricsQueryService retailDailyMetricsQueryService;

    public RetailDailyMetricsResource(
        RetailDailyMetricsService retailDailyMetricsService,
        RetailDailyMetricsRepository retailDailyMetricsRepository,
        RetailDailyMetricsQueryService retailDailyMetricsQueryService
    ) {
        this.retailDailyMetricsService = retailDailyMetricsService;
        this.retailDailyMetricsRepository = retailDailyMetricsRepository;
        this.retailDailyMetricsQueryService = retailDailyMetricsQueryService;
    }

    @PostMapping("")
    public ResponseEntity<RetailDailyMetricsDTO> create(@Valid @RequestBody RetailDailyMetricsDTO dto) throws URISyntaxException {
        LOG.debug("REST request to save RetailDailyMetrics : {}", dto);
        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new retailDailyMetrics cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (dto.getTenantId() == null || dto.getTenantId().isBlank()) {
            throw new BadRequestAlertException("tenantId is required", ENTITY_NAME, "tenantidrequired");
        }
        RetailDailyMetricsDTO result = retailDailyMetricsService.save(dto);
        return ResponseEntity
            .created(new URI("/api/retail-daily-metrics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RetailDailyMetricsDTO> update(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RetailDailyMetricsDTO dto
    ) {
        LOG.debug("REST request to update RetailDailyMetrics : {}, {}", id, dto);
        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!retailDailyMetricsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        RetailDailyMetricsDTO result = retailDailyMetricsService.update(dto);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dto.getId().toString()))
            .body(result);
    }

    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RetailDailyMetricsDTO> partialUpdate(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RetailDailyMetricsDTO dto
    ) {
        LOG.debug("REST request to partial update RetailDailyMetrics : {}, {}", id, dto);
        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!retailDailyMetricsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        Optional<RetailDailyMetricsDTO> result = retailDailyMetricsService.partialUpdate(dto);
        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dto.getId().toString())
        );
    }

    @GetMapping("")
    public ResponseEntity<List<RetailDailyMetricsDTO>> getAll(
        RetailDailyMetricsCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get RetailDailyMetrics by criteria: {}", criteria);
        Page<RetailDailyMetricsDTO> page = retailDailyMetricsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count(RetailDailyMetricsCriteria criteria) {
        return ResponseEntity.ok().body(retailDailyMetricsQueryService.countByCriteria(criteria));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RetailDailyMetricsDTO> get(@PathVariable("id") Long id) {
        LOG.debug("REST request to get RetailDailyMetrics : {}", id);
        return ResponseUtil.wrapOrNotFound(retailDailyMetricsService.findOne(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete RetailDailyMetrics : {}", id);
        retailDailyMetricsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
