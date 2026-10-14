package com.adwiiseagent.repository;

import com.adwiiseagent.domain.ProfileWriting;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileWritingRepository extends JpaRepository<ProfileWriting, Long>, JpaSpecificationExecutor<ProfileWriting> {}
