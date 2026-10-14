package com.adwiiseagent.service;

import com.adwiiseagent.service.dto.ProfileProjectDTO;
import java.util.Optional;

public interface ProfileProjectService {
    ProfileProjectDTO save(ProfileProjectDTO profileProjectDTO);
    ProfileProjectDTO update(ProfileProjectDTO profileProjectDTO);
    Optional<ProfileProjectDTO> partialUpdate(ProfileProjectDTO profileProjectDTO);
    Optional<ProfileProjectDTO> findOne(Long id);
    void delete(Long id);
}
