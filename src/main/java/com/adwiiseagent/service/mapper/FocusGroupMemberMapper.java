package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.FocusGroup;
import com.adwiiseagent.domain.FocusGroupMember;
import com.adwiiseagent.service.dto.FocusGroupMemberDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FocusGroupMember} and its DTO
 * {@link FocusGroupMemberDTO}.
 */
@Mapper(componentModel = "spring")
public interface FocusGroupMemberMapper extends EntityMapper<FocusGroupMemberDTO, FocusGroupMember> {
    @Mapping(target = "focusGroupId", source = "focusGroup.id")
    FocusGroupMemberDTO toDto(FocusGroupMember s);

    @Mapping(target = "focusGroup", source = "focusGroupId", qualifiedByName = "focusGroupId")
    FocusGroupMember toEntity(FocusGroupMemberDTO dto);

    @Named("focusGroupId")
    default FocusGroup focusGroupFromId(Long id) {
        if (id == null) {
            return null;
        }
        FocusGroup focusGroup = new FocusGroup();
        focusGroup.setId(id);
        return focusGroup;
    }
}
