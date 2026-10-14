package com.adwiiseagent.service.impl;

import com.adwiiseagent.domain.WhatsAppLog;
import com.adwiiseagent.repository.WhatsAppLogRepository;
import com.adwiiseagent.service.WhatsAppLogService;
import com.adwiiseagent.service.dto.WhatsAppLogDTO;
import com.adwiiseagent.service.mapper.WhatsAppLogMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.adwiiseagent.domain.WhatsAppLog}.
 */
@Service
@Transactional
public class WhatsAppLogServiceImpl implements WhatsAppLogService {

    private static final Logger LOG = LoggerFactory.getLogger(WhatsAppLogServiceImpl.class);

    private final WhatsAppLogRepository whatsAppLogRepository;

    private final WhatsAppLogMapper whatsAppLogMapper;

    public WhatsAppLogServiceImpl(WhatsAppLogRepository whatsAppLogRepository, WhatsAppLogMapper whatsAppLogMapper) {
        this.whatsAppLogRepository = whatsAppLogRepository;
        this.whatsAppLogMapper = whatsAppLogMapper;
    }

    @Override
    public WhatsAppLogDTO save(WhatsAppLogDTO whatsAppLogDTO) {
        LOG.debug("Request to save WhatsAppLog : {}", whatsAppLogDTO);
        WhatsAppLog whatsAppLog = whatsAppLogMapper.toEntity(whatsAppLogDTO);
        whatsAppLog = whatsAppLogRepository.save(whatsAppLog);
        return whatsAppLogMapper.toDto(whatsAppLog);
    }

    @Override
    public WhatsAppLogDTO update(WhatsAppLogDTO whatsAppLogDTO) {
        LOG.debug("Request to update WhatsAppLog : {}", whatsAppLogDTO);
        WhatsAppLog whatsAppLog = whatsAppLogMapper.toEntity(whatsAppLogDTO);
        whatsAppLog = whatsAppLogRepository.save(whatsAppLog);
        return whatsAppLogMapper.toDto(whatsAppLog);
    }

    @Override
    public Optional<WhatsAppLogDTO> partialUpdate(WhatsAppLogDTO whatsAppLogDTO) {
        LOG.debug("Request to partially update WhatsAppLog : {}", whatsAppLogDTO);

        return whatsAppLogRepository
            .findById(whatsAppLogDTO.getId())
            .map(existingWhatsAppLog -> {
                whatsAppLogMapper.partialUpdate(existingWhatsAppLog, whatsAppLogDTO);

                return existingWhatsAppLog;
            })
            .map(whatsAppLogRepository::save)
            .map(whatsAppLogMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<WhatsAppLogDTO> findOne(Long id) {
        LOG.debug("Request to get WhatsAppLog : {}", id);
        return whatsAppLogRepository.findById(id).map(whatsAppLogMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete WhatsAppLog : {}", id);
        whatsAppLogRepository.deleteById(id);
    }
}
