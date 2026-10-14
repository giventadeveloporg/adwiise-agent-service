package com.adwiiseagent.repository;

import com.adwiiseagent.domain.QrCodeUsage;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the QrCodeUsage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QrCodeUsageRepository extends JpaRepository<QrCodeUsage, Long>, JpaSpecificationExecutor<QrCodeUsage> {}
