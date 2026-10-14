package com.adwiiseagent.web.rest;

import com.adwiiseagent.errors.BadRequestAlertException;
import com.adwiiseagent.repository.RetailRecommendationRepository;
import com.adwiiseagent.service.RetailRecommendationQueryService;
import com.adwiiseagent.service.RetailRecommendationService;
import com.adwiiseagent.service.criteria.RetailRecommendationCriteria;
import com.adwiiseagent.service.dto.RetailRecommendationDTO;
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
@RequestMapping("/api/retail-recommendations")
public class RetailRecommendationResource {

    private static final Logger LOG = LoggerFactory.getLogger(RetailRecommendationResource.class);
    private static final String ENTITY_NAME = "retailRecommendation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RetailRecommendationService retailRecommendationService;
    private final RetailRecommendationRepository retailRecommendationRepository;
    private final RetailRecommendationQueryService retailRecommendationQueryService;

    public RetailRecommendationResource(
        RetailRecommendationService retailRecommendationService,
        RetailRecommendationRepository retailRecommendationRepository,
        RetailRecommendationQueryService retailRecommendationQueryService
    ) {
        this.retailRecommendationService = retailRecommendationService;
        this.retailRecommendationRepository = retailRecommendationRepository;
        this.retailRecommendationQueryService = retailRecommendationQueryService;
    }

    @PostMapping("")
    public ResponseEntity<RetailRecommendationDTO> create(@Valid @RequestBody RetailRecommendationDTO dto) throws URISyntaxException {
        LOG.debug("REST request to save RetailRecommendation : {}", dto);
        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new retailRecommendation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (dto.getTenantId() == null || dto.getTenantId().isBlank()) {
            throw new BadRequestAlertException("tenantId is required", ENTITY_NAME, "tenantidrequired");
        }
        RetailRecommendationDTO result = retailRecommendationService.save(dto);
        return ResponseEntity
            .created(new URI("/api/retail-recommendations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RetailRecommendationDTO> update(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RetailRecommendationDTO dto
    ) {
        LOG.debug("REST request to update RetailRecommendation : {}, {}", id, dto);
        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!retailRecommendationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        RetailRecommendationDTO result = retailRecommendationService.update(dto);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dto.getId().toString()))
            .body(result);
    }

    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RetailRecommendationDTO> partialUpdate(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RetailRecommendationDTO dto
    ) {
        LOG.debug("REST request to partial update RetailRecommendation : {}, {}", id, dto);
        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!retailRecommendationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        Optional<RetailRecommendationDTO> result = retailRecommendationService.partialUpdate(dto);
        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dto.getId().toString())
        );
    }

    @GetMapping("")
    public ResponseEntity<List<RetailRecommendationDTO>> getAll(
        RetailRecommendationCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get RetailRecommendation by criteria: {}", criteria);
        Page<RetailRecommendationDTO> page = retailRecommendationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count(RetailRecommendationCriteria criteria) {
        return ResponseEntity.ok().body(retailRecommendationQueryService.countByCriteria(criteria));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RetailRecommendationDTO> get(@PathVariable("id") Long id) {
        LOG.debug("REST request to get RetailRecommendation : {}", id);
        return ResponseUtil.wrapOrNotFound(retailRecommendationService.findOne(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete RetailRecommendation : {}", id);
        retailRecommendationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
