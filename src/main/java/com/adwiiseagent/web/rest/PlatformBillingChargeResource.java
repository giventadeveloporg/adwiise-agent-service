package com.adwiiseagent.web.rest;

import com.adwiiseagent.errors.BadRequestAlertException;
import com.adwiiseagent.repository.PlatformBillingChargeRepository;
import com.adwiiseagent.service.PlatformBillingChargeQueryService;
import com.adwiiseagent.service.PlatformBillingChargeService;
import com.adwiiseagent.service.criteria.PlatformBillingChargeCriteria;
import com.adwiiseagent.service.dto.PlatformBillingChargeDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
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
@RequestMapping("/api/platform-billing-charges")
public class PlatformBillingChargeResource {

    private static final String ENTITY_NAME = "platformBillingCharge";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlatformBillingChargeService service;
    private final PlatformBillingChargeQueryService queryService;
    private final PlatformBillingChargeRepository repository;

    public PlatformBillingChargeResource(
        PlatformBillingChargeService service,
        PlatformBillingChargeQueryService queryService,
        PlatformBillingChargeRepository repository
    ) {
        this.service = service;
        this.queryService = queryService;
        this.repository = repository;
    }

    @PostMapping("")
    public ResponseEntity<PlatformBillingChargeDTO> create(@Valid @RequestBody PlatformBillingChargeDTO dto) throws URISyntaxException {
        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new charge cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlatformBillingChargeDTO result = service.save(dto);
        return ResponseEntity
            .created(new URI("/api/platform-billing-charges/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlatformBillingChargeDTO> update(@PathVariable Long id, @Valid @RequestBody PlatformBillingChargeDTO dto) {
        if (dto.getId() == null || !Objects.equals(id, dto.getId()) || !repository.existsById(id)) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        PlatformBillingChargeDTO result = service.update(dto);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .body(result);
    }

    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PlatformBillingChargeDTO> partialUpdate(
        @PathVariable Long id,
        @NotNull @RequestBody PlatformBillingChargeDTO dto
    ) {
        if (dto.getId() == null) dto.setId(id);
        if (!Objects.equals(id, dto.getId()) || !repository.existsById(id)) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        Optional<PlatformBillingChargeDTO> result = service.partialUpdate(dto);
        return ResponseUtil.wrapOrNotFound(result, HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, id.toString()));
    }

    @GetMapping("")
    public ResponseEntity<List<PlatformBillingChargeDTO>> getAll(
        PlatformBillingChargeCriteria criteria,
        @ParameterObject Pageable pageable
    ) {
        Page<PlatformBillingChargeDTO> page = queryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlatformBillingChargeDTO> get(@PathVariable Long id) {
        return ResponseUtil.wrapOrNotFound(service.findOne(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
