package com.adwiiseagent.repository;

import com.adwiiseagent.domain.EventCompetitionRegistration;
import java.util.Collection;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EventCompetitionRegistration entity.
 */
@Repository
public interface EventCompetitionRegistrationRepository
    extends JpaRepository<EventCompetitionRegistration, Long>, JpaSpecificationExecutor<EventCompetitionRegistration> {
    boolean existsByCompetitionIdAndParticipantProfileIdAndIdNot(Long competitionId, Long participantProfileId, Long id);
    long countByCompetitionIdAndRegistrationStatusNotIn(
        Long competitionId,
        java.util.Collection<com.adwiiseagent.domain.enumeration.CompetitionRegistrationStatus> statuses
    );
    long countByGroupLeaderRegistrationId(Long groupLeaderRegistrationId);
}
