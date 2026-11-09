package com.adwiiseagent.service.impl;

import com.adwiiseagent.domain.SchoolHighlight;
import com.adwiiseagent.repository.SchoolHighlightRepository;
import com.adwiiseagent.service.SchoolHighlightService;
import com.adwiiseagent.service.dto.SchoolHighlightDTO;
import com.adwiiseagent.service.mapper.SchoolHighlightMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SchoolHighlightServiceImpl implements SchoolHighlightService {

    private static final Logger LOG = LoggerFactory.getLogger(SchoolHighlightServiceImpl.class);

    private final SchoolHighlightRepository schoolHighlightRepository;

    private final SchoolHighlightMapper schoolHighlightMapper;

    public SchoolHighlightServiceImpl(SchoolHighlightRepository schoolHighlightRepository, SchoolHighlightMapper schoolHighlightMapper) {
        this.schoolHighlightRepository = schoolHighlightRepository;
        this.schoolHighlightMapper = schoolHighlightMapper;
    }

    @Override
    public SchoolHighlightDTO save(SchoolHighlightDTO schoolHighlightDTO) {
        LOG.debug("Request to save SchoolHighlight : {}", schoolHighlightDTO);
        SchoolHighlight schoolHighlight = schoolHighlightMapper.toEntity(schoolHighlightDTO);
        if (schoolHighlight.getId() != null) {
            LOG.warn(
                "SchoolHighlight has ID {} set during create operation. Clearing ID to force sequence generation.",
                schoolHighlight.getId()
            );
            schoolHighlight.setId(null);
        }
        schoolHighlight = schoolHighlightRepository.save(schoolHighlight);
        return schoolHighlightMapper.toDto(schoolHighlight);
    }

    @Override
    public SchoolHighlightDTO update(SchoolHighlightDTO schoolHighlightDTO) {
        LOG.debug("Request to update SchoolHighlight : {}", schoolHighlightDTO);
        SchoolHighlight schoolHighlight = schoolHighlightMapper.toEntity(schoolHighlightDTO);
        schoolHighlight = schoolHighlightRepository.save(schoolHighlight);
        return schoolHighlightMapper.toDto(schoolHighlight);
    }

    @Override
    public Optional<SchoolHighlightDTO> partialUpdate(SchoolHighlightDTO schoolHighlightDTO) {
        LOG.debug("Request to partially update SchoolHighlight : {}", schoolHighlightDTO);
        return schoolHighlightRepository
            .findById(schoolHighlightDTO.getId())
            .map(existing -> {
                schoolHighlightMapper.partialUpdate(existing, schoolHighlightDTO);
                return existing;
            })
            .map(schoolHighlightRepository::save)
            .map(schoolHighlightMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SchoolHighlightDTO> findOne(Long id) {
        LOG.debug("Request to get SchoolHighlight : {}", id);
        return schoolHighlightRepository.findById(id).map(schoolHighlightMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete SchoolHighlight : {}", id);
        schoolHighlightRepository.deleteById(id);
    }
}
