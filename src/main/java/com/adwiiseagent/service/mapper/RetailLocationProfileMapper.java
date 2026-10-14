package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.RetailLocationProfile;
import com.adwiiseagent.service.dto.RetailLocationProfileDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RetailLocationProfileMapper extends EntityMapper<RetailLocationProfileDTO, RetailLocationProfile> {}
