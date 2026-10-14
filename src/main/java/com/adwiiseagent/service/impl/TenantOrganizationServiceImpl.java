package com.adwiiseagent.service.impl;

import com.adwiiseagent.domain.TenantOrganization;
import com.adwiiseagent.errors.BadRequestAlertException;
import com.adwiiseagent.repository.TenantOrganizationRepository;
import com.adwiiseagent.service.TenantOrganizationService;
import com.adwiiseagent.service.dto.TenantOrganizationDTO;
import com.adwiiseagent.service.mapper.TenantOrganizationMapper;
import com.adwiiseagent.service.validation.TenantOrganizationProfileValidator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.adwiiseagent.domain.TenantOrganization}.
 */
@Service
@Transactional
public class TenantOrganizationServiceImpl implements TenantOrganizationService {

    private final Logger log = LoggerFactory.getLogger(TenantOrganizationServiceImpl.class);

    private final TenantOrganizationRepository tenantOrganizationRepository;

    private final TenantOrganizationMapper tenantOrganizationMapper;

    private final TenantOrganizationProfileValidator tenantOrganizationProfileValidator;

    @PersistenceContext
    private EntityManager entityManager;

    public TenantOrganizationServiceImpl(
        TenantOrganizationRepository tenantOrganizationRepository,
        TenantOrganizationMapper tenantOrganizationMapper,
        TenantOrganizationProfileValidator tenantOrganizationProfileValidator
    ) {
        this.tenantOrganizationRepository = tenantOrganizationRepository;
        this.tenantOrganizationMapper = tenantOrganizationMapper;
        this.tenantOrganizationProfileValidator = tenantOrganizationProfileValidator;
    }

    @Override
    public TenantOrganizationDTO save(TenantOrganizationDTO tenantOrganizationDTO) {
        log.debug("Request to save TenantOrganization : {}", tenantOrganizationDTO);
        tenantOrganizationProfileValidator.validateAndNormalize(tenantOrganizationDTO);
        TenantOrganization tenantOrganization = tenantOrganizationMapper.toEntity(tenantOrganizationDTO);
        tenantOrganization = tenantOrganizationRepository.save(tenantOrganization);
        return tenantOrganizationMapper.toDto(tenantOrganization);
    }

    @Override
    public TenantOrganizationDTO update(TenantOrganizationDTO tenantOrganizationDTO) {
        log.debug("Request to update TenantOrganization : {}", tenantOrganizationDTO);
        tenantOrganizationProfileValidator.validateAndNormalize(tenantOrganizationDTO);
        renameTenantIdIfChanged(tenantOrganizationDTO.getId(), tenantOrganizationDTO.getTenantId());
        TenantOrganization tenantOrganization = tenantOrganizationMapper.toEntity(tenantOrganizationDTO);
        tenantOrganization = tenantOrganizationRepository.save(tenantOrganization);
        return tenantOrganizationMapper.toDto(tenantOrganization);
    }

    @Override
    public Optional<TenantOrganizationDTO> partialUpdate(TenantOrganizationDTO tenantOrganizationDTO) {
        log.debug("Request to partially update TenantOrganization : {}", tenantOrganizationDTO);
        tenantOrganizationProfileValidator.validatePresentFields(tenantOrganizationDTO);

        renameTenantIdIfChanged(tenantOrganizationDTO.getId(), tenantOrganizationDTO.getTenantId());

        return tenantOrganizationRepository
            .findById(tenantOrganizationDTO.getId())
            .map(existingTenantOrganization -> {
                tenantOrganizationMapper.partialUpdate(existingTenantOrganization, tenantOrganizationDTO);
                tenantOrganizationProfileValidator.applyPartialNullableFields(existingTenantOrganization, tenantOrganizationDTO);
                tenantOrganizationProfileValidator.validateEntityFields(existingTenantOrganization);

                return existingTenantOrganization;
            })
            .map(tenantOrganizationRepository::save)
            .map(tenantOrganizationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TenantOrganizationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TenantOrganizations");
        return tenantOrganizationRepository.findAll(pageable).map(tenantOrganizationMapper::toDto);
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
        return tenantOrganizationRepository.findById(id).map(tenantOrganizationMapper::toDto);
    }

    /**
     * Renames {@code tenant_organization.tenant_id} and every public table that stores the same value.
     * Foreign keys to {@code tenant_organization(tenant_id)} must be {@code ON UPDATE CASCADE}.
     * Tables without that foreign key are updated explicitly so a super-admin rename stays consistent.
     */
    private void renameTenantIdIfChanged(Long id, String requestedTenantId) {
        if (id == null || requestedTenantId == null || requestedTenantId.isBlank()) {
            return;
        }
        String newTenantId = requestedTenantId.trim();
        String currentTenantId = tenantOrganizationRepository.findById(id).map(TenantOrganization::getTenantId).orElse(null);
        if (currentTenantId == null || currentTenantId.equals(newTenantId)) {
            return;
        }
        if (newTenantId.length() > 255 || !newTenantId.matches("[a-z0-9]+(?:_[a-z0-9]+)*")) {
            throw new BadRequestAlertException(
                "Tenant ID must use lowercase letters, numbers, and underscores",
                "tenantOrganization",
                "tenantidinvalid"
            );
        }

        Number clash = (Number) entityManager
            .createNativeQuery("SELECT COUNT(*) FROM public.tenant_organization WHERE tenant_id = :newId AND id <> :id")
            .setParameter("newId", newTenantId)
            .setParameter("id", id)
            .getSingleResult();
        if (clash.longValue() > 0) {
            throw new BadRequestAlertException("Tenant ID is already in use", "tenantOrganization", "tenantidexists");
        }

        int updated = entityManager
            .createNativeQuery(
                "UPDATE public.tenant_organization SET tenant_id = :newId, updated_at = NOW() WHERE id = :id AND tenant_id = :oldId"
            )
            .setParameter("newId", newTenantId)
            .setParameter("id", id)
            .setParameter("oldId", currentTenantId)
            .executeUpdate();
        if (updated != 1) {
            throw new BadRequestAlertException("Could not update Tenant ID", "tenantOrganization", "tenantidupdatefailed");
        }

        @SuppressWarnings("unchecked")
        List<Object> tables = entityManager
            .createNativeQuery(
                """
                SELECT c.table_name
                FROM information_schema.columns c
                JOIN information_schema.tables t
                  ON t.table_schema = c.table_schema
                 AND t.table_name = c.table_name
                WHERE c.table_schema = 'public'
                  AND c.column_name = 'tenant_id'
                  AND t.table_type = 'BASE TABLE'
                  AND c.table_name <> 'tenant_organization'
                """
            )
            .getResultList();
        for (Object table : tables) {
            String tableName = String.valueOf(table);
            if (!tableName.matches("[a-z0-9_]+")) {
                continue;
            }
            entityManager
                .createNativeQuery("UPDATE public." + tableName + " SET tenant_id = :newId WHERE tenant_id = :oldId")
                .setParameter("newId", newTenantId)
                .setParameter("oldId", currentTenantId)
                .executeUpdate();
        }
        entityManager.flush();
        entityManager.clear();
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TenantOrganization : {}", id);
        tenantOrganizationRepository.deleteById(id);
    }
}
