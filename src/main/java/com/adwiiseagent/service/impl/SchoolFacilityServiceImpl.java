package com.adwiiseagent.service.impl;

import com.adwiiseagent.domain.SchoolFacility;
import com.adwiiseagent.repository.SchoolFacilityRepository;
import com.adwiiseagent.service.SchoolFacilityService;
import com.adwiiseagent.service.dto.SchoolFacilityDTO;
import com.adwiiseagent.service.mapper.SchoolFacilityMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SchoolFacilityServiceImpl implements SchoolFacilityService {

    private static final Logger LOG = LoggerFactory.getLogger(SchoolFacilityServiceImpl.class);

    private final SchoolFacilityRepository schoolFacilityRepository;

    private final SchoolFacilityMapper schoolFacilityMapper;

    public SchoolFacilityServiceImpl(SchoolFacilityRepository schoolFacilityRepository, SchoolFacilityMapper schoolFacilityMapper) {
        this.schoolFacilityRepository = schoolFacilityRepository;
        this.schoolFacilityMapper = schoolFacilityMapper;
    }

    @Override
    public SchoolFacilityDTO save(SchoolFacilityDTO schoolFacilityDTO) {
        LOG.debug("Request to save SchoolFacility : {}", schoolFacilityDTO);
        SchoolFacility schoolFacility = schoolFacilityMapper.toEntity(schoolFacilityDTO);
        if (schoolFacility.getId() != null) {
            LOG.warn(
                "SchoolFacility has ID {} set during create operation. Clearing ID to force sequence generation.",
                schoolFacility.getId()
            );
            schoolFacility.setId(null);
        }
        schoolFacility = schoolFacilityRepository.save(schoolFacility);
        return schoolFacilityMapper.toDto(schoolFacility);
    }

    @Override
    public SchoolFacilityDTO update(SchoolFacilityDTO schoolFacilityDTO) {
        LOG.debug("Request to update SchoolFacility : {}", schoolFacilityDTO);
        SchoolFacility schoolFacility = schoolFacilityMapper.toEntity(schoolFacilityDTO);
        schoolFacility = schoolFacilityRepository.save(schoolFacility);
        return schoolFacilityMapper.toDto(schoolFacility);
    }

    @Override
    public Optional<SchoolFacilityDTO> partialUpdate(SchoolFacilityDTO schoolFacilityDTO) {
        LOG.debug("Request to partially update SchoolFacility : {}", schoolFacilityDTO);
        return schoolFacilityRepository
            .findById(schoolFacilityDTO.getId())
            .map(existing -> {
                schoolFacilityMapper.partialUpdate(existing, schoolFacilityDTO);
                return existing;
            })
            .map(schoolFacilityRepository::save)
            .map(schoolFacilityMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SchoolFacilityDTO> findOne(Long id) {
        LOG.debug("Request to get SchoolFacility : {}", id);
        return schoolFacilityRepository.findById(id).map(schoolFacilityMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete SchoolFacility : {}", id);
        schoolFacilityRepository.deleteById(id);
    }
}
