package com.adwiiseagent.service.impl;

import com.adwiiseagent.domain.SchoolActivity;
import com.adwiiseagent.repository.SchoolActivityRepository;
import com.adwiiseagent.service.SchoolActivityService;
import com.adwiiseagent.service.dto.SchoolActivityDTO;
import com.adwiiseagent.service.mapper.SchoolActivityMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SchoolActivityServiceImpl implements SchoolActivityService {

    private static final Logger LOG = LoggerFactory.getLogger(SchoolActivityServiceImpl.class);

    private final SchoolActivityRepository schoolActivityRepository;

    private final SchoolActivityMapper schoolActivityMapper;

    public SchoolActivityServiceImpl(SchoolActivityRepository schoolActivityRepository, SchoolActivityMapper schoolActivityMapper) {
        this.schoolActivityRepository = schoolActivityRepository;
        this.schoolActivityMapper = schoolActivityMapper;
    }

    @Override
    public SchoolActivityDTO save(SchoolActivityDTO schoolActivityDTO) {
        LOG.debug("Request to save SchoolActivity : {}", schoolActivityDTO);
        SchoolActivity schoolActivity = schoolActivityMapper.toEntity(schoolActivityDTO);
        if (schoolActivity.getId() != null) {
            LOG.warn(
                "SchoolActivity has ID {} set during create operation. Clearing ID to force sequence generation.",
                schoolActivity.getId()
            );
            schoolActivity.setId(null);
        }
        schoolActivity = schoolActivityRepository.save(schoolActivity);
        return schoolActivityMapper.toDto(schoolActivity);
    }

    @Override
    public SchoolActivityDTO update(SchoolActivityDTO schoolActivityDTO) {
        LOG.debug("Request to update SchoolActivity : {}", schoolActivityDTO);
        SchoolActivity schoolActivity = schoolActivityMapper.toEntity(schoolActivityDTO);
        schoolActivity = schoolActivityRepository.save(schoolActivity);
        return schoolActivityMapper.toDto(schoolActivity);
    }

    @Override
    public Optional<SchoolActivityDTO> partialUpdate(SchoolActivityDTO schoolActivityDTO) {
        LOG.debug("Request to partially update SchoolActivity : {}", schoolActivityDTO);
        return schoolActivityRepository
            .findById(schoolActivityDTO.getId())
            .map(existing -> {
                schoolActivityMapper.partialUpdate(existing, schoolActivityDTO);
                return existing;
            })
            .map(schoolActivityRepository::save)
            .map(schoolActivityMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SchoolActivityDTO> findOne(Long id) {
        LOG.debug("Request to get SchoolActivity : {}", id);
        return schoolActivityRepository.findById(id).map(schoolActivityMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete SchoolActivity : {}", id);
        schoolActivityRepository.deleteById(id);
    }
}
