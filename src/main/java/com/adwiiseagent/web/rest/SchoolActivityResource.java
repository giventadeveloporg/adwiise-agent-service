package com.adwiiseagent.web.rest;

import com.adwiiseagent.errors.BadRequestAlertException;
import com.adwiiseagent.repository.SchoolActivityRepository;
import com.adwiiseagent.service.SchoolActivityQueryService;
import com.adwiiseagent.service.SchoolActivityService;
import com.adwiiseagent.service.criteria.SchoolActivityCriteria;
import com.adwiiseagent.service.dto.SchoolActivityDTO;
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
@RequestMapping("/api/school-activities")
public class SchoolActivityResource {

    private static final Logger LOG = LoggerFactory.getLogger(SchoolActivityResource.class);

    private static final String ENTITY_NAME = "schoolActivity";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SchoolActivityService schoolActivityService;

    private final SchoolActivityRepository schoolActivityRepository;

    private final SchoolActivityQueryService schoolActivityQueryService;

    public SchoolActivityResource(
        SchoolActivityService schoolActivityService,
        SchoolActivityRepository schoolActivityRepository,
        SchoolActivityQueryService schoolActivityQueryService
    ) {
        this.schoolActivityService = schoolActivityService;
        this.schoolActivityRepository = schoolActivityRepository;
        this.schoolActivityQueryService = schoolActivityQueryService;
    }

    @PostMapping("")
    public ResponseEntity<SchoolActivityDTO> createSchoolActivity(@Valid @RequestBody SchoolActivityDTO schoolActivityDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save SchoolActivity : {}", schoolActivityDTO);
        if (schoolActivityDTO.getId() != null) {
            throw new BadRequestAlertException("A new schoolActivity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (schoolActivityDTO.getTenantId() == null || schoolActivityDTO.getTenantId().isBlank()) {
            throw new BadRequestAlertException("tenantId is required", ENTITY_NAME, "tenantidrequired");
        }
        schoolActivityDTO = schoolActivityService.save(schoolActivityDTO);
        return ResponseEntity
            .created(new URI("/api/school-activities/" + schoolActivityDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, schoolActivityDTO.getId().toString()))
            .body(schoolActivityDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SchoolActivityDTO> updateSchoolActivity(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SchoolActivityDTO schoolActivityDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update SchoolActivity : {}, {}", id, schoolActivityDTO);
        if (schoolActivityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, schoolActivityDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!schoolActivityRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        schoolActivityDTO = schoolActivityService.update(schoolActivityDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, schoolActivityDTO.getId().toString()))
            .body(schoolActivityDTO);
    }

    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SchoolActivityDTO> partialUpdateSchoolActivity(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SchoolActivityDTO schoolActivityDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update SchoolActivity : {}, {}", id, schoolActivityDTO);
        if (schoolActivityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, schoolActivityDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!schoolActivityRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        Optional<SchoolActivityDTO> result = schoolActivityService.partialUpdate(schoolActivityDTO);
        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, schoolActivityDTO.getId().toString())
        );
    }

    @GetMapping("")
    public ResponseEntity<List<SchoolActivityDTO>> getAllSchoolActivitys(
        SchoolActivityCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get SchoolActivitys by criteria: {}", criteria);
        Page<SchoolActivityDTO> page = schoolActivityQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countSchoolActivitys(SchoolActivityCriteria criteria) {
        LOG.debug("REST request to count SchoolActivitys by criteria: {}", criteria);
        return ResponseEntity.ok().body(schoolActivityQueryService.countByCriteria(criteria));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SchoolActivityDTO> getSchoolActivity(@PathVariable("id") Long id) {
        LOG.debug("REST request to get SchoolActivity : {}", id);
        Optional<SchoolActivityDTO> dto = schoolActivityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchoolActivity(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete SchoolActivity : {}", id);
        schoolActivityService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
