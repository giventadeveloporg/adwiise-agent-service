package com.adwiiseagent.repository;

import com.adwiiseagent.domain.LeagueMatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface LeagueMatchRepository extends JpaRepository<LeagueMatch, Long>, JpaSpecificationExecutor<LeagueMatch> {

    java.util.List<LeagueMatch> findByLeagueIdAndStatus(Long leagueId, String status);
    java.util.List<LeagueMatch> findByLeagueId(Long leagueId);

}
