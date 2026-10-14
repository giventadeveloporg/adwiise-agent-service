package com.adwiiseagent.repository;

import com.adwiiseagent.domain.RetailFuelDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RetailFuelDayRepository extends JpaRepository<RetailFuelDay, Long>, JpaSpecificationExecutor<RetailFuelDay> {}
