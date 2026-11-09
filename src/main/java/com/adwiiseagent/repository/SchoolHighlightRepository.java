package com.adwiiseagent.repository;

import com.adwiiseagent.domain.SchoolHighlight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolHighlightRepository extends JpaRepository<SchoolHighlight, Long>, JpaSpecificationExecutor<SchoolHighlight> {}
