package com.adwiiseagent.service;

import com.adwiiseagent.service.dto.SchoolFeeInvoiceDTO;
import java.util.Optional;

public interface SchoolFeeInvoiceService {
    SchoolFeeInvoiceDTO save(SchoolFeeInvoiceDTO schoolFeeInvoiceDTO);

    SchoolFeeInvoiceDTO update(SchoolFeeInvoiceDTO schoolFeeInvoiceDTO);

    Optional<SchoolFeeInvoiceDTO> partialUpdate(SchoolFeeInvoiceDTO schoolFeeInvoiceDTO);

    Optional<SchoolFeeInvoiceDTO> findOne(Long id);

    void delete(Long id);
}
