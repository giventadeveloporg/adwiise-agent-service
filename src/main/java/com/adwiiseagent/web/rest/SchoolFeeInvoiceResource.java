package com.adwiiseagent.web.rest;

import com.adwiiseagent.errors.BadRequestAlertException;
import com.adwiiseagent.repository.SchoolFeeInvoiceRepository;
import com.adwiiseagent.service.SchoolFeeInvoiceQueryService;
import com.adwiiseagent.service.SchoolFeeInvoiceService;
import com.adwiiseagent.service.criteria.SchoolFeeInvoiceCriteria;
import com.adwiiseagent.service.dto.SchoolFeeInvoiceDTO;
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
@RequestMapping("/api/school-fee-invoices")
public class SchoolFeeInvoiceResource {

    private static final Logger LOG = LoggerFactory.getLogger(SchoolFeeInvoiceResource.class);

    private static final String ENTITY_NAME = "schoolFeeInvoice";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SchoolFeeInvoiceService schoolFeeInvoiceService;

    private final SchoolFeeInvoiceRepository schoolFeeInvoiceRepository;

    private final SchoolFeeInvoiceQueryService schoolFeeInvoiceQueryService;

    public SchoolFeeInvoiceResource(
        SchoolFeeInvoiceService schoolFeeInvoiceService,
        SchoolFeeInvoiceRepository schoolFeeInvoiceRepository,
        SchoolFeeInvoiceQueryService schoolFeeInvoiceQueryService
    ) {
        this.schoolFeeInvoiceService = schoolFeeInvoiceService;
        this.schoolFeeInvoiceRepository = schoolFeeInvoiceRepository;
        this.schoolFeeInvoiceQueryService = schoolFeeInvoiceQueryService;
    }

    @PostMapping("")
    public ResponseEntity<SchoolFeeInvoiceDTO> createSchoolFeeInvoice(@Valid @RequestBody SchoolFeeInvoiceDTO schoolFeeInvoiceDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save SchoolFeeInvoice : {}", schoolFeeInvoiceDTO);
        if (schoolFeeInvoiceDTO.getId() != null) {
            throw new BadRequestAlertException("A new schoolFeeInvoice cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (schoolFeeInvoiceDTO.getTenantId() == null || schoolFeeInvoiceDTO.getTenantId().isBlank()) {
            throw new BadRequestAlertException("tenantId is required", ENTITY_NAME, "tenantidrequired");
        }
        schoolFeeInvoiceDTO = schoolFeeInvoiceService.save(schoolFeeInvoiceDTO);
        return ResponseEntity
            .created(new URI("/api/school-fee-invoices/" + schoolFeeInvoiceDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, schoolFeeInvoiceDTO.getId().toString()))
            .body(schoolFeeInvoiceDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SchoolFeeInvoiceDTO> updateSchoolFeeInvoice(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SchoolFeeInvoiceDTO schoolFeeInvoiceDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update SchoolFeeInvoice : {}, {}", id, schoolFeeInvoiceDTO);
        if (schoolFeeInvoiceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, schoolFeeInvoiceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!schoolFeeInvoiceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        schoolFeeInvoiceDTO = schoolFeeInvoiceService.update(schoolFeeInvoiceDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, schoolFeeInvoiceDTO.getId().toString()))
            .body(schoolFeeInvoiceDTO);
    }

    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SchoolFeeInvoiceDTO> partialUpdateSchoolFeeInvoice(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SchoolFeeInvoiceDTO schoolFeeInvoiceDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update SchoolFeeInvoice : {}, {}", id, schoolFeeInvoiceDTO);
        if (schoolFeeInvoiceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, schoolFeeInvoiceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!schoolFeeInvoiceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        Optional<SchoolFeeInvoiceDTO> result = schoolFeeInvoiceService.partialUpdate(schoolFeeInvoiceDTO);
        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, schoolFeeInvoiceDTO.getId().toString())
        );
    }

    @GetMapping("")
    public ResponseEntity<List<SchoolFeeInvoiceDTO>> getAllSchoolFeeInvoices(
        SchoolFeeInvoiceCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get SchoolFeeInvoices by criteria: {}", criteria);
        Page<SchoolFeeInvoiceDTO> page = schoolFeeInvoiceQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countSchoolFeeInvoices(SchoolFeeInvoiceCriteria criteria) {
        LOG.debug("REST request to count SchoolFeeInvoices by criteria: {}", criteria);
        return ResponseEntity.ok().body(schoolFeeInvoiceQueryService.countByCriteria(criteria));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SchoolFeeInvoiceDTO> getSchoolFeeInvoice(@PathVariable("id") Long id) {
        LOG.debug("REST request to get SchoolFeeInvoice : {}", id);
        Optional<SchoolFeeInvoiceDTO> dto = schoolFeeInvoiceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchoolFeeInvoice(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete SchoolFeeInvoice : {}", id);
        schoolFeeInvoiceService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
