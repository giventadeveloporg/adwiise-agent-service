package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.BulkOperationLog;
import com.adwiiseagent.domain.UserProfile;
import com.adwiiseagent.service.dto.BulkOperationLogDTO;
import com.adwiiseagent.service.dto.UserProfileDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BulkOperationLog} and its DTO {@link BulkOperationLogDTO}.
 */
@Mapper(componentModel = "spring")
public interface BulkOperationLogMapper extends EntityMapper<BulkOperationLogDTO, BulkOperationLog> {
    @Mapping(target = "performedBy", source = "performedBy", qualifiedByName = "userProfileId")
    BulkOperationLogDTO toDto(BulkOperationLog s);

    @Named("userProfileId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserProfileDTO toDtoUserProfileId(UserProfile userProfile);
}
