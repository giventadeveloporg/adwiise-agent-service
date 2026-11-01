package com.adwiiseagent.repository;

import com.adwiiseagent.domain.RetailerSizeBand;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RetailerSizeBandRepository extends JpaRepository<RetailerSizeBand, Long> {
    Optional<RetailerSizeBand> findByRetailerTypeCodeAndCode(String retailerTypeCode, String code);

    List<RetailerSizeBand> findByIsActiveTrueOrderByRetailerTypeCodeAscSortOrderAsc();

    List<RetailerSizeBand> findAllByOrderByRetailerTypeCodeAscSortOrderAsc();
}
