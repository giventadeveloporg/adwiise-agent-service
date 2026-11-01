package com.adwiiseagent.repository;

import com.adwiiseagent.domain.TeamGroupMembership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamGroupMembershipRepository extends JpaRepository<TeamGroupMembership, Long>, JpaSpecificationExecutor<TeamGroupMembership> {

    java.util.List<TeamGroupMembership> findByTeamMemberId(Long teamMemberId);
    java.util.List<TeamGroupMembership> findByTeamGroupId(Long teamGroupId);

}
