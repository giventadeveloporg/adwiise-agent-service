package com.adwiiseagent.web.rest;

import com.adwiiseagent.errors.BadRequestAlertException;
import com.adwiiseagent.repository.SchoolDepartmentRepository;
import com.adwiiseagent.service.SchoolDepartmentQueryService;
import com.adwiiseagent.service.SchoolDepartmentService;
import com.adwiiseagent.service.criteria.SchoolDepartmentCriteria;
import com.adwiiseagent.service.dto.SchoolDepartmentDTO;
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
@RequestMapping("/api/school-departments")
public class SchoolDepartmentResource {

    private static final Logger LOG = LoggerFactory.getLogger(SchoolDepartmentResource.class);

    private static final String ENTITY_NAME = "schoolDepartment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SchoolDepartmentService schoolDepartmentService;

    private final SchoolDepartmentRepository schoolDepartmentRepository;

    private final SchoolDepartmentQueryService schoolDepartmentQueryService;

    public SchoolDepartmentResource(
        SchoolDepartmentService schoolDepartmentService,
        SchoolDepartmentRepository schoolDepartmentRepository,
        SchoolDepartmentQueryService schoolDepartmentQueryService
    ) {
        this.schoolDepartmentService = schoolDepartmentService;
        this.schoolDepartmentRepository = schoolDepartmentRepository;
        this.schoolDepartmentQueryService = schoolDepartmentQueryService;
    }

    @PostMapping("")
    public ResponseEntity<SchoolDepartmentDTO> createSchoolDepartment(@Valid @RequestBody SchoolDepartmentDTO schoolDepartmentDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save SchoolDepartment : {}", schoolDepartmentDTO);
        if (schoolDepartmentDTO.getId() != null) {
            throw new BadRequestAlertException("A new schoolDepartment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (schoolDepartmentDTO.getTenantId() == null || schoolDepartmentDTO.getTenantId().isBlank()) {
            throw new BadRequestAlertException("tenantId is required", ENTITY_NAME, "tenantidrequired");
        }
        schoolDepartmentDTO = schoolDepartmentService.save(schoolDepartmentDTO);
        return ResponseEntity
            .created(new URI("/api/school-departments/" + schoolDepartmentDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, schoolDepartmentDTO.getId().toString()))
            .body(schoolDepartmentDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SchoolDepartmentDTO> updateSchoolDepartment(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SchoolDepartmentDTO schoolDepartmentDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update SchoolDepartment : {}, {}", id, schoolDepartmentDTO);
        if (schoolDepartmentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, schoolDepartmentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!schoolDepartmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        schoolDepartmentDTO = schoolDepartmentService.update(schoolDepartmentDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, schoolDepartmentDTO.getId().toString()))
            .body(schoolDepartmentDTO);
    }

    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SchoolDepartmentDTO> partialUpdateSchoolDepartment(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SchoolDepartmentDTO schoolDepartmentDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update SchoolDepartment : {}, {}", id, schoolDepartmentDTO);
        if (schoolDepartmentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, schoolDepartmentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!schoolDepartmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        Optional<SchoolDepartmentDTO> result = schoolDepartmentService.partialUpdate(schoolDepartmentDTO);
        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, schoolDepartmentDTO.getId().toString())
        );
    }

    @GetMapping("")
    public ResponseEntity<List<SchoolDepartmentDTO>> getAllSchoolDepartments(
        SchoolDepartmentCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get SchoolDepartments by criteria: {}", criteria);
        Page<SchoolDepartmentDTO> page = schoolDepartmentQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countSchoolDepartments(SchoolDepartmentCriteria criteria) {
        LOG.debug("REST request to count SchoolDepartments by criteria: {}", criteria);
        return ResponseEntity.ok().body(schoolDepartmentQueryService.countByCriteria(criteria));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SchoolDepartmentDTO> getSchoolDepartment(@PathVariable("id") Long id) {
        LOG.debug("REST request to get SchoolDepartment : {}", id);
        Optional<SchoolDepartmentDTO> dto = schoolDepartmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchoolDepartment(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete SchoolDepartment : {}", id);
        schoolDepartmentService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
