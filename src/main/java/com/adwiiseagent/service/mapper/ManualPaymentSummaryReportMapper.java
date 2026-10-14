package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.ManualPaymentSummaryReport;
import com.adwiiseagent.service.dto.ManualPaymentSummaryReportDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link ManualPaymentSummaryReport} and its DTO {@link ManualPaymentSummaryReportDTO}.
 */
@Mapper(componentModel = "spring")
public interface ManualPaymentSummaryReportMapper extends EntityMapper<ManualPaymentSummaryReportDTO, ManualPaymentSummaryReport> {}
