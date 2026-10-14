package com.adwiiseagent.repository;

import com.adwiiseagent.domain.ProfileService;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileServiceRepository extends JpaRepository<ProfileService, Long>, JpaSpecificationExecutor<ProfileService> {}
