package com.adwiiseagent.repository;

import com.adwiiseagent.domain.TeamGroup;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TeamGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TeamGroupRepository extends JpaRepository<TeamGroup, Long>, JpaSpecificationExecutor<TeamGroup> {}
