package com.lamnguyen.profile_service.service.business;

import com.lamnguyen.profile_service.domain.dto.ProfileDto;
import com.lamnguyen.profile_service.domain.request.SaveProfileRequest;
import com.lamnguyen.profile_service.domain.response.ProfileAdminResponse;

import java.util.List;

public interface IProfileService {
    ProfileDto saveProfile(SaveProfileRequest saveProfileRequest);

    List<ProfileAdminResponse> getProfiles();

    ProfileDto getProfileByUserId(Long id);

    ProfileDto getProfile();
}
