package com.adwiiseagent.service.impl;

import com.adwiiseagent.domain.RetailDepartmentDay;
import com.adwiiseagent.repository.RetailDepartmentDayRepository;
import com.adwiiseagent.service.RetailAccessService;
import com.adwiiseagent.service.RetailDepartmentDayService;
import com.adwiiseagent.service.dto.RetailDepartmentDayDTO;
import com.adwiiseagent.service.mapper.RetailDepartmentDayMapper;
import java.time.ZonedDateTime;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RetailDepartmentDayServiceImpl implements RetailDepartmentDayService {

    private static final Logger LOG = LoggerFactory.getLogger(RetailDepartmentDayServiceImpl.class);

    private final RetailDepartmentDayRepository retailDepartmentDayRepository;
    private final RetailDepartmentDayMapper retailDepartmentDayMapper;
    private final RetailAccessService retailAccessService;

    public RetailDepartmentDayServiceImpl(
        RetailDepartmentDayRepository retailDepartmentDayRepository,
        RetailDepartmentDayMapper retailDepartmentDayMapper,
        RetailAccessService retailAccessService
    ) {
        this.retailDepartmentDayRepository = retailDepartmentDayRepository;
        this.retailDepartmentDayMapper = retailDepartmentDayMapper;
        this.retailAccessService = retailAccessService;
    }

    @Override
    public RetailDepartmentDayDTO save(RetailDepartmentDayDTO dto) {
        LOG.debug("Request to save RetailDepartmentDay : {}", dto);
        retailAccessService.assertAllLocationsScope();
        RetailDepartmentDay entity = retailDepartmentDayMapper.toEntity(dto);
        entity.setId(null);
        stamp(entity);
        entity = retailDepartmentDayRepository.save(entity);
        return retailDepartmentDayMapper.toDto(entity);
    }

    @Override
    public RetailDepartmentDayDTO update(RetailDepartmentDayDTO dto) {
        LOG.debug("Request to update RetailDepartmentDay : {}", dto);
        retailAccessService.assertAllLocationsScope();
        RetailDepartmentDay entity = retailDepartmentDayMapper.toEntity(dto);
        if (entity.getUpdatedAt() == null) {
            entity.setUpdatedAt(ZonedDateTime.now());
        }
        entity = retailDepartmentDayRepository.save(entity);
        return retailDepartmentDayMapper.toDto(entity);
    }

    @Override
    public Optional<RetailDepartmentDayDTO> partialUpdate(RetailDepartmentDayDTO dto) {
        LOG.debug("Request to partially update RetailDepartmentDay : {}", dto);
        retailAccessService.assertAllLocationsScope();
        return retailDepartmentDayRepository
            .findById(dto.getId())
            .map(existing -> {
                retailDepartmentDayMapper.partialUpdate(existing, dto);
                if (existing.getUpdatedAt() == null) {
                    existing.setUpdatedAt(ZonedDateTime.now());
                }
                return existing;
            })
            .map(retailDepartmentDayRepository::save)
            .map(retailDepartmentDayMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RetailDepartmentDayDTO> findOne(Long id) {
        LOG.debug("Request to get RetailDepartmentDay : {}", id);
        retailAccessService.assertModuleAccess();
        return retailDepartmentDayRepository
            .findById(id)
            .map(retailDepartmentDayMapper::toDto)
            .map(dto -> {
                retailAccessService.assertLocationIdAllowed(dto.getLocationId());
                return dto;
            });
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete RetailDepartmentDay : {}", id);
        retailAccessService.assertAllLocationsScope();
        retailDepartmentDayRepository.deleteById(id);
    }

    private static void stamp(RetailDepartmentDay entity) {
        ZonedDateTime now = ZonedDateTime.now();
        if (entity.getCreatedAt() == null) {
            entity.setCreatedAt(now);
        }
        if (entity.getUpdatedAt() == null) {
            entity.setUpdatedAt(now);
        }
    }
}
