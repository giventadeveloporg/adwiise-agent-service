package com.adwiiseagent.service.impl;

import com.adwiiseagent.domain.SatelliteDomain;
import com.adwiiseagent.repository.SatelliteDomainRepository;
import com.adwiiseagent.repository.TenantSettingsRepository;
import com.adwiiseagent.service.SatelliteDomainService;
import com.adwiiseagent.service.TenantOnboardingSupport;
import com.adwiiseagent.service.dto.SatelliteDomainDTO;
import com.adwiiseagent.service.mapper.SatelliteDomainMapper;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.adwiiseagent.domain.SatelliteDomain}.
 */
@Service
@Transactional
public class SatelliteDomainServiceImpl implements SatelliteDomainService {

    private static final Logger LOG = LoggerFactory.getLogger(SatelliteDomainServiceImpl.class);

    private final SatelliteDomainRepository satelliteDomainRepository;

    private final SatelliteDomainMapper satelliteDomainMapper;

    private final TenantSettingsRepository tenantSettingsRepository;

    public SatelliteDomainServiceImpl(
        SatelliteDomainRepository satelliteDomainRepository,
        SatelliteDomainMapper satelliteDomainMapper,
        TenantSettingsRepository tenantSettingsRepository
    ) {
        this.satelliteDomainRepository = satelliteDomainRepository;
        this.satelliteDomainMapper = satelliteDomainMapper;
        this.tenantSettingsRepository = tenantSettingsRepository;
    }

    @Override
    @CacheEvict(value = "satelliteDomains", allEntries = true)
    public SatelliteDomainDTO save(SatelliteDomainDTO satelliteDomainDTO) {
        LOG.debug("Request to save SatelliteDomain : {}", satelliteDomainDTO);
        SatelliteDomain satelliteDomain = satelliteDomainMapper.toEntity(satelliteDomainDTO);
        satelliteDomain = satelliteDomainRepository.save(satelliteDomain);
        return satelliteDomainMapper.toDto(satelliteDomain);
    }

    @Override
    @CacheEvict(value = "satelliteDomains", allEntries = true)
    public SatelliteDomainDTO update(SatelliteDomainDTO satelliteDomainDTO) {
        LOG.debug("Request to update SatelliteDomain : {}", satelliteDomainDTO);
        SatelliteDomain satelliteDomain = satelliteDomainMapper.toEntity(satelliteDomainDTO);
        satelliteDomain = satelliteDomainRepository.save(satelliteDomain);
        return satelliteDomainMapper.toDto(satelliteDomain);
    }

    @Override
    public Optional<SatelliteDomainDTO> partialUpdate(SatelliteDomainDTO satelliteDomainDTO) {
        LOG.debug("Request to partially update SatelliteDomain : {}", satelliteDomainDTO);

        return satelliteDomainRepository
            .findById(satelliteDomainDTO.getId())
            .map(existingSatelliteDomain -> {
                satelliteDomainMapper.partialUpdate(existingSatelliteDomain, satelliteDomainDTO);

                return existingSatelliteDomain;
            })
            .map(satelliteDomainRepository::save)
            .map(satelliteDomainMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "satelliteDomains", key = "#id", unless = "#result == null")
    public Optional<SatelliteDomainDTO> findOne(Long id) {
        LOG.debug("Request to get SatelliteDomain : {}", id);
        return satelliteDomainRepository.findById(id).map(satelliteDomainMapper::toDto);
    }

    @Override
    @CacheEvict(value = "satelliteDomains", allEntries = true)
    public void delete(Long id) {
        LOG.debug("Request to delete SatelliteDomain : {}", id);
        satelliteDomainRepository.deleteById(id);
    }

    @Override
    @CacheEvict(value = "satelliteDomains", allEntries = true)
    public void releaseForOrganization(String tenantId, String... hostSources) {
        String tid = tenantId == null ? "" : tenantId.trim();
        if (!tid.isEmpty()) {
            List<SatelliteDomain> rows = satelliteDomainRepository.findAllByTenantId(tid);
            if (!rows.isEmpty()) {
                LOG.info("Releasing {} satellite domain(s) for organization tenant {}", rows.size(), tid);
                satelliteDomainRepository.deleteAll(rows);
                satelliteDomainRepository.flush();
            }
        }
        Set<String> hosts = new LinkedHashSet<>();
        if (hostSources != null) {
            for (String source : hostSources) {
                addHostnameVariants(hosts, source);
            }
        }
        for (String host : hosts) {
            satelliteDomainRepository.findByHostname(host).ifPresent(row -> deleteIfReleasedWith(row, tid));
        }
        satelliteDomainRepository.flush();
    }

    @Override
    @CacheEvict(value = "satelliteDomains", allEntries = true)
    public boolean hostnameHeldByLiveOrganization(String hostname) {
        Set<String> hosts = new LinkedHashSet<>();
        addHostnameVariants(hosts, hostname);
        boolean held = false;
        for (String host : hosts) {
            Optional<SatelliteDomain> found = satelliteDomainRepository.findByHostname(host);
            if (found.isEmpty()) {
                continue;
            }
            SatelliteDomain row = found.orElseThrow();
            if (organizationStillExists(row.getTenantId())) {
                held = true;
                continue;
            }
            LOG.info("Released orphan satellite hostname {} because tenant {} has no organization", row.getHostname(), row.getTenantId());
            satelliteDomainRepository.delete(row);
            satelliteDomainRepository.flush();
        }
        return held;
    }

    private void deleteIfReleasedWith(SatelliteDomain row, String tenantId) {
        String owner = row.getTenantId() == null ? "" : row.getTenantId().trim();
        if (owner.isEmpty() || owner.equals(tenantId) || !organizationStillExists(owner)) {
            LOG.info("Releasing satellite hostname {} for tenant {}", row.getHostname(), owner);
            satelliteDomainRepository.delete(row);
        }
    }

    private boolean organizationStillExists(String tenantId) {
        String tid = tenantId == null ? "" : tenantId.trim();
        return !tid.isEmpty() && tenantSettingsRepository.findByTenantId(tid).isPresent();
    }

    private static void addHostnameVariants(Set<String> hosts, String raw) {
        String host = TenantOnboardingSupport.normalizeHostname(raw);
        if (host == null || !TenantOnboardingSupport.isValidHostname(host)) {
            return;
        }
        String bare = TenantOnboardingSupport.stripWww(host);
        hosts.add(host);
        hosts.add(bare);
        hosts.add("www." + bare);
    }
}
