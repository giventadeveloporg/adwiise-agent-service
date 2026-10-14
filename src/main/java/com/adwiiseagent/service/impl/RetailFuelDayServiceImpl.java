package com.adwiiseagent.service.impl;

import com.adwiiseagent.domain.RetailFuelDay;
import com.adwiiseagent.repository.RetailFuelDayRepository;
import com.adwiiseagent.service.RetailAccessService;
import com.adwiiseagent.service.RetailFuelDayService;
import com.adwiiseagent.service.dto.RetailFuelDayDTO;
import com.adwiiseagent.service.mapper.RetailFuelDayMapper;
import java.time.ZonedDateTime;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RetailFuelDayServiceImpl implements RetailFuelDayService {

    private static final Logger LOG = LoggerFactory.getLogger(RetailFuelDayServiceImpl.class);

    private final RetailFuelDayRepository retailFuelDayRepository;
    private final RetailFuelDayMapper retailFuelDayMapper;
    private final RetailAccessService retailAccessService;

    public RetailFuelDayServiceImpl(
        RetailFuelDayRepository retailFuelDayRepository,
        RetailFuelDayMapper retailFuelDayMapper,
        RetailAccessService retailAccessService
    ) {
        this.retailFuelDayRepository = retailFuelDayRepository;
        this.retailFuelDayMapper = retailFuelDayMapper;
        this.retailAccessService = retailAccessService;
    }

    @Override
    public RetailFuelDayDTO save(RetailFuelDayDTO dto) {
        LOG.debug("Request to save RetailFuelDay : {}", dto);
        retailAccessService.assertAllLocationsScope();
        RetailFuelDay entity = retailFuelDayMapper.toEntity(dto);
        entity.setId(null);
        stamp(entity);
        entity = retailFuelDayRepository.save(entity);
        return retailFuelDayMapper.toDto(entity);
    }

    @Override
    public RetailFuelDayDTO update(RetailFuelDayDTO dto) {
        LOG.debug("Request to update RetailFuelDay : {}", dto);
        retailAccessService.assertAllLocationsScope();
        RetailFuelDay entity = retailFuelDayMapper.toEntity(dto);
        if (entity.getUpdatedAt() == null) {
            entity.setUpdatedAt(ZonedDateTime.now());
        }
        entity = retailFuelDayRepository.save(entity);
        return retailFuelDayMapper.toDto(entity);
    }

    @Override
    public Optional<RetailFuelDayDTO> partialUpdate(RetailFuelDayDTO dto) {
        LOG.debug("Request to partially update RetailFuelDay : {}", dto);
        retailAccessService.assertAllLocationsScope();
        return retailFuelDayRepository
            .findById(dto.getId())
            .map(existing -> {
                retailFuelDayMapper.partialUpdate(existing, dto);
                if (existing.getUpdatedAt() == null) {
                    existing.setUpdatedAt(ZonedDateTime.now());
                }
                return existing;
            })
            .map(retailFuelDayRepository::save)
            .map(retailFuelDayMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RetailFuelDayDTO> findOne(Long id) {
        LOG.debug("Request to get RetailFuelDay : {}", id);
        retailAccessService.assertModuleAccess();
        return retailFuelDayRepository
            .findById(id)
            .map(retailFuelDayMapper::toDto)
            .map(dto -> {
                retailAccessService.assertLocationIdAllowed(dto.getLocationId());
                return dto;
            });
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete RetailFuelDay : {}", id);
        retailAccessService.assertAllLocationsScope();
        retailFuelDayRepository.deleteById(id);
    }

    private static void stamp(RetailFuelDay entity) {
        ZonedDateTime now = ZonedDateTime.now();
        if (entity.getCreatedAt() == null) {
            entity.setCreatedAt(now);
        }
        if (entity.getUpdatedAt() == null) {
            entity.setUpdatedAt(now);
        }
    }
}
