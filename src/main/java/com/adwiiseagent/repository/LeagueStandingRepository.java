package com.adwiiseagent.repository;

import com.adwiiseagent.domain.LeagueStanding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface LeagueStandingRepository extends JpaRepository<LeagueStanding, Long>, JpaSpecificationExecutor<LeagueStanding> {

    java.util.List<LeagueStanding> findByLeagueId(Long leagueId);
    void deleteByLeagueId(Long leagueId);
    java.util.Optional<LeagueStanding> findByLeagueIdAndLeagueClubId(Long leagueId, Long leagueClubId);

}
