package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.OrganizationPortfolio;
import com.adwiiseagent.service.dto.OrganizationPortfolioDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrganizationPortfolioMapper extends EntityMapper<OrganizationPortfolioDTO, OrganizationPortfolio> {}
