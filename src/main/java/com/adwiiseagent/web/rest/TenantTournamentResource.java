package com.adwiiseagent.web.rest;

import com.adwiiseagent.errors.BadRequestAlertException;
import com.adwiiseagent.repository.TenantTournamentRepository;
import com.adwiiseagent.service.TenantTournamentQueryService;
import com.adwiiseagent.service.TenantTournamentService;
import com.adwiiseagent.service.criteria.TenantTournamentCriteria;
import com.adwiiseagent.service.dto.TenantTournamentDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

@RestController
@RequestMapping("/api/tenant-tournaments")
public class TenantTournamentResource {

    private final Logger log = LoggerFactory.getLogger(TenantTournamentResource.class);
    private static final String ENTITY_NAME = "tenantTournament";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TenantTournamentService tenantTournamentService;
    private final TenantTournamentRepository tenantTournamentRepository;
    private final TenantTournamentQueryService tenantTournamentQueryService;

    public TenantTournamentResource(
        TenantTournamentService tenantTournamentService,
        TenantTournamentRepository tenantTournamentRepository,
        TenantTournamentQueryService tenantTournamentQueryService
    ) {
        this.tenantTournamentService = tenantTournamentService;
        this.tenantTournamentRepository = tenantTournamentRepository;
        this.tenantTournamentQueryService = tenantTournamentQueryService;
    }

    @PostMapping("")
    public ResponseEntity<TenantTournamentDTO> createTenantTournament(@Valid @RequestBody TenantTournamentDTO dto)
        throws URISyntaxException {
        log.debug("REST request to save TenantTournament : {}", dto);
        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new tenantTournament cannot already have an ID", ENTITY_NAME, "idexists");
        }
        try {
            TenantTournamentDTO result = tenantTournamentService.save(dto);
            return ResponseEntity
                .created(new URI("/api/tenant-tournaments/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
        } catch (IllegalArgumentException e) {
            throw new BadRequestAlertException(e.getMessage(), ENTITY_NAME, "validationfailed");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TenantTournamentDTO> updateTenantTournament(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TenantTournamentDTO dto
    ) {
        log.debug("REST request to update TenantTournament : {}, {}", id, dto);
        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!tenantTournamentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        try {
            TenantTournamentDTO result = tenantTournamentService.update(dto);
            return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dto.getId().toString()))
                .body(result);
        } catch (IllegalArgumentException e) {
            throw new BadRequestAlertException(e.getMessage(), ENTITY_NAME, "validationfailed");
        }
    }

    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TenantTournamentDTO> partialUpdateTenantTournament(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TenantTournamentDTO dto
    ) {
        log.debug("REST request to partial update TenantTournament : {}, {}", id, dto);
        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!tenantTournamentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        try {
            Optional<TenantTournamentDTO> result = tenantTournamentService.partialUpdate(dto);
            return ResponseUtil.wrapOrNotFound(
                result,
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dto.getId().toString())
            );
        } catch (IllegalArgumentException e) {
            throw new BadRequestAlertException(e.getMessage(), ENTITY_NAME, "validationfailed");
        }
    }

    @GetMapping("")
    public ResponseEntity<List<TenantTournamentDTO>> getAllTenantTournaments(
        TenantTournamentCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        Page<TenantTournamentDTO> page = tenantTournamentQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countTenantTournaments(TenantTournamentCriteria criteria) {
        return ResponseEntity.ok().body(tenantTournamentQueryService.countByCriteria(criteria));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TenantTournamentDTO> getTenantTournament(@PathVariable Long id) {
        return ResponseUtil.wrapOrNotFound(tenantTournamentService.findOne(id));
    }

    @GetMapping("/by-slug/{slug}")
    public ResponseEntity<TenantTournamentDTO> getTenantTournamentBySlug(@RequestParam String tenantId, @PathVariable String slug) {
        return ResponseUtil.wrapOrNotFound(tenantTournamentService.findByTenantIdAndSlug(tenantId, slug));
    }

    /**
     * {@code POST  /tenant-tournaments/upload/image} : store a tournament image and return its URL.
     * The admin form writes that URL onto hero, fixtures, or chief-guest image columns when the tournament is saved.
     */
    @PostMapping(value = "/upload/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, String>> uploadTournamentImage(
        @RequestParam("file") MultipartFile file,
        @RequestParam("kind") String kind,
        @RequestParam(value = "tenantId", required = false) String tenantId
    ) {
        log.debug("REST request to upload tenant tournament image kind={}", kind);
        String finalTenantId = tenantId;
        if (finalTenantId == null || finalTenantId.isBlank()) {
            try {
                finalTenantId = com.adwiiseagent.security.TenantContext.getCurrentTenant();
            } catch (Exception e) {
                log.warn("Could not get tenant ID from context: {}", e.getMessage());
            }
        }
        if (finalTenantId == null || finalTenantId.isBlank()) {
            throw new BadRequestAlertException("Tenant ID is required", ENTITY_NAME, "tenantIdRequired");
        }
        try {
            String imageUrl = tenantTournamentService.uploadImage(finalTenantId, kind, file);
            return ResponseEntity.ok(Map.of("url", imageUrl, "imageUrl", imageUrl));
        } catch (IllegalArgumentException e) {
            throw new BadRequestAlertException(e.getMessage(), ENTITY_NAME, "invalidRequest");
        } catch (Exception e) {
            log.error("Failed to upload tournament image", e);
            throw new BadRequestAlertException("Failed to upload tournament image: " + e.getMessage(), ENTITY_NAME, "uploadFailed");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTenantTournament(@PathVariable Long id) {
        tenantTournamentService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
