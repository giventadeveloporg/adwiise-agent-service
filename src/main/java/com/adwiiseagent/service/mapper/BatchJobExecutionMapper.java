package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.BatchJobExecution;
import com.adwiiseagent.service.dto.BatchJobExecutionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BatchJobExecution} and its DTO {@link BatchJobExecutionDTO}.
 */
@Mapper(componentModel = "spring")
public interface BatchJobExecutionMapper extends EntityMapper<BatchJobExecutionDTO, BatchJobExecution> {}
