package com.adwiiseagent.service.impl;

import com.adwiiseagent.domain.SchoolDepartment;
import com.adwiiseagent.repository.SchoolDepartmentRepository;
import com.adwiiseagent.service.SchoolDepartmentService;
import com.adwiiseagent.service.dto.SchoolDepartmentDTO;
import com.adwiiseagent.service.mapper.SchoolDepartmentMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SchoolDepartmentServiceImpl implements SchoolDepartmentService {

    private static final Logger LOG = LoggerFactory.getLogger(SchoolDepartmentServiceImpl.class);

    private final SchoolDepartmentRepository schoolDepartmentRepository;

    private final SchoolDepartmentMapper schoolDepartmentMapper;

    public SchoolDepartmentServiceImpl(
        SchoolDepartmentRepository schoolDepartmentRepository,
        SchoolDepartmentMapper schoolDepartmentMapper
    ) {
        this.schoolDepartmentRepository = schoolDepartmentRepository;
        this.schoolDepartmentMapper = schoolDepartmentMapper;
    }

    @Override
    public SchoolDepartmentDTO save(SchoolDepartmentDTO schoolDepartmentDTO) {
        LOG.debug("Request to save SchoolDepartment : {}", schoolDepartmentDTO);
        SchoolDepartment schoolDepartment = schoolDepartmentMapper.toEntity(schoolDepartmentDTO);
        if (schoolDepartment.getId() != null) {
            LOG.warn(
                "SchoolDepartment has ID {} set during create operation. Clearing ID to force sequence generation.",
                schoolDepartment.getId()
            );
            schoolDepartment.setId(null);
        }
        schoolDepartment = schoolDepartmentRepository.save(schoolDepartment);
        return schoolDepartmentMapper.toDto(schoolDepartment);
    }

    @Override
    public SchoolDepartmentDTO update(SchoolDepartmentDTO schoolDepartmentDTO) {
        LOG.debug("Request to update SchoolDepartment : {}", schoolDepartmentDTO);
        SchoolDepartment schoolDepartment = schoolDepartmentMapper.toEntity(schoolDepartmentDTO);
        schoolDepartment = schoolDepartmentRepository.save(schoolDepartment);
        return schoolDepartmentMapper.toDto(schoolDepartment);
    }

    @Override
    public Optional<SchoolDepartmentDTO> partialUpdate(SchoolDepartmentDTO schoolDepartmentDTO) {
        LOG.debug("Request to partially update SchoolDepartment : {}", schoolDepartmentDTO);
        return schoolDepartmentRepository
            .findById(schoolDepartmentDTO.getId())
            .map(existing -> {
                schoolDepartmentMapper.partialUpdate(existing, schoolDepartmentDTO);
                return existing;
            })
            .map(schoolDepartmentRepository::save)
            .map(schoolDepartmentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SchoolDepartmentDTO> findOne(Long id) {
        LOG.debug("Request to get SchoolDepartment : {}", id);
        return schoolDepartmentRepository.findById(id).map(schoolDepartmentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete SchoolDepartment : {}", id);
        schoolDepartmentRepository.deleteById(id);
    }
}
