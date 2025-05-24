package com.lamnguyen.profile_service.service.business;

import com.lamnguyen.profile_service.domain.ApiPaging;
import com.lamnguyen.profile_service.domain.dto.ProfileDto;
import com.lamnguyen.profile_service.domain.request.SaveProfileRequest;

public interface IProfileService {
    ProfileDto saveProfile(SaveProfileRequest saveProfileRequest);

    ApiPaging<ProfileDto> getProfiles(Integer page, Integer size);

    ProfileDto getProfileById(Long id);

    ProfileDto getProfileByUserId(Long id);

    ProfileDto getProfile();
}
