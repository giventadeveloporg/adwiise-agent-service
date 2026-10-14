package com.adwiiseagent.service;

import com.adwiiseagent.service.dto.ProfileServiceDTO;
import java.util.Optional;

public interface ProfileServiceService {
    ProfileServiceDTO save(ProfileServiceDTO profileServiceDTO);
    ProfileServiceDTO update(ProfileServiceDTO profileServiceDTO);
    Optional<ProfileServiceDTO> partialUpdate(ProfileServiceDTO profileServiceDTO);
    Optional<ProfileServiceDTO> findOne(Long id);
    void delete(Long id);
}
