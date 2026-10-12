package com.eventsitemanager.repository;

import com.eventsitemanager.domain.RetailLocationProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RetailLocationProfileRepository
    extends JpaRepository<RetailLocationProfile, Long>, JpaSpecificationExecutor<RetailLocationProfile> {}
