package com.adwiiseagent.web.rest;

import com.adwiiseagent.errors.BadRequestAlertException;
import com.adwiiseagent.repository.SchoolFeePaymentRepository;
import com.adwiiseagent.service.SchoolFeePaymentQueryService;
import com.adwiiseagent.service.SchoolFeePaymentService;
import com.adwiiseagent.service.criteria.SchoolFeePaymentCriteria;
import com.adwiiseagent.service.dto.SchoolFeePaymentDTO;
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
@RequestMapping("/api/school-fee-payments")
public class SchoolFeePaymentResource {

    private static final Logger LOG = LoggerFactory.getLogger(SchoolFeePaymentResource.class);

    private static final String ENTITY_NAME = "schoolFeePayment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SchoolFeePaymentService schoolFeePaymentService;

    private final SchoolFeePaymentRepository schoolFeePaymentRepository;

    private final SchoolFeePaymentQueryService schoolFeePaymentQueryService;

    public SchoolFeePaymentResource(
        SchoolFeePaymentService schoolFeePaymentService,
        SchoolFeePaymentRepository schoolFeePaymentRepository,
        SchoolFeePaymentQueryService schoolFeePaymentQueryService
    ) {
        this.schoolFeePaymentService = schoolFeePaymentService;
        this.schoolFeePaymentRepository = schoolFeePaymentRepository;
        this.schoolFeePaymentQueryService = schoolFeePaymentQueryService;
    }

    @PostMapping("")
    public ResponseEntity<SchoolFeePaymentDTO> createSchoolFeePayment(@Valid @RequestBody SchoolFeePaymentDTO schoolFeePaymentDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save SchoolFeePayment : {}", schoolFeePaymentDTO);
        if (schoolFeePaymentDTO.getId() != null) {
            throw new BadRequestAlertException("A new schoolFeePayment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (schoolFeePaymentDTO.getTenantId() == null || schoolFeePaymentDTO.getTenantId().isBlank()) {
            throw new BadRequestAlertException("tenantId is required", ENTITY_NAME, "tenantidrequired");
        }
        schoolFeePaymentDTO = schoolFeePaymentService.save(schoolFeePaymentDTO);
        return ResponseEntity
            .created(new URI("/api/school-fee-payments/" + schoolFeePaymentDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, schoolFeePaymentDTO.getId().toString()))
            .body(schoolFeePaymentDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SchoolFeePaymentDTO> updateSchoolFeePayment(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SchoolFeePaymentDTO schoolFeePaymentDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update SchoolFeePayment : {}, {}", id, schoolFeePaymentDTO);
        if (schoolFeePaymentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, schoolFeePaymentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!schoolFeePaymentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        schoolFeePaymentDTO = schoolFeePaymentService.update(schoolFeePaymentDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, schoolFeePaymentDTO.getId().toString()))
            .body(schoolFeePaymentDTO);
    }

    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SchoolFeePaymentDTO> partialUpdateSchoolFeePayment(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SchoolFeePaymentDTO schoolFeePaymentDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update SchoolFeePayment : {}, {}", id, schoolFeePaymentDTO);
        if (schoolFeePaymentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, schoolFeePaymentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!schoolFeePaymentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        Optional<SchoolFeePaymentDTO> result = schoolFeePaymentService.partialUpdate(schoolFeePaymentDTO);
        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, schoolFeePaymentDTO.getId().toString())
        );
    }

    @GetMapping("")
    public ResponseEntity<List<SchoolFeePaymentDTO>> getAllSchoolFeePayments(
        SchoolFeePaymentCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get SchoolFeePayments by criteria: {}", criteria);
        Page<SchoolFeePaymentDTO> page = schoolFeePaymentQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countSchoolFeePayments(SchoolFeePaymentCriteria criteria) {
        LOG.debug("REST request to count SchoolFeePayments by criteria: {}", criteria);
        return ResponseEntity.ok().body(schoolFeePaymentQueryService.countByCriteria(criteria));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SchoolFeePaymentDTO> getSchoolFeePayment(@PathVariable("id") Long id) {
        LOG.debug("REST request to get SchoolFeePayment : {}", id);
        Optional<SchoolFeePaymentDTO> dto = schoolFeePaymentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchoolFeePayment(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete SchoolFeePayment : {}", id);
        schoolFeePaymentService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
