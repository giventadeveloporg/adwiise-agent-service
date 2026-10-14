package com.adwiiseagent.service.impl;

import com.adwiiseagent.domain.EmailLog;
import com.adwiiseagent.repository.EmailLogRepository;
import com.adwiiseagent.service.EmailLogService;
import com.adwiiseagent.service.dto.EmailLogDTO;
import com.adwiiseagent.service.mapper.EmailLogMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.adwiiseagent.domain.EmailLog}.
 */
@Service
@Transactional
public class EmailLogServiceImpl implements EmailLogService {

    private static final Logger LOG = LoggerFactory.getLogger(EmailLogServiceImpl.class);

    private final EmailLogRepository emailLogRepository;

    private final EmailLogMapper emailLogMapper;

    public EmailLogServiceImpl(EmailLogRepository emailLogRepository, EmailLogMapper emailLogMapper) {
        this.emailLogRepository = emailLogRepository;
        this.emailLogMapper = emailLogMapper;
    }

    @Override
    public EmailLogDTO save(EmailLogDTO emailLogDTO) {
        LOG.debug("Request to save EmailLog : {}", emailLogDTO);
        EmailLog emailLog = emailLogMapper.toEntity(emailLogDTO);
        emailLog = emailLogRepository.save(emailLog);
        return emailLogMapper.toDto(emailLog);
    }

    @Override
    public EmailLogDTO update(EmailLogDTO emailLogDTO) {
        LOG.debug("Request to update EmailLog : {}", emailLogDTO);
        EmailLog emailLog = emailLogMapper.toEntity(emailLogDTO);
        emailLog = emailLogRepository.save(emailLog);
        return emailLogMapper.toDto(emailLog);
    }

    @Override
    public Optional<EmailLogDTO> partialUpdate(EmailLogDTO emailLogDTO) {
        LOG.debug("Request to partially update EmailLog : {}", emailLogDTO);

        return emailLogRepository
            .findById(emailLogDTO.getId())
            .map(existingEmailLog -> {
                emailLogMapper.partialUpdate(existingEmailLog, emailLogDTO);

                return existingEmailLog;
            })
            .map(emailLogRepository::save)
            .map(emailLogMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EmailLogDTO> findOne(Long id) {
        LOG.debug("Request to get EmailLog : {}", id);
        return emailLogRepository.findById(id).map(emailLogMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete EmailLog : {}", id);
        emailLogRepository.deleteById(id);
    }
}
