package com.adwiiseagent.repository;

import com.adwiiseagent.domain.TeamType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamTypeRepository extends JpaRepository<TeamType, Long>, JpaSpecificationExecutor<TeamType> {
    Optional<TeamType> findByCodeAndTenantId(String code, String tenantId);

    Optional<TeamType> findByCodeAndTenantIdIsNull(String code);

    @Query(
        """
        select c from TeamType c
        where c.code = :code and c.isActive = true
          and (c.tenantId is null or (:tenantId is not null and c.tenantId = :tenantId))
        order by case when c.tenantId is null then 1 else 0 end
        """
    )
    List<TeamType> findActiveForTenant(@Param("code") String code, @Param("tenantId") String tenantId);
}
