package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.SchoolHighlight;
import com.adwiiseagent.service.dto.SchoolHighlightDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SchoolHighlightMapper extends EntityMapper<SchoolHighlightDTO, SchoolHighlight> {}
