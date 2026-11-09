package com.adwiiseagent.service.impl;

import com.adwiiseagent.domain.SchoolFeeInvoice;
import com.adwiiseagent.repository.SchoolFeeInvoiceRepository;
import com.adwiiseagent.service.SchoolFeeInvoiceService;
import com.adwiiseagent.service.dto.SchoolFeeInvoiceDTO;
import com.adwiiseagent.service.mapper.SchoolFeeInvoiceMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SchoolFeeInvoiceServiceImpl implements SchoolFeeInvoiceService {

    private static final Logger LOG = LoggerFactory.getLogger(SchoolFeeInvoiceServiceImpl.class);

    private final SchoolFeeInvoiceRepository schoolFeeInvoiceRepository;

    private final SchoolFeeInvoiceMapper schoolFeeInvoiceMapper;

    public SchoolFeeInvoiceServiceImpl(
        SchoolFeeInvoiceRepository schoolFeeInvoiceRepository,
        SchoolFeeInvoiceMapper schoolFeeInvoiceMapper
    ) {
        this.schoolFeeInvoiceRepository = schoolFeeInvoiceRepository;
        this.schoolFeeInvoiceMapper = schoolFeeInvoiceMapper;
    }

    @Override
    public SchoolFeeInvoiceDTO save(SchoolFeeInvoiceDTO schoolFeeInvoiceDTO) {
        LOG.debug("Request to save SchoolFeeInvoice : {}", schoolFeeInvoiceDTO);
        SchoolFeeInvoice schoolFeeInvoice = schoolFeeInvoiceMapper.toEntity(schoolFeeInvoiceDTO);
        if (schoolFeeInvoice.getId() != null) {
            LOG.warn(
                "SchoolFeeInvoice has ID {} set during create operation. Clearing ID to force sequence generation.",
                schoolFeeInvoice.getId()
            );
            schoolFeeInvoice.setId(null);
        }
        schoolFeeInvoice = schoolFeeInvoiceRepository.save(schoolFeeInvoice);
        return schoolFeeInvoiceMapper.toDto(schoolFeeInvoice);
    }

    @Override
    public SchoolFeeInvoiceDTO update(SchoolFeeInvoiceDTO schoolFeeInvoiceDTO) {
        LOG.debug("Request to update SchoolFeeInvoice : {}", schoolFeeInvoiceDTO);
        SchoolFeeInvoice schoolFeeInvoice = schoolFeeInvoiceMapper.toEntity(schoolFeeInvoiceDTO);
        schoolFeeInvoice = schoolFeeInvoiceRepository.save(schoolFeeInvoice);
        return schoolFeeInvoiceMapper.toDto(schoolFeeInvoice);
    }

    @Override
    public Optional<SchoolFeeInvoiceDTO> partialUpdate(SchoolFeeInvoiceDTO schoolFeeInvoiceDTO) {
        LOG.debug("Request to partially update SchoolFeeInvoice : {}", schoolFeeInvoiceDTO);
        return schoolFeeInvoiceRepository
            .findById(schoolFeeInvoiceDTO.getId())
            .map(existing -> {
                schoolFeeInvoiceMapper.partialUpdate(existing, schoolFeeInvoiceDTO);
                return existing;
            })
            .map(schoolFeeInvoiceRepository::save)
            .map(schoolFeeInvoiceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SchoolFeeInvoiceDTO> findOne(Long id) {
        LOG.debug("Request to get SchoolFeeInvoice : {}", id);
        return schoolFeeInvoiceRepository.findById(id).map(schoolFeeInvoiceMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete SchoolFeeInvoice : {}", id);
        schoolFeeInvoiceRepository.deleteById(id);
    }
}
