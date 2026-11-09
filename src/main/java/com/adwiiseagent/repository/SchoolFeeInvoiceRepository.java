package com.adwiiseagent.repository;

import com.adwiiseagent.domain.SchoolFeeInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolFeeInvoiceRepository extends JpaRepository<SchoolFeeInvoice, Long>, JpaSpecificationExecutor<SchoolFeeInvoice> {}
