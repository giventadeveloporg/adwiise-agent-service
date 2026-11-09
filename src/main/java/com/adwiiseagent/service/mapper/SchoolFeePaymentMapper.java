package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.SchoolFeePayment;
import com.adwiiseagent.service.dto.SchoolFeePaymentDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SchoolFeePaymentMapper extends EntityMapper<SchoolFeePaymentDTO, SchoolFeePayment> {}
