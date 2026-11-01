package com.adwiiseagent.web.rest;

import com.adwiiseagent.errors.BadRequestAlertException;
import com.adwiiseagent.repository.RetailerSizeBandRepository;
import com.adwiiseagent.service.RetailerSizeBandService;
import com.adwiiseagent.service.dto.RetailSizeResolutionDTO;
import com.adwiiseagent.service.dto.RetailerSizeBandDTO;
import jakarta.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

@RestController
@RequestMapping("/api/retailer-size-bands")
public class RetailerSizeBandResource {

    private static final Logger LOG = LoggerFactory.getLogger(RetailerSizeBandResource.class);
    private static final String ENTITY_NAME = "retailerSizeBand";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RetailerSizeBandService service;
    private final RetailerSizeBandRepository repository;

    public RetailerSizeBandResource(RetailerSizeBandService service, RetailerSizeBandRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @PostMapping("")
    public ResponseEntity<RetailerSizeBandDTO> create(@Valid @RequestBody RetailerSizeBandDTO dto) throws URISyntaxException {
        LOG.debug("REST request to save RetailerSizeBand : {}", dto);
        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new size band cannot already have an ID", ENTITY_NAME, "idexists");
        }
        dto = service.save(dto);
        return ResponseEntity
            .created(new URI("/api/retailer-size-bands/" + dto.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, dto.getId().toString()))
            .body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RetailerSizeBandDTO> update(@PathVariable("id") Long id, @Valid @RequestBody RetailerSizeBandDTO dto) {
        if (dto.getId() == null || !Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idinvalid");
        }
        if (!repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        dto = service.update(dto);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dto.getId().toString()))
            .body(dto);
    }

    @GetMapping("")
    public List<RetailerSizeBandDTO> getAll() {
        return service.findAll();
    }

    @GetMapping("/resolve")
    public RetailSizeResolutionDTO resolve(
        @RequestParam(name = "tenantId", required = false) String tenantId,
        @RequestParam(name = "retailerType") String retailerType
    ) {
        return service.resolve(tenantId, retailerType);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RetailerSizeBandDTO> get(@PathVariable("id") Long id) {
        return ResponseUtil.wrapOrNotFound(service.findOne(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
