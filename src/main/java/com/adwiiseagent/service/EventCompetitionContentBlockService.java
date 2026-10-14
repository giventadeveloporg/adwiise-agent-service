package com.adwiiseagent.service;

import com.adwiiseagent.service.dto.EventCompetitionContentBlockDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.adwiiseagent.domain.EventCompetitionContentBlock}.
 */
public interface EventCompetitionContentBlockService {
    EventCompetitionContentBlockDTO save(EventCompetitionContentBlockDTO eventCompetitionContentBlockDTO);

    EventCompetitionContentBlockDTO update(EventCompetitionContentBlockDTO eventCompetitionContentBlockDTO);

    Optional<EventCompetitionContentBlockDTO> partialUpdate(EventCompetitionContentBlockDTO eventCompetitionContentBlockDTO);

    Page<EventCompetitionContentBlockDTO> findAll(Pageable pageable);

    Optional<EventCompetitionContentBlockDTO> findOne(Long id);

    void delete(Long id);
}
