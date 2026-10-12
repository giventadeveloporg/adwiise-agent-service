package com.eventsitemanager.service.impl;

import com.eventsitemanager.domain.RetailDailyMetrics;
import com.eventsitemanager.repository.RetailDailyMetricsRepository;
import com.eventsitemanager.service.RetailAccessService;
import com.eventsitemanager.service.RetailDailyMetricsService;
import com.eventsitemanager.service.dto.RetailDailyMetricsDTO;
import com.eventsitemanager.service.mapper.RetailDailyMetricsMapper;
import java.time.ZonedDateTime;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RetailDailyMetricsServiceImpl implements RetailDailyMetricsService {

    private static final Logger LOG = LoggerFactory.getLogger(RetailDailyMetricsServiceImpl.class);

    private final RetailDailyMetricsRepository retailDailyMetricsRepository;
    private final RetailDailyMetricsMapper retailDailyMetricsMapper;
    private final RetailAccessService retailAccessService;

    public RetailDailyMetricsServiceImpl(
        RetailDailyMetricsRepository retailDailyMetricsRepository,
        RetailDailyMetricsMapper retailDailyMetricsMapper,
        RetailAccessService retailAccessService
    ) {
        this.retailDailyMetricsRepository = retailDailyMetricsRepository;
        this.retailDailyMetricsMapper = retailDailyMetricsMapper;
        this.retailAccessService = retailAccessService;
    }

    @Override
    public RetailDailyMetricsDTO save(RetailDailyMetricsDTO dto) {
        LOG.debug("Request to save RetailDailyMetrics : {}", dto);
        retailAccessService.assertAllLocationsScope();
        RetailDailyMetrics entity = retailDailyMetricsMapper.toEntity(dto);
        entity.setId(null);
        stamp(entity);
        entity = retailDailyMetricsRepository.save(entity);
        return retailDailyMetricsMapper.toDto(entity);
    }

    @Override
    public RetailDailyMetricsDTO update(RetailDailyMetricsDTO dto) {
        LOG.debug("Request to update RetailDailyMetrics : {}", dto);
        retailAccessService.assertAllLocationsScope();
        RetailDailyMetrics entity = retailDailyMetricsMapper.toEntity(dto);
        if (entity.getUpdatedAt() == null) {
            entity.setUpdatedAt(ZonedDateTime.now());
        }
        entity = retailDailyMetricsRepository.save(entity);
        return retailDailyMetricsMapper.toDto(entity);
    }

    @Override
    public Optional<RetailDailyMetricsDTO> partialUpdate(RetailDailyMetricsDTO dto) {
        LOG.debug("Request to partially update RetailDailyMetrics : {}", dto);
        retailAccessService.assertAllLocationsScope();
        return retailDailyMetricsRepository
            .findById(dto.getId())
            .map(existing -> {
                retailDailyMetricsMapper.partialUpdate(existing, dto);
                if (existing.getUpdatedAt() == null) {
                    existing.setUpdatedAt(ZonedDateTime.now());
                }
                return existing;
            })
            .map(retailDailyMetricsRepository::save)
            .map(retailDailyMetricsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RetailDailyMetricsDTO> findOne(Long id) {
        LOG.debug("Request to get RetailDailyMetrics : {}", id);
        retailAccessService.assertModuleAccess();
        return retailDailyMetricsRepository
            .findById(id)
            .map(retailDailyMetricsMapper::toDto)
            .map(dto -> {
                retailAccessService.assertLocationIdAllowed(dto.getLocationId());
                return dto;
            });
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete RetailDailyMetrics : {}", id);
        retailAccessService.assertAllLocationsScope();
        retailDailyMetricsRepository.deleteById(id);
    }

    private static void stamp(RetailDailyMetrics entity) {
        ZonedDateTime now = ZonedDateTime.now();
        if (entity.getCreatedAt() == null) {
            entity.setCreatedAt(now);
        }
        if (entity.getUpdatedAt() == null) {
            entity.setUpdatedAt(now);
        }
    }
}
