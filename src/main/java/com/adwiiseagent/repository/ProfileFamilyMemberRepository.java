package com.adwiiseagent.repository;

import com.adwiiseagent.domain.ProfileFamilyMember;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileFamilyMemberRepository
    extends JpaRepository<ProfileFamilyMember, Long>, JpaSpecificationExecutor<ProfileFamilyMember> {}
