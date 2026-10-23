package com.adwiiseagent.repository;

import com.adwiiseagent.domain.TeamMemberAchievement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamMemberAchievementRepository
    extends JpaRepository<TeamMemberAchievement, Long>, JpaSpecificationExecutor<TeamMemberAchievement> {}
