package com.adwiiseagent.service.impl;

import com.adwiiseagent.domain.EventLiveUpdateAttachment;
import com.adwiiseagent.repository.EventLiveUpdateAttachmentRepository;
import com.adwiiseagent.service.EventLiveUpdateAttachmentService;
import com.adwiiseagent.service.dto.EventLiveUpdateAttachmentDTO;
import com.adwiiseagent.service.mapper.EventLiveUpdateAttachmentMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.adwiiseagent.domain.EventLiveUpdateAttachment}.
 */
@Service
@Transactional
public class EventLiveUpdateAttachmentServiceImpl implements EventLiveUpdateAttachmentService {

    private static final Logger LOG = LoggerFactory.getLogger(EventLiveUpdateAttachmentServiceImpl.class);

    private final EventLiveUpdateAttachmentRepository eventLiveUpdateAttachmentRepository;

    private final EventLiveUpdateAttachmentMapper eventLiveUpdateAttachmentMapper;

    public EventLiveUpdateAttachmentServiceImpl(
        EventLiveUpdateAttachmentRepository eventLiveUpdateAttachmentRepository,
        EventLiveUpdateAttachmentMapper eventLiveUpdateAttachmentMapper
    ) {
        this.eventLiveUpdateAttachmentRepository = eventLiveUpdateAttachmentRepository;
        this.eventLiveUpdateAttachmentMapper = eventLiveUpdateAttachmentMapper;
    }

    @Override
    public EventLiveUpdateAttachmentDTO save(EventLiveUpdateAttachmentDTO eventLiveUpdateAttachmentDTO) {
        LOG.debug("Request to save EventLiveUpdateAttachment : {}", eventLiveUpdateAttachmentDTO);
        EventLiveUpdateAttachment eventLiveUpdateAttachment = eventLiveUpdateAttachmentMapper.toEntity(eventLiveUpdateAttachmentDTO);
        eventLiveUpdateAttachment = eventLiveUpdateAttachmentRepository.save(eventLiveUpdateAttachment);
        return eventLiveUpdateAttachmentMapper.toDto(eventLiveUpdateAttachment);
    }

    @Override
    public EventLiveUpdateAttachmentDTO update(EventLiveUpdateAttachmentDTO eventLiveUpdateAttachmentDTO) {
        LOG.debug("Request to update EventLiveUpdateAttachment : {}", eventLiveUpdateAttachmentDTO);
        EventLiveUpdateAttachment eventLiveUpdateAttachment = eventLiveUpdateAttachmentMapper.toEntity(eventLiveUpdateAttachmentDTO);
        eventLiveUpdateAttachment = eventLiveUpdateAttachmentRepository.save(eventLiveUpdateAttachment);
        return eventLiveUpdateAttachmentMapper.toDto(eventLiveUpdateAttachment);
    }

    @Override
    public Optional<EventLiveUpdateAttachmentDTO> partialUpdate(EventLiveUpdateAttachmentDTO eventLiveUpdateAttachmentDTO) {
        LOG.debug("Request to partially update EventLiveUpdateAttachment : {}", eventLiveUpdateAttachmentDTO);

        return eventLiveUpdateAttachmentRepository
            .findById(eventLiveUpdateAttachmentDTO.getId())
            .map(existingEventLiveUpdateAttachment -> {
                eventLiveUpdateAttachmentMapper.partialUpdate(existingEventLiveUpdateAttachment, eventLiveUpdateAttachmentDTO);

                return existingEventLiveUpdateAttachment;
            })
            .map(eventLiveUpdateAttachmentRepository::save)
            .map(eventLiveUpdateAttachmentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EventLiveUpdateAttachmentDTO> findOne(Long id) {
        LOG.debug("Request to get EventLiveUpdateAttachment : {}", id);
        return eventLiveUpdateAttachmentRepository.findById(id).map(eventLiveUpdateAttachmentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete EventLiveUpdateAttachment : {}", id);
        eventLiveUpdateAttachmentRepository.deleteById(id);
    }
}
