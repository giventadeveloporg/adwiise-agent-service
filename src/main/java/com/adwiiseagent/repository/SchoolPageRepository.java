package com.adwiiseagent.repository;

import com.adwiiseagent.domain.SchoolPage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolPageRepository extends JpaRepository<SchoolPage, Long>, JpaSpecificationExecutor<SchoolPage> {}
