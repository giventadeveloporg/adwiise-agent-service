package com.adwiiseagent.web.rest;

import com.adwiiseagent.errors.BadRequestAlertException;
import com.adwiiseagent.repository.SchoolHighlightRepository;
import com.adwiiseagent.service.SchoolHighlightQueryService;
import com.adwiiseagent.service.SchoolHighlightService;
import com.adwiiseagent.service.criteria.SchoolHighlightCriteria;
import com.adwiiseagent.service.dto.SchoolHighlightDTO;
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
@RequestMapping("/api/school-highlights")
public class SchoolHighlightResource {

    private static final Logger LOG = LoggerFactory.getLogger(SchoolHighlightResource.class);

    private static final String ENTITY_NAME = "schoolHighlight";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SchoolHighlightService schoolHighlightService;

    private final SchoolHighlightRepository schoolHighlightRepository;

    private final SchoolHighlightQueryService schoolHighlightQueryService;

    public SchoolHighlightResource(
        SchoolHighlightService schoolHighlightService,
        SchoolHighlightRepository schoolHighlightRepository,
        SchoolHighlightQueryService schoolHighlightQueryService
    ) {
        this.schoolHighlightService = schoolHighlightService;
        this.schoolHighlightRepository = schoolHighlightRepository;
        this.schoolHighlightQueryService = schoolHighlightQueryService;
    }

    @PostMapping("")
    public ResponseEntity<SchoolHighlightDTO> createSchoolHighlight(@Valid @RequestBody SchoolHighlightDTO schoolHighlightDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save SchoolHighlight : {}", schoolHighlightDTO);
        if (schoolHighlightDTO.getId() != null) {
            throw new BadRequestAlertException("A new schoolHighlight cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (schoolHighlightDTO.getTenantId() == null || schoolHighlightDTO.getTenantId().isBlank()) {
            throw new BadRequestAlertException("tenantId is required", ENTITY_NAME, "tenantidrequired");
        }
        schoolHighlightDTO = schoolHighlightService.save(schoolHighlightDTO);
        return ResponseEntity
            .created(new URI("/api/school-highlights/" + schoolHighlightDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, schoolHighlightDTO.getId().toString()))
            .body(schoolHighlightDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SchoolHighlightDTO> updateSchoolHighlight(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SchoolHighlightDTO schoolHighlightDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update SchoolHighlight : {}, {}", id, schoolHighlightDTO);
        if (schoolHighlightDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, schoolHighlightDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!schoolHighlightRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        schoolHighlightDTO = schoolHighlightService.update(schoolHighlightDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, schoolHighlightDTO.getId().toString()))
            .body(schoolHighlightDTO);
    }

    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SchoolHighlightDTO> partialUpdateSchoolHighlight(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SchoolHighlightDTO schoolHighlightDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update SchoolHighlight : {}, {}", id, schoolHighlightDTO);
        if (schoolHighlightDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, schoolHighlightDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!schoolHighlightRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        Optional<SchoolHighlightDTO> result = schoolHighlightService.partialUpdate(schoolHighlightDTO);
        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, schoolHighlightDTO.getId().toString())
        );
    }

    @GetMapping("")
    public ResponseEntity<List<SchoolHighlightDTO>> getAllSchoolHighlights(
        SchoolHighlightCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get SchoolHighlights by criteria: {}", criteria);
        Page<SchoolHighlightDTO> page = schoolHighlightQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countSchoolHighlights(SchoolHighlightCriteria criteria) {
        LOG.debug("REST request to count SchoolHighlights by criteria: {}", criteria);
        return ResponseEntity.ok().body(schoolHighlightQueryService.countByCriteria(criteria));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SchoolHighlightDTO> getSchoolHighlight(@PathVariable("id") Long id) {
        LOG.debug("REST request to get SchoolHighlight : {}", id);
        Optional<SchoolHighlightDTO> dto = schoolHighlightService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchoolHighlight(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete SchoolHighlight : {}", id);
        schoolHighlightService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
