package com.adwiiseagent.service.impl;

import com.adwiiseagent.domain.TenantOrganization;
import com.adwiiseagent.domain.TenantSettings;
import com.adwiiseagent.errors.BadRequestAlertException;
import com.adwiiseagent.repository.TenantOnboardingRequestRepository;
import com.adwiiseagent.repository.TenantOrganizationRepository;
import com.adwiiseagent.repository.TenantSettingsRepository;
import com.adwiiseagent.service.SatelliteDomainService;
import com.adwiiseagent.service.TenantOrganizationService;
import com.adwiiseagent.service.dto.OrganizationWebsiteDTO;
import com.adwiiseagent.service.dto.OrganizationWebsitesDTO;
import com.adwiiseagent.service.dto.TenantOrganizationDTO;
import com.adwiiseagent.service.mapper.TenantOrganizationMapper;
import com.adwiiseagent.service.validation.TenantOrganizationProfileValidator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * Service Implementation for managing {@link com.adwiiseagent.domain.TenantOrganization}.
 */
@Service
@Transactional
public class TenantOrganizationServiceImpl implements TenantOrganizationService {

    private final Logger log = LoggerFactory.getLogger(TenantOrganizationServiceImpl.class);

    private final TenantOrganizationRepository tenantOrganizationRepository;

    private final TenantSettingsRepository tenantSettingsRepository;

    private final TenantOrganizationMapper tenantOrganizationMapper;

    private final TenantOrganizationProfileValidator tenantOrganizationProfileValidator;

    private final SatelliteDomainService satelliteDomainService;

    private final TenantOnboardingRequestRepository tenantOnboardingRequestRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public TenantOrganizationServiceImpl(
        TenantOrganizationRepository tenantOrganizationRepository,
        TenantSettingsRepository tenantSettingsRepository,
        TenantOrganizationMapper tenantOrganizationMapper,
        TenantOrganizationProfileValidator tenantOrganizationProfileValidator,
        SatelliteDomainService satelliteDomainService,
        TenantOnboardingRequestRepository tenantOnboardingRequestRepository
    ) {
        this.tenantOrganizationRepository = tenantOrganizationRepository;
        this.tenantSettingsRepository = tenantSettingsRepository;
        this.tenantOrganizationMapper = tenantOrganizationMapper;
        this.tenantOrganizationProfileValidator = tenantOrganizationProfileValidator;
        this.satelliteDomainService = satelliteDomainService;
        this.tenantOnboardingRequestRepository = tenantOnboardingRequestRepository;
    }

    @Override
    public TenantOrganizationDTO save(TenantOrganizationDTO tenantOrganizationDTO) {
        log.debug("Request to save TenantOrganization : {}", tenantOrganizationDTO);
        tenantOrganizationProfileValidator.validateAndNormalize(tenantOrganizationDTO);
        requireUniqueOrganizationName(tenantOrganizationDTO);
        requireUniqueDomain(tenantOrganizationDTO);
        TenantOrganization tenantOrganization = tenantOrganizationMapper.toEntity(tenantOrganizationDTO);
        tenantOrganization = tenantOrganizationRepository.save(tenantOrganization);
        return decorate(tenantOrganizationMapper.toDto(tenantOrganization));
    }

    @Override
    public TenantOrganizationDTO update(TenantOrganizationDTO tenantOrganizationDTO) {
        log.debug("Request to update TenantOrganization : {}", tenantOrganizationDTO);
        tenantOrganizationProfileValidator.validateAndNormalize(tenantOrganizationDTO);
        requireUniqueOrganizationName(tenantOrganizationDTO);
        requireUniqueDomain(tenantOrganizationDTO);
        TenantOrganization tenantOrganization = tenantOrganizationMapper.toEntity(tenantOrganizationDTO);
        tenantOrganization = tenantOrganizationRepository.save(tenantOrganization);
        return decorate(tenantOrganizationMapper.toDto(tenantOrganization));
    }

    @Override
    public Optional<TenantOrganizationDTO> partialUpdate(TenantOrganizationDTO tenantOrganizationDTO) {
        log.debug("Request to partially update TenantOrganization : {}", tenantOrganizationDTO);
        tenantOrganizationProfileValidator.validatePresentFields(tenantOrganizationDTO);

        return tenantOrganizationRepository
            .findById(tenantOrganizationDTO.getId())
            .map(existingTenantOrganization -> {
                tenantOrganizationMapper.partialUpdate(existingTenantOrganization, tenantOrganizationDTO);
                tenantOrganizationProfileValidator.applyPartialNullableFields(existingTenantOrganization, tenantOrganizationDTO);
                tenantOrganizationProfileValidator.validateEntityFields(existingTenantOrganization);
                if (existingTenantOrganization.getOrganizationName() != null) {
                    existingTenantOrganization.setOrganizationName(existingTenantOrganization.getOrganizationName().trim());
                }
                requireUniqueOrganizationName(existingTenantOrganization.getOrganizationName(), existingTenantOrganization.getId());
                if (tenantOrganizationDTO.getDomain() != null) {
                    String domain = tenantOrganizationDTO.getDomain().trim();
                    existingTenantOrganization.setDomain(domain.isEmpty() ? null : domain);
                }
                requireUniqueDomain(existingTenantOrganization.getDomain(), existingTenantOrganization.getId());

                return existingTenantOrganization;
            })
            .map(tenantOrganizationRepository::save)
            .map(tenantOrganizationMapper::toDto)
            .map(this::decorate);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TenantOrganizationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TenantOrganizations");
        Page<TenantOrganizationDTO> page = tenantOrganizationRepository.findAll(pageable).map(tenantOrganizationMapper::toDto);
        decorate(page.getContent());
        return page;
    }

    /**
     *  Get all the tenantOrganizations where TenantSettings is {@code null}.
     *  @return the list of entities.
     */
    /* @Transactional(readOnly = true)
    public List<TenantOrganizationDTO> findAllWhereTenantSettingsIsNull() {
        log.debug("Request to get all tenantOrganizations where TenantSettings is null");
        return StreamSupport
            .stream(tenantOrganizationRepository.findAll().spliterator(), false)
            .filter(tenantOrganization -> tenantOrganization.getTenantSettings() == null)
            .map(tenantOrganizationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }*/

    @Override
    @Transactional(readOnly = true)
    public Optional<TenantOrganizationDTO> findOne(Long id) {
        log.debug("Request to get TenantOrganization : {}", id);
        if (id == null || !organizationRowExists(id)) {
            evictOrganizationCache(id);
            return Optional.empty();
        }
        return tenantOrganizationRepository.findById(id).map(tenantOrganizationMapper::toDto).map(this::decorate);
    }

    /**
     * The local profile keeps a read-write second-level cache. A delete can commit in the
     * database while a concurrent read puts the old entity back into that cache, so
     * {@code findById} still returns an organization the list no longer contains.
     * A native count reads the table and bypasses that cache.
     */
    private boolean organizationRowExists(Long id) {
        Number count = (Number) entityManager
            .createNativeQuery("SELECT COUNT(*) FROM public.tenant_organization WHERE id = :id")
            .setParameter("id", id)
            .getSingleResult();
        return count != null && count.longValue() > 0;
    }

    private void evictOrganizationCache(Long id) {
        if (id == null) {
            return;
        }
        entityManager.getEntityManagerFactory().getCache().evict(TenantOrganization.class, id);
    }

    private TenantOrganizationDTO decorate(TenantOrganizationDTO dto) {
        if (dto == null) {
            return null;
        }
        decorate(List.of(dto));
        return dto;
    }

    @Override
    public void attachWebsiteTenantIds(List<TenantOrganizationDTO> organizations) {
        decorate(organizations);
    }

    private void decorate(List<TenantOrganizationDTO> dtos) {
        List<Long> ids = dtos.stream().map(TenantOrganizationDTO::getId).filter(id -> id != null).toList();
        Map<Long, List<String>> tenantIds = new HashMap<>();
        if (!ids.isEmpty()) {
            for (Object[] row : tenantSettingsRepository.findTenantIdsByOrganizationIds(ids)) {
                Long organizationId = (Long) row[0];
                String tenantId = (String) row[1];
                tenantIds.computeIfAbsent(organizationId, key -> new ArrayList<>()).add(tenantId);
            }
        }
        for (TenantOrganizationDTO dto : dtos) {
            dto.setWebsiteTenantIds(tenantIds.getOrDefault(dto.getId(), List.of()));
        }
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TenantOrganization : {}", id);
        tenantOrganizationRepository
            .findById(id)
            .ifPresent(org -> {
                for (TenantOrganization child : tenantOrganizationRepository.findByParentOrganizationId(id)) {
                    child.setParentOrganization(null);
                    tenantOrganizationRepository.save(child);
                }
                for (TenantSettings site : tenantSettingsRepository.findByTenantOrganizationId(id)) {
                    satelliteDomainService.releaseForOrganization(site.getTenantId(), org.getDomain(), org.getWebsiteUrl());
                    tenantOnboardingRequestRepository.releaseAssignedTenantId(site.getTenantId());
                }
            });
        tenantOrganizationRepository.deleteById(id);
        entityManager.flush();
        evictOrganizationCache(id);
    }

    @Override
    @Transactional(readOnly = true)
    public OrganizationWebsitesDTO findWebsites(Long organizationId) {
        TenantOrganization viewed = tenantOrganizationRepository
            .findById(organizationId)
            .orElseThrow(() -> new BadRequestAlertException("Organization not found", "tenantOrganization", "idnotfound"));
        TenantOrganization client = viewed.getParentOrganization() != null ? viewed.getParentOrganization() : viewed;
        OrganizationWebsitesDTO result = new OrganizationWebsitesDTO();
        result.setClient(decorate(tenantOrganizationMapper.toDto(client)));
        result.setViewedIsClient(viewed.getParentOrganization() == null);
        List<TenantOrganizationDTO> websites = tenantOrganizationRepository
            .findByParentOrganizationId(client.getId())
            .stream()
            .map(tenantOrganizationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
        decorate(websites);
        result.setWebsites(websites);
        List<OrganizationWebsiteDTO> settingsWebsites = new ArrayList<>();
        for (TenantSettings site : tenantSettingsRepository.findByTenantOrganizationId(viewed.getId())) {
            OrganizationWebsiteDTO row = new OrganizationWebsiteDTO();
            row.setSettingsId(site.getId());
            row.setTenantId(site.getTenantId());
            row.setOrganizationId(viewed.getId());
            settingsWebsites.add(row);
        }
        result.setSettingsWebsites(settingsWebsites);
        return result;
    }

    @Override
    public TenantOrganizationDTO assignParent(Long organizationId, Long parentOrganizationId) {
        TenantOrganization organization = tenantOrganizationRepository
            .findById(organizationId)
            .orElseThrow(() -> new BadRequestAlertException("Organization not found", "tenantOrganization", "idnotfound"));
        if (parentOrganizationId == null) {
            organization.setParentOrganization(null);
            return decorate(tenantOrganizationMapper.toDto(tenantOrganizationRepository.save(organization)));
        }
        if (parentOrganizationId.equals(organizationId)) {
            throw new BadRequestAlertException("An organization cannot be its own website", "tenantOrganization", "ownparent");
        }
        TenantOrganization parent = tenantOrganizationRepository
            .findById(parentOrganizationId)
            .orElseThrow(() -> new BadRequestAlertException("Client organization not found", "tenantOrganization", "parentnotfound"));
        if (parent.getParentOrganization() != null) {
            throw new BadRequestAlertException(
                "Websites belong to one client organization. Choose the client, not another website.",
                "tenantOrganization",
                "parentiswebsite"
            );
        }
        if (tenantOrganizationRepository.countByParentOrganizationId(organizationId) > 0) {
            throw new BadRequestAlertException(
                "This organization already owns websites, so it cannot be moved under another organization.",
                "tenantOrganization",
                "clienthaswebsites"
            );
        }
        organization.setParentOrganization(parent);
        return decorate(tenantOrganizationMapper.toDto(tenantOrganizationRepository.save(organization)));
    }

    private void requireUniqueDomain(TenantOrganizationDTO tenantOrganizationDTO) {
        if (!StringUtils.hasText(tenantOrganizationDTO.getDomain())) {
            tenantOrganizationDTO.setDomain(null);
            return;
        }
        requireUniqueDomain(tenantOrganizationDTO.getDomain(), tenantOrganizationDTO.getId());
    }

    private void requireUniqueDomain(String domain, Long excludeId) {
        if (!StringUtils.hasText(domain)) {
            return;
        }
        String trimmed = domain.trim();
        Long otherThan = excludeId == null ? -1L : excludeId;
        if (tenantOrganizationRepository.existsByDomainIgnoreCaseAndIdNot(trimmed, otherThan)) {
            throw new BadRequestAlertException(
                "An organization with this domain already exists. Use a different domain or leave Domain empty.",
                "tenantOrganization",
                "domainexists"
            );
        }
    }

    private void requireUniqueOrganizationName(TenantOrganizationDTO tenantOrganizationDTO) {
        if (tenantOrganizationDTO.getOrganizationName() != null) {
            tenantOrganizationDTO.setOrganizationName(tenantOrganizationDTO.getOrganizationName().trim());
        }
        requireUniqueOrganizationName(tenantOrganizationDTO.getOrganizationName(), tenantOrganizationDTO.getId());
    }

    private void requireUniqueOrganizationName(String organizationName, Long excludeId) {
        if (!StringUtils.hasText(organizationName)) {
            throw new BadRequestAlertException("Organization name is required", "tenantOrganization", "organizationnamerequired");
        }
        String trimmed = organizationName.trim();
        Long otherThan = excludeId == null ? -1L : excludeId;
        if (tenantOrganizationRepository.existsByOrganizationNameIgnoreCaseAndIdNot(trimmed, otherThan)) {
            throw new BadRequestAlertException(
                "An organization named " + trimmed + " already exists",
                "tenantOrganization",
                "organizationnameexists"
            );
        }
    }
}
