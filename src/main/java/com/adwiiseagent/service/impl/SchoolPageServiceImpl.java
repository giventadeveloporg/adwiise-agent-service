package com.adwiiseagent.service.impl;

import com.adwiiseagent.domain.SchoolPage;
import com.adwiiseagent.repository.SchoolPageRepository;
import com.adwiiseagent.service.SchoolPageService;
import com.adwiiseagent.service.dto.SchoolPageDTO;
import com.adwiiseagent.service.mapper.SchoolPageMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SchoolPageServiceImpl implements SchoolPageService {

    private static final Logger LOG = LoggerFactory.getLogger(SchoolPageServiceImpl.class);

    private final SchoolPageRepository schoolPageRepository;

    private final SchoolPageMapper schoolPageMapper;

    public SchoolPageServiceImpl(SchoolPageRepository schoolPageRepository, SchoolPageMapper schoolPageMapper) {
        this.schoolPageRepository = schoolPageRepository;
        this.schoolPageMapper = schoolPageMapper;
    }

    @Override
    public SchoolPageDTO save(SchoolPageDTO schoolPageDTO) {
        LOG.debug("Request to save SchoolPage : {}", schoolPageDTO);
        SchoolPage schoolPage = schoolPageMapper.toEntity(schoolPageDTO);
        if (schoolPage.getId() != null) {
            LOG.warn("SchoolPage has ID {} set during create operation. Clearing ID to force sequence generation.", schoolPage.getId());
            schoolPage.setId(null);
        }
        schoolPage = schoolPageRepository.save(schoolPage);
        return schoolPageMapper.toDto(schoolPage);
    }

    @Override
    public SchoolPageDTO update(SchoolPageDTO schoolPageDTO) {
        LOG.debug("Request to update SchoolPage : {}", schoolPageDTO);
        SchoolPage schoolPage = schoolPageMapper.toEntity(schoolPageDTO);
        schoolPage = schoolPageRepository.save(schoolPage);
        return schoolPageMapper.toDto(schoolPage);
    }

    @Override
    public Optional<SchoolPageDTO> partialUpdate(SchoolPageDTO schoolPageDTO) {
        LOG.debug("Request to partially update SchoolPage : {}", schoolPageDTO);
        return schoolPageRepository
            .findById(schoolPageDTO.getId())
            .map(existing -> {
                schoolPageMapper.partialUpdate(existing, schoolPageDTO);
                return existing;
            })
            .map(schoolPageRepository::save)
            .map(schoolPageMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SchoolPageDTO> findOne(Long id) {
        LOG.debug("Request to get SchoolPage : {}", id);
        return schoolPageRepository.findById(id).map(schoolPageMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete SchoolPage : {}", id);
        schoolPageRepository.deleteById(id);
    }
}
