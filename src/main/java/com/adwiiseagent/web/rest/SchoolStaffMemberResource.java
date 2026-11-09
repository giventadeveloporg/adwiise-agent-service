package com.adwiiseagent.web.rest;

import com.adwiiseagent.errors.BadRequestAlertException;
import com.adwiiseagent.repository.SchoolStaffMemberRepository;
import com.adwiiseagent.service.SchoolStaffMemberQueryService;
import com.adwiiseagent.service.SchoolStaffMemberService;
import com.adwiiseagent.service.criteria.SchoolStaffMemberCriteria;
import com.adwiiseagent.service.dto.SchoolStaffMemberDTO;
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
@RequestMapping("/api/school-staff-members")
public class SchoolStaffMemberResource {

    private static final Logger LOG = LoggerFactory.getLogger(SchoolStaffMemberResource.class);

    private static final String ENTITY_NAME = "schoolStaffMember";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SchoolStaffMemberService schoolStaffMemberService;

    private final SchoolStaffMemberRepository schoolStaffMemberRepository;

    private final SchoolStaffMemberQueryService schoolStaffMemberQueryService;

    public SchoolStaffMemberResource(
        SchoolStaffMemberService schoolStaffMemberService,
        SchoolStaffMemberRepository schoolStaffMemberRepository,
        SchoolStaffMemberQueryService schoolStaffMemberQueryService
    ) {
        this.schoolStaffMemberService = schoolStaffMemberService;
        this.schoolStaffMemberRepository = schoolStaffMemberRepository;
        this.schoolStaffMemberQueryService = schoolStaffMemberQueryService;
    }

    @PostMapping("")
    public ResponseEntity<SchoolStaffMemberDTO> createSchoolStaffMember(@Valid @RequestBody SchoolStaffMemberDTO schoolStaffMemberDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save SchoolStaffMember : {}", schoolStaffMemberDTO);
        if (schoolStaffMemberDTO.getId() != null) {
            throw new BadRequestAlertException("A new schoolStaffMember cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (schoolStaffMemberDTO.getTenantId() == null || schoolStaffMemberDTO.getTenantId().isBlank()) {
            throw new BadRequestAlertException("tenantId is required", ENTITY_NAME, "tenantidrequired");
        }
        schoolStaffMemberDTO = schoolStaffMemberService.save(schoolStaffMemberDTO);
        return ResponseEntity
            .created(new URI("/api/school-staff-members/" + schoolStaffMemberDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, schoolStaffMemberDTO.getId().toString()))
            .body(schoolStaffMemberDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SchoolStaffMemberDTO> updateSchoolStaffMember(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SchoolStaffMemberDTO schoolStaffMemberDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update SchoolStaffMember : {}, {}", id, schoolStaffMemberDTO);
        if (schoolStaffMemberDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, schoolStaffMemberDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!schoolStaffMemberRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        schoolStaffMemberDTO = schoolStaffMemberService.update(schoolStaffMemberDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, schoolStaffMemberDTO.getId().toString()))
            .body(schoolStaffMemberDTO);
    }

    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SchoolStaffMemberDTO> partialUpdateSchoolStaffMember(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SchoolStaffMemberDTO schoolStaffMemberDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update SchoolStaffMember : {}, {}", id, schoolStaffMemberDTO);
        if (schoolStaffMemberDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, schoolStaffMemberDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!schoolStaffMemberRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        Optional<SchoolStaffMemberDTO> result = schoolStaffMemberService.partialUpdate(schoolStaffMemberDTO);
        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, schoolStaffMemberDTO.getId().toString())
        );
    }

    @GetMapping("")
    public ResponseEntity<List<SchoolStaffMemberDTO>> getAllSchoolStaffMembers(
        SchoolStaffMemberCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get SchoolStaffMembers by criteria: {}", criteria);
        Page<SchoolStaffMemberDTO> page = schoolStaffMemberQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countSchoolStaffMembers(SchoolStaffMemberCriteria criteria) {
        LOG.debug("REST request to count SchoolStaffMembers by criteria: {}", criteria);
        return ResponseEntity.ok().body(schoolStaffMemberQueryService.countByCriteria(criteria));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SchoolStaffMemberDTO> getSchoolStaffMember(@PathVariable("id") Long id) {
        LOG.debug("REST request to get SchoolStaffMember : {}", id);
        Optional<SchoolStaffMemberDTO> dto = schoolStaffMemberService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchoolStaffMember(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete SchoolStaffMember : {}", id);
        schoolStaffMemberService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
