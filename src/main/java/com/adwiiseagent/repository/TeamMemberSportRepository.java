package com.adwiiseagent.repository;

import com.adwiiseagent.domain.TeamMemberSport;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamMemberSportRepository extends JpaRepository<TeamMemberSport, Long> {
    boolean existsBySportsCategoryId(Long sportsCategoryId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("delete from TeamMemberSport s where s.teamMemberId = :teamMemberId")
    void deleteByTeamMemberId(@Param("teamMemberId") Long teamMemberId);

    @Query(
        """
        select s from TeamMemberSport s
        join fetch s.sportsCategory c
        where s.teamMemberId in :ids
        order by c.sortOrder asc, c.label asc
        """
    )
    List<TeamMemberSport> findWithCategoryByTeamMemberIdIn(@Param("ids") Collection<Long> ids);

    @Query(
        """
        select s from TeamMemberSport s
        join fetch s.sportsCategory c
        where s.teamMemberId = :teamMemberId
        order by c.sortOrder asc, c.label asc
        """
    )
    List<TeamMemberSport> findWithCategoryByTeamMemberId(@Param("teamMemberId") Long teamMemberId);
}
