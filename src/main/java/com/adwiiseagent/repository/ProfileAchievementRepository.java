package com.adwiiseagent.repository;

import com.adwiiseagent.domain.ProfileAchievement;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileAchievementRepository
    extends JpaRepository<ProfileAchievement, Long>, JpaSpecificationExecutor<ProfileAchievement> {}
