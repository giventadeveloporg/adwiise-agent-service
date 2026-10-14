package com.adwiiseagent.repository;

import com.adwiiseagent.domain.RetailIntegration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RetailIntegrationRepository extends JpaRepository<RetailIntegration, Long>, JpaSpecificationExecutor<RetailIntegration> {}
