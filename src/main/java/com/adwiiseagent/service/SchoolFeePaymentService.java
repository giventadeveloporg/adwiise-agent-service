package com.adwiiseagent.service;

import com.adwiiseagent.service.dto.SchoolFeePaymentDTO;
import java.util.Optional;

public interface SchoolFeePaymentService {
    SchoolFeePaymentDTO save(SchoolFeePaymentDTO schoolFeePaymentDTO);

    SchoolFeePaymentDTO update(SchoolFeePaymentDTO schoolFeePaymentDTO);

    Optional<SchoolFeePaymentDTO> partialUpdate(SchoolFeePaymentDTO schoolFeePaymentDTO);

    Optional<SchoolFeePaymentDTO> findOne(Long id);

    void delete(Long id);
}
