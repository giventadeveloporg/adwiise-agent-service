package com.adwiiseagent.repository;

import com.adwiiseagent.domain.EventCompetitionDay;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EventCompetitionDay entity.
 */
@Repository
public interface EventCompetitionDayRepository
    extends JpaRepository<EventCompetitionDay, Long>, JpaSpecificationExecutor<EventCompetitionDay> {}
