package com.adwiiseagent.repository;

import com.adwiiseagent.domain.ProfileAffiliation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileAffiliationRepository
    extends JpaRepository<ProfileAffiliation, Long>, JpaSpecificationExecutor<ProfileAffiliation> {}
