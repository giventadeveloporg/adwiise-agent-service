package com.eventsitemanager.service.impl;

import com.eventsitemanager.domain.RetailRecommendation;
import com.eventsitemanager.repository.RetailRecommendationRepository;
import com.eventsitemanager.service.RetailAccessService;
import com.eventsitemanager.service.RetailRecommendationService;
import com.eventsitemanager.service.dto.RetailRecommendationDTO;
import com.eventsitemanager.service.mapper.RetailRecommendationMapper;
import java.time.ZonedDateTime;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RetailRecommendationServiceImpl implements RetailRecommendationService {

    private static final Logger LOG = LoggerFactory.getLogger(RetailRecommendationServiceImpl.class);

    private final RetailRecommendationRepository retailRecommendationRepository;
    private final RetailRecommendationMapper retailRecommendationMapper;
    private final RetailAccessService retailAccessService;

    public RetailRecommendationServiceImpl(
        RetailRecommendationRepository retailRecommendationRepository,
        RetailRecommendationMapper retailRecommendationMapper,
        RetailAccessService retailAccessService
    ) {
        this.retailRecommendationRepository = retailRecommendationRepository;
        this.retailRecommendationMapper = retailRecommendationMapper;
        this.retailAccessService = retailAccessService;
    }

    @Override
    public RetailRecommendationDTO save(RetailRecommendationDTO dto) {
        LOG.debug("Request to save RetailRecommendation : {}", dto);
        retailAccessService.assertRecommendationAccess(dto.getLocationId());
        RetailRecommendation entity = retailRecommendationMapper.toEntity(dto);
        entity.setId(null);
        stamp(entity);
        entity = retailRecommendationRepository.save(entity);
        return retailRecommendationMapper.toDto(entity);
    }

    @Override
    public RetailRecommendationDTO update(RetailRecommendationDTO dto) {
        LOG.debug("Request to update RetailRecommendation : {}", dto);
        retailAccessService.assertRecommendationAccess(dto.getLocationId());
        RetailRecommendation entity = retailRecommendationMapper.toEntity(dto);
        if (entity.getUpdatedAt() == null) {
            entity.setUpdatedAt(ZonedDateTime.now());
        }
        entity = retailRecommendationRepository.save(entity);
        return retailRecommendationMapper.toDto(entity);
    }

    @Override
    public Optional<RetailRecommendationDTO> partialUpdate(RetailRecommendationDTO dto) {
        LOG.debug("Request to partially update RetailRecommendation : {}", dto);
        retailAccessService.assertRecommendationAccess(dto.getLocationId());
        return retailRecommendationRepository
            .findById(dto.getId())
            .map(existing -> {
                retailRecommendationMapper.partialUpdate(existing, dto);
                if (existing.getUpdatedAt() == null) {
                    existing.setUpdatedAt(ZonedDateTime.now());
                }
                return existing;
            })
            .map(retailRecommendationRepository::save)
            .map(retailRecommendationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RetailRecommendationDTO> findOne(Long id) {
        LOG.debug("Request to get RetailRecommendation : {}", id);
        retailAccessService.assertModuleAccess();
        return retailRecommendationRepository
            .findById(id)
            .map(retailRecommendationMapper::toDto)
            .map(dto -> {
                retailAccessService.assertRecommendationAccess(dto.getLocationId());
                return dto;
            });
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete RetailRecommendation : {}", id);
        retailAccessService.assertAllLocationsScope();
        retailRecommendationRepository.deleteById(id);
    }

    private static void stamp(RetailRecommendation entity) {
        ZonedDateTime now = ZonedDateTime.now();
        if (entity.getCreatedAt() == null) {
            entity.setCreatedAt(now);
        }
        if (entity.getUpdatedAt() == null) {
            entity.setUpdatedAt(now);
        }
    }
}
