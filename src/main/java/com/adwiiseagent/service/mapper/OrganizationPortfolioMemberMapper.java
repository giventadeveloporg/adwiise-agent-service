package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.OrganizationPortfolioMember;
import com.adwiiseagent.service.dto.OrganizationPortfolioMemberDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrganizationPortfolioMemberMapper extends EntityMapper<OrganizationPortfolioMemberDTO, OrganizationPortfolioMember> {}
