package com.adwiiseagent.web.rest;

import com.adwiiseagent.errors.BadRequestAlertException;
import com.adwiiseagent.repository.OrganizationPortfolioRepository;
import com.adwiiseagent.service.OrganizationPortfolioMemberQueryService;
import com.adwiiseagent.service.OrganizationPortfolioQueryService;
import com.adwiiseagent.service.OrganizationPortfolioService;
import com.adwiiseagent.service.criteria.OrganizationPortfolioCriteria;
import com.adwiiseagent.service.criteria.OrganizationPortfolioMemberCriteria;
import com.adwiiseagent.service.dto.OrganizationPortfolioDTO;
import com.adwiiseagent.service.dto.OrganizationPortfolioMemberDTO;
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
@RequestMapping("/api")
public class OrganizationPortfolioResource {

    private final Logger log = LoggerFactory.getLogger(OrganizationPortfolioResource.class);
    private static final String ENTITY_NAME = "organizationPortfolio";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrganizationPortfolioService organizationPortfolioService;
    private final OrganizationPortfolioRepository organizationPortfolioRepository;
    private final OrganizationPortfolioQueryService organizationPortfolioQueryService;
    private final OrganizationPortfolioMemberQueryService organizationPortfolioMemberQueryService;

    public OrganizationPortfolioResource(
        OrganizationPortfolioService organizationPortfolioService,
        OrganizationPortfolioRepository organizationPortfolioRepository,
        OrganizationPortfolioQueryService organizationPortfolioQueryService,
        OrganizationPortfolioMemberQueryService organizationPortfolioMemberQueryService
    ) {
        this.organizationPortfolioService = organizationPortfolioService;
        this.organizationPortfolioRepository = organizationPortfolioRepository;
        this.organizationPortfolioQueryService = organizationPortfolioQueryService;
        this.organizationPortfolioMemberQueryService = organizationPortfolioMemberQueryService;
    }

    @PostMapping("/organization-portfolios")
    public ResponseEntity<OrganizationPortfolioDTO> create(@Valid @RequestBody OrganizationPortfolioDTO dto) throws URISyntaxException {
        log.debug("REST request to save OrganizationPortfolio : {}", dto);
        OrganizationPortfolioDTO result = organizationPortfolioService.save(dto);
        return ResponseEntity
            .created(new URI("/api/organization-portfolios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/organization-portfolios/{id}")
    public ResponseEntity<OrganizationPortfolioDTO> update(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody OrganizationPortfolioDTO dto
    ) {
        log.debug("REST request to update OrganizationPortfolio : {}, {}", id, dto);
        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!organizationPortfolioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        OrganizationPortfolioDTO result = organizationPortfolioService.update(dto);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dto.getId().toString()))
            .body(result);
    }

    @PatchMapping(value = "/organization-portfolios/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OrganizationPortfolioDTO> partialUpdate(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody OrganizationPortfolioDTO dto
    ) {
        log.debug("REST request to partial update OrganizationPortfolio : {}, {}", id, dto);
        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!organizationPortfolioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        Optional<OrganizationPortfolioDTO> result = organizationPortfolioService.partialUpdate(dto);
        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dto.getId().toString())
        );
    }

    @GetMapping("/organization-portfolios")
    public ResponseEntity<List<OrganizationPortfolioDTO>> getAll(
        OrganizationPortfolioCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        Page<OrganizationPortfolioDTO> page = organizationPortfolioQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/organization-portfolios/count")
    public ResponseEntity<Long> count(OrganizationPortfolioCriteria criteria) {
        return ResponseEntity.ok(organizationPortfolioQueryService.countByCriteria(criteria));
    }

    /**
     * Tenant ids a primary-tenant admin may manage. Inactive portfolios are omitted.
     * Declared before {@code /{id}} so the path is not parsed as an id.
     */
    @GetMapping("/organization-portfolios/member-tenant-ids")
    public ResponseEntity<List<String>> memberTenantIds(
        @RequestParam(name = "primaryTenantId", required = false) List<String> primaryTenantIds
    ) {
        return ResponseEntity.ok(organizationPortfolioService.findMemberTenantIdsForPrimaries(primaryTenantIds));
    }

    @GetMapping("/organization-portfolios/{id}")
    public ResponseEntity<OrganizationPortfolioDTO> getOne(@PathVariable Long id) {
        return ResponseUtil.wrapOrNotFound(organizationPortfolioService.findOne(id));
    }

    @DeleteMapping("/organization-portfolios/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete OrganizationPortfolio : {}", id);
        organizationPortfolioService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @GetMapping("/organization-portfolios/{id}/members")
    public ResponseEntity<List<OrganizationPortfolioMemberDTO>> getMembers(@PathVariable Long id) {
        return ResponseEntity.ok(organizationPortfolioService.findMembers(id));
    }

    @PostMapping("/organization-portfolios/{id}/members")
    public ResponseEntity<OrganizationPortfolioMemberDTO> addMember(
        @PathVariable Long id,
        @Valid @RequestBody OrganizationPortfolioMemberDTO dto
    ) {
        boolean asPrimary = Boolean.TRUE.equals(dto.getIsPrimary());
        OrganizationPortfolioMemberDTO result = organizationPortfolioService.addMember(id, dto.getTenantId(), asPrimary);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/organization-portfolios/{id}/members/{memberId}")
    public ResponseEntity<Void> removeMember(@PathVariable Long id, @PathVariable Long memberId) {
        organizationPortfolioService.removeMember(id, memberId);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, "organizationPortfolioMember", memberId.toString()))
            .build();
    }

    @GetMapping("/organization-portfolio-members")
    public ResponseEntity<List<OrganizationPortfolioMemberDTO>> getAllMembers(
        OrganizationPortfolioMemberCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        Page<OrganizationPortfolioMemberDTO> page = organizationPortfolioMemberQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
