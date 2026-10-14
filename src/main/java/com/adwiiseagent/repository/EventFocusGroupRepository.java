package com.adwiiseagent.repository;

import com.adwiiseagent.domain.EventFocusGroup;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EventFocusGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EventFocusGroupRepository extends JpaRepository<EventFocusGroup, Long>, JpaSpecificationExecutor<EventFocusGroup> {}
