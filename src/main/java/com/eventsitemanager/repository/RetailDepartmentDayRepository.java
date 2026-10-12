package com.eventsitemanager.repository;

import com.eventsitemanager.domain.RetailDepartmentDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RetailDepartmentDayRepository
    extends JpaRepository<RetailDepartmentDay, Long>, JpaSpecificationExecutor<RetailDepartmentDay> {}
