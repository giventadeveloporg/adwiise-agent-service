package com.adwiiseagent.repository;

import com.adwiiseagent.domain.LeagueClub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface LeagueClubRepository extends JpaRepository<LeagueClub, Long>, JpaSpecificationExecutor<LeagueClub> {

    java.util.List<LeagueClub> findByLeagueIdOrderByDisplayOrderAscNameAsc(Long leagueId);
    java.util.Optional<LeagueClub> findByIdAndLeagueId(Long id, Long leagueId);

}
