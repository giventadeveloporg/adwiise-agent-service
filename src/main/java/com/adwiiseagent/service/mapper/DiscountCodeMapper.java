package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.DiscountCode;
import com.adwiiseagent.service.dto.DiscountCodeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DiscountCode} and its DTO {@link DiscountCodeDTO}.
 */
@Mapper(componentModel = "spring")
public interface DiscountCodeMapper extends EntityMapper<DiscountCodeDTO, DiscountCode> {}
