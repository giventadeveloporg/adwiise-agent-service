package com.adwiiseagent.service;

import com.adwiiseagent.service.dto.OrganizationWebsitesDTO;
import com.adwiiseagent.service.dto.TenantOrganizationDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.adwiiseagent.domain.TenantOrganization}.
 */
public interface TenantOrganizationService {
    /**
     * Save a tenantOrganization.
     *
     * @param tenantOrganizationDTO the entity to save.
     * @return the persisted entity.
     */
    TenantOrganizationDTO save(TenantOrganizationDTO tenantOrganizationDTO);

    /**
     * Updates a tenantOrganization.
     *
     * @param tenantOrganizationDTO the entity to update.
     * @return the persisted entity.
     */
    TenantOrganizationDTO update(TenantOrganizationDTO tenantOrganizationDTO);

    /**
     * Partially updates a tenantOrganization.
     *
     * @param tenantOrganizationDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TenantOrganizationDTO> partialUpdate(TenantOrganizationDTO tenantOrganizationDTO);

    /**
     * Get all the tenantOrganizations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TenantOrganizationDTO> findAll(Pageable pageable);

    /**
     * Get all the TenantOrganizationDTO where TenantSettings is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    //    List<TenantOrganizationDTO> findAllWhereTenantSettingsIsNull();

    /**
     * Get the "id" tenantOrganization.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TenantOrganizationDTO> findOne(Long id);

    /**
     * Delete the "id" tenantOrganization.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Client organization and the websites it owns. A website id resolves to its client.
     */
    OrganizationWebsitesDTO findWebsites(Long organizationId);

    /**
     * Move a website under a client, or detach it when {@code parentOrganizationId} is null.
     * A client that already owns websites cannot become a website.
     */
    TenantOrganizationDTO assignParent(Long organizationId, Long parentOrganizationId);
}
