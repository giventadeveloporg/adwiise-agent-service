package com.adwiiseagent.service.impl;

import com.adwiiseagent.domain.RetailLocationProfile;
import com.adwiiseagent.repository.RetailLocationProfileRepository;
import com.adwiiseagent.service.RetailAccessService;
import com.adwiiseagent.service.RetailLocationProfileService;
import com.adwiiseagent.service.dto.RetailLocationProfileDTO;
import com.adwiiseagent.service.mapper.RetailLocationProfileMapper;
import java.time.ZonedDateTime;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RetailLocationProfileServiceImpl implements RetailLocationProfileService {

    private static final Logger LOG = LoggerFactory.getLogger(RetailLocationProfileServiceImpl.class);

    private final RetailLocationProfileRepository retailLocationProfileRepository;
    private final RetailLocationProfileMapper retailLocationProfileMapper;
    private final RetailAccessService retailAccessService;

    public RetailLocationProfileServiceImpl(
        RetailLocationProfileRepository retailLocationProfileRepository,
        RetailLocationProfileMapper retailLocationProfileMapper,
        RetailAccessService retailAccessService
    ) {
        this.retailLocationProfileRepository = retailLocationProfileRepository;
        this.retailLocationProfileMapper = retailLocationProfileMapper;
        this.retailAccessService = retailAccessService;
    }

    @Override
    public RetailLocationProfileDTO save(RetailLocationProfileDTO dto) {
        LOG.debug("Request to save RetailLocationProfile : {}", dto);
        retailAccessService.assertAllLocationsScope();
        RetailLocationProfile entity = retailLocationProfileMapper.toEntity(dto);
        entity.setId(null);
        stamp(entity);
        entity = retailLocationProfileRepository.save(entity);
        return retailLocationProfileMapper.toDto(entity);
    }

    @Override
    public RetailLocationProfileDTO update(RetailLocationProfileDTO dto) {
        LOG.debug("Request to update RetailLocationProfile : {}", dto);
        retailAccessService.assertAllLocationsScope();
        RetailLocationProfile entity = retailLocationProfileMapper.toEntity(dto);
        if (entity.getUpdatedAt() == null) {
            entity.setUpdatedAt(ZonedDateTime.now());
        }
        entity = retailLocationProfileRepository.save(entity);
        return retailLocationProfileMapper.toDto(entity);
    }

    @Override
    public Optional<RetailLocationProfileDTO> partialUpdate(RetailLocationProfileDTO dto) {
        LOG.debug("Request to partially update RetailLocationProfile : {}", dto);
        retailAccessService.assertAllLocationsScope();
        return retailLocationProfileRepository
            .findById(dto.getId())
            .map(existing -> {
                retailLocationProfileMapper.partialUpdate(existing, dto);
                if (existing.getUpdatedAt() == null) {
                    existing.setUpdatedAt(ZonedDateTime.now());
                }
                return existing;
            })
            .map(retailLocationProfileRepository::save)
            .map(retailLocationProfileMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RetailLocationProfileDTO> findOne(Long id) {
        LOG.debug("Request to get RetailLocationProfile : {}", id);
        retailAccessService.assertModuleAccess();
        return retailLocationProfileRepository
            .findById(id)
            .map(retailLocationProfileMapper::toDto)
            .map(dto -> {
                retailAccessService.assertLocationIdAllowed(dto.getLocationId());
                return dto;
            });
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete RetailLocationProfile : {}", id);
        retailAccessService.assertAllLocationsScope();
        retailLocationProfileRepository.deleteById(id);
    }

    private static void stamp(RetailLocationProfile entity) {
        ZonedDateTime now = ZonedDateTime.now();
        if (entity.getCreatedAt() == null) {
            entity.setCreatedAt(now);
        }
        if (entity.getUpdatedAt() == null) {
            entity.setUpdatedAt(now);
        }
    }
}
