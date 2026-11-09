package com.adwiiseagent.web.rest;

import com.adwiiseagent.errors.BadRequestAlertException;
import com.adwiiseagent.repository.SchoolPageRepository;
import com.adwiiseagent.service.SchoolPageQueryService;
import com.adwiiseagent.service.SchoolPageService;
import com.adwiiseagent.service.criteria.SchoolPageCriteria;
import com.adwiiseagent.service.dto.SchoolPageDTO;
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
@RequestMapping("/api/school-pages")
public class SchoolPageResource {

    private static final Logger LOG = LoggerFactory.getLogger(SchoolPageResource.class);

    private static final String ENTITY_NAME = "schoolPage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SchoolPageService schoolPageService;

    private final SchoolPageRepository schoolPageRepository;

    private final SchoolPageQueryService schoolPageQueryService;

    public SchoolPageResource(
        SchoolPageService schoolPageService,
        SchoolPageRepository schoolPageRepository,
        SchoolPageQueryService schoolPageQueryService
    ) {
        this.schoolPageService = schoolPageService;
        this.schoolPageRepository = schoolPageRepository;
        this.schoolPageQueryService = schoolPageQueryService;
    }

    @PostMapping("")
    public ResponseEntity<SchoolPageDTO> createSchoolPage(@Valid @RequestBody SchoolPageDTO schoolPageDTO) throws URISyntaxException {
        LOG.debug("REST request to save SchoolPage : {}", schoolPageDTO);
        if (schoolPageDTO.getId() != null) {
            throw new BadRequestAlertException("A new schoolPage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (schoolPageDTO.getTenantId() == null || schoolPageDTO.getTenantId().isBlank()) {
            throw new BadRequestAlertException("tenantId is required", ENTITY_NAME, "tenantidrequired");
        }
        schoolPageDTO = schoolPageService.save(schoolPageDTO);
        return ResponseEntity
            .created(new URI("/api/school-pages/" + schoolPageDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, schoolPageDTO.getId().toString()))
            .body(schoolPageDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SchoolPageDTO> updateSchoolPage(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SchoolPageDTO schoolPageDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update SchoolPage : {}, {}", id, schoolPageDTO);
        if (schoolPageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, schoolPageDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!schoolPageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        schoolPageDTO = schoolPageService.update(schoolPageDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, schoolPageDTO.getId().toString()))
            .body(schoolPageDTO);
    }

    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SchoolPageDTO> partialUpdateSchoolPage(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SchoolPageDTO schoolPageDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update SchoolPage : {}, {}", id, schoolPageDTO);
        if (schoolPageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, schoolPageDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!schoolPageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        Optional<SchoolPageDTO> result = schoolPageService.partialUpdate(schoolPageDTO);
        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, schoolPageDTO.getId().toString())
        );
    }

    @GetMapping("")
    public ResponseEntity<List<SchoolPageDTO>> getAllSchoolPages(
        SchoolPageCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get SchoolPages by criteria: {}", criteria);
        Page<SchoolPageDTO> page = schoolPageQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countSchoolPages(SchoolPageCriteria criteria) {
        LOG.debug("REST request to count SchoolPages by criteria: {}", criteria);
        return ResponseEntity.ok().body(schoolPageQueryService.countByCriteria(criteria));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SchoolPageDTO> getSchoolPage(@PathVariable("id") Long id) {
        LOG.debug("REST request to get SchoolPage : {}", id);
        Optional<SchoolPageDTO> dto = schoolPageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchoolPage(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete SchoolPage : {}", id);
        schoolPageService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
