package com.eventsitemanager.service.impl;

import com.eventsitemanager.domain.RetailUserLocationAssignment;
import com.eventsitemanager.repository.RetailUserLocationAssignmentRepository;
import com.eventsitemanager.service.RetailAccessService;
import com.eventsitemanager.service.RetailUserLocationAssignmentService;
import com.eventsitemanager.service.dto.RetailUserLocationAssignmentDTO;
import com.eventsitemanager.service.mapper.RetailUserLocationAssignmentMapper;
import java.time.ZonedDateTime;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RetailUserLocationAssignmentServiceImpl implements RetailUserLocationAssignmentService {

    private static final Logger LOG = LoggerFactory.getLogger(RetailUserLocationAssignmentServiceImpl.class);

    private final RetailUserLocationAssignmentRepository retailUserLocationAssignmentRepository;
    private final RetailUserLocationAssignmentMapper retailUserLocationAssignmentMapper;
    private final RetailAccessService retailAccessService;

    public RetailUserLocationAssignmentServiceImpl(
        RetailUserLocationAssignmentRepository retailUserLocationAssignmentRepository,
        RetailUserLocationAssignmentMapper retailUserLocationAssignmentMapper,
        RetailAccessService retailAccessService
    ) {
        this.retailUserLocationAssignmentRepository = retailUserLocationAssignmentRepository;
        this.retailUserLocationAssignmentMapper = retailUserLocationAssignmentMapper;
        this.retailAccessService = retailAccessService;
    }

    @Override
    public RetailUserLocationAssignmentDTO save(RetailUserLocationAssignmentDTO dto) {
        LOG.debug("Request to save RetailUserLocationAssignment : {}", dto);
        retailAccessService.assertAllLocationsScope();
        RetailUserLocationAssignment entity = retailUserLocationAssignmentMapper.toEntity(dto);
        entity.setId(null);
        stamp(entity);
        entity = retailUserLocationAssignmentRepository.save(entity);
        return retailUserLocationAssignmentMapper.toDto(entity);
    }

    @Override
    public RetailUserLocationAssignmentDTO update(RetailUserLocationAssignmentDTO dto) {
        LOG.debug("Request to update RetailUserLocationAssignment : {}", dto);
        retailAccessService.assertAllLocationsScope();
        RetailUserLocationAssignment entity = retailUserLocationAssignmentMapper.toEntity(dto);
        if (entity.getUpdatedAt() == null) {
            entity.setUpdatedAt(ZonedDateTime.now());
        }
        entity = retailUserLocationAssignmentRepository.save(entity);
        return retailUserLocationAssignmentMapper.toDto(entity);
    }

    @Override
    public Optional<RetailUserLocationAssignmentDTO> partialUpdate(RetailUserLocationAssignmentDTO dto) {
        LOG.debug("Request to partially update RetailUserLocationAssignment : {}", dto);
        retailAccessService.assertAllLocationsScope();
        return retailUserLocationAssignmentRepository
            .findById(dto.getId())
            .map(existing -> {
                retailUserLocationAssignmentMapper.partialUpdate(existing, dto);
                if (existing.getUpdatedAt() == null) {
                    existing.setUpdatedAt(ZonedDateTime.now());
                }
                return existing;
            })
            .map(retailUserLocationAssignmentRepository::save)
            .map(retailUserLocationAssignmentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RetailUserLocationAssignmentDTO> findOne(Long id) {
        LOG.debug("Request to get RetailUserLocationAssignment : {}", id);
        retailAccessService.assertModuleAccess();
        return retailUserLocationAssignmentRepository
            .findById(id)
            .map(retailUserLocationAssignmentMapper::toDto)
            .map(dto -> {
                retailAccessService.assertLocationIdAllowed(dto.getLocationId());
                return dto;
            });
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete RetailUserLocationAssignment : {}", id);
        retailAccessService.assertAllLocationsScope();
        retailUserLocationAssignmentRepository.deleteById(id);
    }

    private static void stamp(RetailUserLocationAssignment entity) {
        ZonedDateTime now = ZonedDateTime.now();
        if (entity.getCreatedAt() == null) {
            entity.setCreatedAt(now);
        }
        if (entity.getUpdatedAt() == null) {
            entity.setUpdatedAt(now);
        }
    }
}
