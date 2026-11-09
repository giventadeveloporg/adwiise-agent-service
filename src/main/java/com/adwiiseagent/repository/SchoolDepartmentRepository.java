package com.adwiiseagent.repository;

import com.adwiiseagent.domain.SchoolDepartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolDepartmentRepository extends JpaRepository<SchoolDepartment, Long>, JpaSpecificationExecutor<SchoolDepartment> {}
