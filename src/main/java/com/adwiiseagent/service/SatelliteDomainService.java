package com.adwiiseagent.service;

import com.adwiiseagent.service.dto.SatelliteDomainDTO;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.adwiiseagent.domain.SatelliteDomain}.
 */
public interface SatelliteDomainService {
    /**
     * Save a satelliteDomain.
     *
     * @param satelliteDomainDTO the entity to save.
     * @return the persisted entity.
     */
    SatelliteDomainDTO save(SatelliteDomainDTO satelliteDomainDTO);

    /**
     * Updates a satelliteDomain.
     *
     * @param satelliteDomainDTO the entity to update.
     * @return the persisted entity.
     */
    SatelliteDomainDTO update(SatelliteDomainDTO satelliteDomainDTO);

    /**
     * Partially updates a satelliteDomain.
     *
     * @param satelliteDomainDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SatelliteDomainDTO> partialUpdate(SatelliteDomainDTO satelliteDomainDTO);

    /**
     * Get the "id" satelliteDomain.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SatelliteDomainDTO> findOne(Long id);

    /**
     * Delete the "id" satelliteDomain.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Remove satellite rows for an organization that is being deleted, including the public hostname.
     * {@code satellite_domain.tenant_id} has no foreign key, so deleting the organization does not free the domain.
     */
    void releaseForOrganization(String tenantId, String... hostSources);

    /**
     * True when a satellite row for this hostname still belongs to a live organization.
     * A row whose tenant organization is already gone is deleted so the hostname can be registered again.
     */
    boolean hostnameHeldByLiveOrganization(String hostname);
}
