package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.ManualPaymentRequest;
import com.adwiiseagent.service.dto.ManualPaymentRequestDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ManualPaymentRequest} and its DTO {@link ManualPaymentRequestDTO}.
 */
@Mapper(componentModel = "spring")
public interface ManualPaymentRequestMapper extends EntityMapper<ManualPaymentRequestDTO, ManualPaymentRequest> {
    // MapStruct will generate implementation; keep partialUpdate consistent with other mappers.
    void partialUpdate(@MappingTarget ManualPaymentRequest entity, ManualPaymentRequestDTO dto);
}
