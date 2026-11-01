package com.adwiiseagent.service.impl;

import com.adwiiseagent.domain.RetailUserRegionAssignment;
import com.adwiiseagent.repository.RetailUserRegionAssignmentRepository;
import com.adwiiseagent.service.RetailAccessService;
import com.adwiiseagent.service.RetailUserRegionAssignmentService;
import com.adwiiseagent.service.dto.RetailUserRegionAssignmentDTO;
import com.adwiiseagent.service.mapper.RetailUserRegionAssignmentMapper;
import java.time.ZonedDateTime;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RetailUserRegionAssignmentServiceImpl implements RetailUserRegionAssignmentService {

    private static final Logger LOG = LoggerFactory.getLogger(RetailUserRegionAssignmentServiceImpl.class);

    private final RetailUserRegionAssignmentRepository retailUserRegionAssignmentRepository;
    private final RetailUserRegionAssignmentMapper retailUserRegionAssignmentMapper;
    private final RetailAccessService retailAccessService;

    public RetailUserRegionAssignmentServiceImpl(
        RetailUserRegionAssignmentRepository retailUserRegionAssignmentRepository,
        RetailUserRegionAssignmentMapper retailUserRegionAssignmentMapper,
        RetailAccessService retailAccessService
    ) {
        this.retailUserRegionAssignmentRepository = retailUserRegionAssignmentRepository;
        this.retailUserRegionAssignmentMapper = retailUserRegionAssignmentMapper;
        this.retailAccessService = retailAccessService;
    }

    @Override
    public RetailUserRegionAssignmentDTO save(RetailUserRegionAssignmentDTO dto) {
        LOG.debug("Request to save RetailUserRegionAssignment : {}", dto);
        retailAccessService.assertAllLocationsScope();
        RetailUserRegionAssignment entity = retailUserRegionAssignmentMapper.toEntity(dto);
        entity.setId(null);
        stamp(entity);
        entity = retailUserRegionAssignmentRepository.save(entity);
        return retailUserRegionAssignmentMapper.toDto(entity);
    }

    @Override
    public RetailUserRegionAssignmentDTO update(RetailUserRegionAssignmentDTO dto) {
        LOG.debug("Request to update RetailUserRegionAssignment : {}", dto);
        retailAccessService.assertAllLocationsScope();
        RetailUserRegionAssignment entity = retailUserRegionAssignmentMapper.toEntity(dto);
        if (entity.getUpdatedAt() == null) {
            entity.setUpdatedAt(ZonedDateTime.now());
        }
        entity = retailUserRegionAssignmentRepository.save(entity);
        return retailUserRegionAssignmentMapper.toDto(entity);
    }

    @Override
    public Optional<RetailUserRegionAssignmentDTO> partialUpdate(RetailUserRegionAssignmentDTO dto) {
        LOG.debug("Request to partially update RetailUserRegionAssignment : {}", dto);
        retailAccessService.assertAllLocationsScope();
        return retailUserRegionAssignmentRepository
            .findById(dto.getId())
            .map(existing -> {
                retailUserRegionAssignmentMapper.partialUpdate(existing, dto);
                if (existing.getUpdatedAt() == null) {
                    existing.setUpdatedAt(ZonedDateTime.now());
                }
                return existing;
            })
            .map(retailUserRegionAssignmentRepository::save)
            .map(retailUserRegionAssignmentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RetailUserRegionAssignmentDTO> findOne(Long id) {
        LOG.debug("Request to get RetailUserRegionAssignment : {}", id);
        retailAccessService.assertModuleAccess();
        return retailUserRegionAssignmentRepository
            .findById(id)
            .map(retailUserRegionAssignmentMapper::toDto)
            .map(dto -> {
                assertOwnRow(dto.getUserProfileId());
                return dto;
            });
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete RetailUserRegionAssignment : {}", id);
        retailAccessService.assertAllLocationsScope();
        retailUserRegionAssignmentRepository.deleteById(id);
    }

    private void assertOwnRow(Long userProfileId) {
        if (retailAccessService.isUnrestrictedServiceAccess() || retailAccessService.getAllowedLocationIdsOrNull() == null) {
            return;
        }
        Long self = retailAccessService.currentUserProfileIdOrNull();
        if (self == null || !self.equals(userProfileId)) {
            throw new AccessDeniedException("Access denied for this region assignment.");
        }
    }

    private static void stamp(RetailUserRegionAssignment entity) {
        ZonedDateTime now = ZonedDateTime.now();
        if (entity.getCreatedAt() == null) {
            entity.setCreatedAt(now);
        }
        if (entity.getUpdatedAt() == null) {
            entity.setUpdatedAt(now);
        }
    }
}
