package com.adwiiseagent.service.impl;

import com.adwiiseagent.domain.RetailRecommendation;
import com.adwiiseagent.repository.RetailRecommendationRepository;
import com.adwiiseagent.service.RecommendationCategoryLookup;
import com.adwiiseagent.service.RetailAccessService;
import com.adwiiseagent.service.RetailRecommendationService;
import com.adwiiseagent.service.dto.RetailRecommendationDTO;
import com.adwiiseagent.service.mapper.RetailRecommendationMapper;
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
    private final RecommendationCategoryLookup recommendationCategoryLookup;

    public RetailRecommendationServiceImpl(
        RetailRecommendationRepository retailRecommendationRepository,
        RetailRecommendationMapper retailRecommendationMapper,
        RetailAccessService retailAccessService,
        RecommendationCategoryLookup recommendationCategoryLookup
    ) {
        this.retailRecommendationRepository = retailRecommendationRepository;
        this.retailRecommendationMapper = retailRecommendationMapper;
        this.retailAccessService = retailAccessService;
        this.recommendationCategoryLookup = recommendationCategoryLookup;
    }

    @Override
    public RetailRecommendationDTO save(RetailRecommendationDTO dto) {
        LOG.debug("Request to save RetailRecommendation : {}", dto);
        retailAccessService.assertRecommendationAccess(dto.getLocationId());
        recommendationCategoryLookup.requireActive(dto.getCategory());
        RetailRecommendation entity = retailRecommendationMapper.toEntity(dto);
        entity.setId(null);
        stamp(entity);
        entity = retailRecommendationRepository.save(entity);
        return retailRecommendationMapper.toDto(entity);
    }

    @Override
    public RetailRecommendationDTO update(RetailRecommendationDTO dto) {
        LOG.debug("Request to update RetailRecommendation : {}", dto);
        retailRecommendationRepository
            .findById(dto.getId())
            .ifPresent(existing -> retailAccessService.assertRecommendationAccess(existing.getLocationId()));
        if (dto.getLocationId() != null) {
            retailAccessService.assertRecommendationAccess(dto.getLocationId());
        }
        recommendationCategoryLookup.requireActive(dto.getCategory());
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
        return retailRecommendationRepository
            .findById(dto.getId())
            .map(existing -> {
                retailAccessService.assertRecommendationAccess(existing.getLocationId());
                if (dto.getLocationId() != null) {
                    retailAccessService.assertRecommendationAccess(dto.getLocationId());
                }
                retailRecommendationMapper.partialUpdate(existing, dto);
                if (dto.getCategory() != null) {
                    recommendationCategoryLookup.requireActive(existing.getCategory());
                }
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
