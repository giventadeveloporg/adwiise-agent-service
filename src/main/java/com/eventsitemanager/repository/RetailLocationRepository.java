package com.eventsitemanager.repository;

import com.eventsitemanager.domain.RetailLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RetailLocationRepository extends JpaRepository<RetailLocation, Long>, JpaSpecificationExecutor<RetailLocation> {}
