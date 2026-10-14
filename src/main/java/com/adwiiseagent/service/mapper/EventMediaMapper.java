package com.adwiiseagent.service.mapper;

import com.adwiiseagent.domain.EventDetails;
import com.adwiiseagent.domain.EventMedia;
import com.adwiiseagent.domain.GalleryAlbum;
import com.adwiiseagent.domain.UserProfile;
import com.adwiiseagent.service.dto.EventDetailsDTO;
import com.adwiiseagent.service.dto.EventMediaDTO;
import com.adwiiseagent.service.dto.GalleryAlbumDTO;
import com.adwiiseagent.service.dto.UserProfileDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EventMedia} and its DTO {@link EventMediaDTO}.
 */
@Mapper(componentModel = "spring")
public interface EventMediaMapper extends EntityMapper<EventMediaDTO, EventMedia> {
    @Mapping(target = "albumId", source = "album.id")
    /*@Mapping(target = "event", source = "event", qualifiedByName = "eventDetailsId")
    @Mapping(target = "uploadedBy", source = "uploadedBy", qualifiedByName = "userProfileId")*/
    EventMediaDTO toDto(EventMedia s);

    @Mapping(target = "album", ignore = true)
    EventMedia toEntity(EventMediaDTO dto);

    default EventMedia fromId(Long id) {
        if (id == null) {
            return null;
        }
        EventMedia eventMedia = new EventMedia();
        eventMedia.setId(id);
        return eventMedia;
    }

    @Named("eventDetailsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EventDetailsDTO toDtoEventDetailsId(EventDetails eventDetails);

    @Named("userProfileId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserProfileDTO toDtoUserProfileId(UserProfile userProfile);
}
