package com.adwiiseagent.service;

import com.adwiiseagent.service.dto.EventCompetitionRegistrationDTO;
import com.adwiiseagent.service.dto.TeamRegistrationRequestDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.adwiiseagent.domain.EventCompetitionRegistration}.
 */
public interface EventCompetitionRegistrationService {
    EventCompetitionRegistrationDTO save(EventCompetitionRegistrationDTO eventCompetitionRegistrationDTO);

    EventCompetitionRegistrationDTO update(EventCompetitionRegistrationDTO eventCompetitionRegistrationDTO);

    Optional<EventCompetitionRegistrationDTO> partialUpdate(EventCompetitionRegistrationDTO eventCompetitionRegistrationDTO);

    Page<EventCompetitionRegistrationDTO> findAll(Pageable pageable);

    Optional<EventCompetitionRegistrationDTO> findOne(Long id);

    void delete(Long id);

    EventCompetitionRegistrationDTO saveTeamRegistration(TeamRegistrationRequestDTO request);

    List<EventCompetitionRegistrationDTO> saveBulkRegistrations(List<EventCompetitionRegistrationDTO> registrations);
}
