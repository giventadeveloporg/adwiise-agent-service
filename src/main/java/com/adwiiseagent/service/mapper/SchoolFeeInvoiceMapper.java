package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.SchoolFeeInvoice;
import com.adwiiseagent.service.dto.SchoolFeeInvoiceDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SchoolFeeInvoiceMapper extends EntityMapper<SchoolFeeInvoiceDTO, SchoolFeeInvoice> {}
