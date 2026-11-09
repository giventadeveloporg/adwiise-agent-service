package com.adwiiseagent.repository;

import com.adwiiseagent.domain.SchoolFacility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolFacilityRepository extends JpaRepository<SchoolFacility, Long>, JpaSpecificationExecutor<SchoolFacility> {}
