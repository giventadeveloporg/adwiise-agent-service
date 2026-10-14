package com.adwiiseagent.web.rest;

import com.adwiiseagent.errors.BadRequestAlertException;
import com.adwiiseagent.repository.TenantProductBillingRepository;
import com.adwiiseagent.service.TenantProductBillingQueryService;
import com.adwiiseagent.service.TenantProductBillingService;
import com.adwiiseagent.service.criteria.TenantProductBillingCriteria;
import com.adwiiseagent.service.dto.TenantProductBillingDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
@RequestMapping("/api/tenant-product-billings")
public class TenantProductBillingResource {

    private static final String ENTITY_NAME = "tenantProductBilling";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TenantProductBillingService service;
    private final TenantProductBillingQueryService queryService;
    private final TenantProductBillingRepository repository;

    public TenantProductBillingResource(
        TenantProductBillingService service,
        TenantProductBillingQueryService queryService,
        TenantProductBillingRepository repository
    ) {
        this.service = service;
        this.queryService = queryService;
        this.repository = repository;
    }

    @PostMapping("")
    public ResponseEntity<TenantProductBillingDTO> create(@Valid @RequestBody TenantProductBillingDTO dto) throws URISyntaxException {
        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new billing row cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (dto.getTenantId() == null || dto.getTenantId().isBlank()) {
            throw new BadRequestAlertException("tenantId is required", ENTITY_NAME, "tenantidrequired");
        }
        TenantProductBillingDTO result = service.save(dto);
        return ResponseEntity
            .created(new URI("/api/tenant-product-billings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TenantProductBillingDTO> update(@PathVariable Long id, @Valid @RequestBody TenantProductBillingDTO dto) {
        if (dto.getId() == null || !Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        TenantProductBillingDTO result = service.update(dto);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .body(result);
    }

    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TenantProductBillingDTO> partialUpdate(@PathVariable Long id, @NotNull @RequestBody TenantProductBillingDTO dto) {
        if (dto.getId() == null) {
            dto.setId(id);
        }
        if (!Objects.equals(id, dto.getId()) || !repository.existsById(id)) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        Optional<TenantProductBillingDTO> result = service.partialUpdate(dto);
        return ResponseUtil.wrapOrNotFound(result, HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, id.toString()));
    }

    @GetMapping("")
    public ResponseEntity<List<TenantProductBillingDTO>> getAll(
        TenantProductBillingCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        Page<TenantProductBillingDTO> page = queryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TenantProductBillingDTO> get(@PathVariable Long id) {
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
