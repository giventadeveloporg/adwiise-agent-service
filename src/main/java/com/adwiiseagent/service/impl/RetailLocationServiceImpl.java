package com.adwiiseagent.service.impl;

import com.adwiiseagent.domain.RetailLocation;
import com.adwiiseagent.repository.RetailLocationRepository;
import com.adwiiseagent.service.RetailAccessService;
import com.adwiiseagent.service.RetailLocationService;
import com.adwiiseagent.service.dto.RetailLocationDTO;
import com.adwiiseagent.service.mapper.RetailLocationMapper;
import java.time.ZonedDateTime;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RetailLocationServiceImpl implements RetailLocationService {

    private static final Logger LOG = LoggerFactory.getLogger(RetailLocationServiceImpl.class);

    private final RetailLocationRepository retailLocationRepository;
    private final RetailLocationMapper retailLocationMapper;
    private final RetailAccessService retailAccessService;

    public RetailLocationServiceImpl(
        RetailLocationRepository retailLocationRepository,
        RetailLocationMapper retailLocationMapper,
        RetailAccessService retailAccessService
    ) {
        this.retailLocationRepository = retailLocationRepository;
        this.retailLocationMapper = retailLocationMapper;
        this.retailAccessService = retailAccessService;
    }

    @Override
    public RetailLocationDTO save(RetailLocationDTO dto) {
        LOG.debug("Request to save RetailLocation : {}", dto);
        retailAccessService.assertAllLocationsScope();
        RetailLocation entity = retailLocationMapper.toEntity(dto);
        entity.setId(null);
        stamp(entity);
        entity = retailLocationRepository.save(entity);
        return retailLocationMapper.toDto(entity);
    }

    @Override
    public RetailLocationDTO update(RetailLocationDTO dto) {
        LOG.debug("Request to update RetailLocation : {}", dto);
        retailAccessService.assertAllLocationsScope();
        RetailLocation entity = retailLocationMapper.toEntity(dto);
        if (entity.getUpdatedAt() == null) {
            entity.setUpdatedAt(ZonedDateTime.now());
        }
        entity = retailLocationRepository.save(entity);
        return retailLocationMapper.toDto(entity);
    }

    @Override
    public Optional<RetailLocationDTO> partialUpdate(RetailLocationDTO dto) {
        LOG.debug("Request to partially update RetailLocation : {}", dto);
        retailAccessService.assertAllLocationsScope();
        return retailLocationRepository
            .findById(dto.getId())
            .map(existing -> {
                retailLocationMapper.partialUpdate(existing, dto);
                if (existing.getUpdatedAt() == null) {
                    existing.setUpdatedAt(ZonedDateTime.now());
                }
                return existing;
            })
            .map(retailLocationRepository::save)
            .map(retailLocationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RetailLocationDTO> findOne(Long id) {
        LOG.debug("Request to get RetailLocation : {}", id);
        retailAccessService.assertModuleAccess();
        return retailLocationRepository
            .findById(id)
            .map(retailLocationMapper::toDto)
            .map(dto -> {
                retailAccessService.assertLocationIdAllowed(dto.getId());
                return dto;
            });
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete RetailLocation : {}", id);
        retailAccessService.assertAllLocationsScope();
        retailLocationRepository.deleteById(id);
    }

    private static void stamp(RetailLocation entity) {
        ZonedDateTime now = ZonedDateTime.now();
        if (entity.getCreatedAt() == null) {
            entity.setCreatedAt(now);
        }
        if (entity.getUpdatedAt() == null) {
            entity.setUpdatedAt(now);
        }
    }
}
