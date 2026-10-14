package com.adwiiseagent.service.impl;

import com.adwiiseagent.domain.RetailIntegration;
import com.adwiiseagent.repository.RetailIntegrationRepository;
import com.adwiiseagent.service.RetailAccessService;
import com.adwiiseagent.service.RetailIntegrationService;
import com.adwiiseagent.service.dto.RetailIntegrationDTO;
import com.adwiiseagent.service.mapper.RetailIntegrationMapper;
import java.time.ZonedDateTime;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RetailIntegrationServiceImpl implements RetailIntegrationService {

    private static final Logger LOG = LoggerFactory.getLogger(RetailIntegrationServiceImpl.class);

    private final RetailIntegrationRepository retailIntegrationRepository;
    private final RetailIntegrationMapper retailIntegrationMapper;
    private final RetailAccessService retailAccessService;

    public RetailIntegrationServiceImpl(
        RetailIntegrationRepository retailIntegrationRepository,
        RetailIntegrationMapper retailIntegrationMapper,
        RetailAccessService retailAccessService
    ) {
        this.retailIntegrationRepository = retailIntegrationRepository;
        this.retailIntegrationMapper = retailIntegrationMapper;
        this.retailAccessService = retailAccessService;
    }

    @Override
    public RetailIntegrationDTO save(RetailIntegrationDTO dto) {
        LOG.debug("Request to save RetailIntegration : {}", dto);
        retailAccessService.assertAllLocationsScope();
        RetailIntegration entity = retailIntegrationMapper.toEntity(dto);
        entity.setId(null);
        stamp(entity);
        entity = retailIntegrationRepository.save(entity);
        return retailIntegrationMapper.toDto(entity);
    }

    @Override
    public RetailIntegrationDTO update(RetailIntegrationDTO dto) {
        LOG.debug("Request to update RetailIntegration : {}", dto);
        retailAccessService.assertAllLocationsScope();
        RetailIntegration entity = retailIntegrationMapper.toEntity(dto);
        if (entity.getUpdatedAt() == null) {
            entity.setUpdatedAt(ZonedDateTime.now());
        }
        entity = retailIntegrationRepository.save(entity);
        return retailIntegrationMapper.toDto(entity);
    }

    @Override
    public Optional<RetailIntegrationDTO> partialUpdate(RetailIntegrationDTO dto) {
        LOG.debug("Request to partially update RetailIntegration : {}", dto);
        retailAccessService.assertAllLocationsScope();
        return retailIntegrationRepository
            .findById(dto.getId())
            .map(existing -> {
                retailIntegrationMapper.partialUpdate(existing, dto);
                if (existing.getUpdatedAt() == null) {
                    existing.setUpdatedAt(ZonedDateTime.now());
                }
                return existing;
            })
            .map(retailIntegrationRepository::save)
            .map(retailIntegrationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RetailIntegrationDTO> findOne(Long id) {
        LOG.debug("Request to get RetailIntegration : {}", id);
        retailAccessService.assertModuleAccess();
        return retailIntegrationRepository
            .findById(id)
            .map(retailIntegrationMapper::toDto)
            .map(dto -> {
                retailAccessService.assertLocationIdAllowed(dto.getLocationId());
                return dto;
            });
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete RetailIntegration : {}", id);
        retailAccessService.assertAllLocationsScope();
        retailIntegrationRepository.deleteById(id);
    }

    private static void stamp(RetailIntegration entity) {
        ZonedDateTime now = ZonedDateTime.now();
        if (entity.getCreatedAt() == null) {
            entity.setCreatedAt(now);
        }
        if (entity.getUpdatedAt() == null) {
            entity.setUpdatedAt(now);
        }
    }
}
