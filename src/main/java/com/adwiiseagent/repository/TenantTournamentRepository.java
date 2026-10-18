package com.adwiiseagent.repository;

import com.adwiiseagent.domain.TenantTournament;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TenantTournamentRepository extends JpaRepository<TenantTournament, Long>, JpaSpecificationExecutor<TenantTournament> {
    Optional<TenantTournament> findByTenantIdAndSlug(String tenantId, String slug);

    @Modifying(flushAutomatically = true, clearAutomatically = false)
    @Query(
        "update TenantTournament t set t.showInHeader = false where t.tenantId = :tenantId and t.showInHeader = true and t.id <> :keepId"
    )
    int clearHeaderExcept(@Param("tenantId") String tenantId, @Param("keepId") Long keepId);

    @Modifying(flushAutomatically = true, clearAutomatically = false)
    @Query("update TenantTournament t set t.showInHeader = false where t.tenantId = :tenantId and t.showInHeader = true")
    int clearHeaderForTenant(@Param("tenantId") String tenantId);
}
