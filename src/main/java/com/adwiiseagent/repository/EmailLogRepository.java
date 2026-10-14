package com.adwiiseagent.repository;

import com.adwiiseagent.domain.EmailLog;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EmailLog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmailLogRepository extends JpaRepository<EmailLog, Long>, JpaSpecificationExecutor<EmailLog> {}
