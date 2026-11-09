package com.adwiiseagent.web.rest;

import com.adwiiseagent.errors.BadRequestAlertException;
import com.adwiiseagent.repository.SchoolFacilityRepository;
import com.adwiiseagent.service.SchoolFacilityQueryService;
import com.adwiiseagent.service.SchoolFacilityService;
import com.adwiiseagent.service.criteria.SchoolFacilityCriteria;
import com.adwiiseagent.service.dto.SchoolFacilityDTO;
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
@RequestMapping("/api/school-facilities")
public class SchoolFacilityResource {

    private static final Logger LOG = LoggerFactory.getLogger(SchoolFacilityResource.class);

    private static final String ENTITY_NAME = "schoolFacility";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SchoolFacilityService schoolFacilityService;

    private final SchoolFacilityRepository schoolFacilityRepository;

    private final SchoolFacilityQueryService schoolFacilityQueryService;

    public SchoolFacilityResource(
        SchoolFacilityService schoolFacilityService,
        SchoolFacilityRepository schoolFacilityRepository,
        SchoolFacilityQueryService schoolFacilityQueryService
    ) {
        this.schoolFacilityService = schoolFacilityService;
        this.schoolFacilityRepository = schoolFacilityRepository;
        this.schoolFacilityQueryService = schoolFacilityQueryService;
    }

    @PostMapping("")
    public ResponseEntity<SchoolFacilityDTO> createSchoolFacility(@Valid @RequestBody SchoolFacilityDTO schoolFacilityDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save SchoolFacility : {}", schoolFacilityDTO);
        if (schoolFacilityDTO.getId() != null) {
            throw new BadRequestAlertException("A new schoolFacility cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (schoolFacilityDTO.getTenantId() == null || schoolFacilityDTO.getTenantId().isBlank()) {
            throw new BadRequestAlertException("tenantId is required", ENTITY_NAME, "tenantidrequired");
        }
        schoolFacilityDTO = schoolFacilityService.save(schoolFacilityDTO);
        return ResponseEntity
            .created(new URI("/api/school-facilities/" + schoolFacilityDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, schoolFacilityDTO.getId().toString()))
            .body(schoolFacilityDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SchoolFacilityDTO> updateSchoolFacility(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SchoolFacilityDTO schoolFacilityDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update SchoolFacility : {}, {}", id, schoolFacilityDTO);
        if (schoolFacilityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, schoolFacilityDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!schoolFacilityRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        schoolFacilityDTO = schoolFacilityService.update(schoolFacilityDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, schoolFacilityDTO.getId().toString()))
            .body(schoolFacilityDTO);
    }

    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SchoolFacilityDTO> partialUpdateSchoolFacility(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SchoolFacilityDTO schoolFacilityDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update SchoolFacility : {}, {}", id, schoolFacilityDTO);
        if (schoolFacilityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, schoolFacilityDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!schoolFacilityRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        Optional<SchoolFacilityDTO> result = schoolFacilityService.partialUpdate(schoolFacilityDTO);
        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, schoolFacilityDTO.getId().toString())
        );
    }

    @GetMapping("")
    public ResponseEntity<List<SchoolFacilityDTO>> getAllSchoolFacilitys(
        SchoolFacilityCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get SchoolFacilitys by criteria: {}", criteria);
        Page<SchoolFacilityDTO> page = schoolFacilityQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countSchoolFacilitys(SchoolFacilityCriteria criteria) {
        LOG.debug("REST request to count SchoolFacilitys by criteria: {}", criteria);
        return ResponseEntity.ok().body(schoolFacilityQueryService.countByCriteria(criteria));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SchoolFacilityDTO> getSchoolFacility(@PathVariable("id") Long id) {
        LOG.debug("REST request to get SchoolFacility : {}", id);
        Optional<SchoolFacilityDTO> dto = schoolFacilityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchoolFacility(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete SchoolFacility : {}", id);
        schoolFacilityService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
