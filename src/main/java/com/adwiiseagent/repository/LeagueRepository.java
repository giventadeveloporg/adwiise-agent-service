package com.adwiiseagent.repository;

import com.adwiiseagent.domain.League;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface LeagueRepository extends JpaRepository<League, Long>, JpaSpecificationExecutor<League> {

    java.util.Optional<League> findOneByTenantIdAndSlug(String tenantId, String slug);

}
