package com.adwiiseagent.service.impl;

import com.adwiiseagent.domain.SchoolFeePayment;
import com.adwiiseagent.repository.SchoolFeePaymentRepository;
import com.adwiiseagent.service.SchoolFeePaymentService;
import com.adwiiseagent.service.dto.SchoolFeePaymentDTO;
import com.adwiiseagent.service.mapper.SchoolFeePaymentMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SchoolFeePaymentServiceImpl implements SchoolFeePaymentService {

    private static final Logger LOG = LoggerFactory.getLogger(SchoolFeePaymentServiceImpl.class);

    private final SchoolFeePaymentRepository schoolFeePaymentRepository;

    private final SchoolFeePaymentMapper schoolFeePaymentMapper;

    public SchoolFeePaymentServiceImpl(
        SchoolFeePaymentRepository schoolFeePaymentRepository,
        SchoolFeePaymentMapper schoolFeePaymentMapper
    ) {
        this.schoolFeePaymentRepository = schoolFeePaymentRepository;
        this.schoolFeePaymentMapper = schoolFeePaymentMapper;
    }

    @Override
    public SchoolFeePaymentDTO save(SchoolFeePaymentDTO schoolFeePaymentDTO) {
        LOG.debug("Request to save SchoolFeePayment : {}", schoolFeePaymentDTO);
        SchoolFeePayment schoolFeePayment = schoolFeePaymentMapper.toEntity(schoolFeePaymentDTO);
        if (schoolFeePayment.getId() != null) {
            LOG.warn(
                "SchoolFeePayment has ID {} set during create operation. Clearing ID to force sequence generation.",
                schoolFeePayment.getId()
            );
            schoolFeePayment.setId(null);
        }
        schoolFeePayment = schoolFeePaymentRepository.save(schoolFeePayment);
        return schoolFeePaymentMapper.toDto(schoolFeePayment);
    }

    @Override
    public SchoolFeePaymentDTO update(SchoolFeePaymentDTO schoolFeePaymentDTO) {
        LOG.debug("Request to update SchoolFeePayment : {}", schoolFeePaymentDTO);
        SchoolFeePayment schoolFeePayment = schoolFeePaymentMapper.toEntity(schoolFeePaymentDTO);
        schoolFeePayment = schoolFeePaymentRepository.save(schoolFeePayment);
        return schoolFeePaymentMapper.toDto(schoolFeePayment);
    }

    @Override
    public Optional<SchoolFeePaymentDTO> partialUpdate(SchoolFeePaymentDTO schoolFeePaymentDTO) {
        LOG.debug("Request to partially update SchoolFeePayment : {}", schoolFeePaymentDTO);
        return schoolFeePaymentRepository
            .findById(schoolFeePaymentDTO.getId())
            .map(existing -> {
                schoolFeePaymentMapper.partialUpdate(existing, schoolFeePaymentDTO);
                return existing;
            })
            .map(schoolFeePaymentRepository::save)
            .map(schoolFeePaymentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SchoolFeePaymentDTO> findOne(Long id) {
        LOG.debug("Request to get SchoolFeePayment : {}", id);
        return schoolFeePaymentRepository.findById(id).map(schoolFeePaymentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete SchoolFeePayment : {}", id);
        schoolFeePaymentRepository.deleteById(id);
    }
}
