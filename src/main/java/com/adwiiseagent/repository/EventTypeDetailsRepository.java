package com.adwiiseagent.repository;

import com.adwiiseagent.domain.EventTypeDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EventTypeDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EventTypeDetailsRepository extends JpaRepository<EventTypeDetails, Long>, JpaSpecificationExecutor<EventTypeDetails> {}
