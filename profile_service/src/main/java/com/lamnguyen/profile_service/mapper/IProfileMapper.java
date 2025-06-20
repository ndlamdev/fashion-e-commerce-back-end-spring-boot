package com.lamnguyen.profile_service.mapper;

import com.lamnguyen.profile_service.domain.dto.ProfileDto;
import com.lamnguyen.profile_service.domain.request.SaveProfileRequest;
import com.lamnguyen.profile_service.domain.response.ProfileAdminResponse;
import com.lamnguyen.profile_service.domain.response.SaveProfileResponse;
import com.lamnguyen.profile_service.message.SaveProfileUserMessage;
import com.lamnguyen.profile_service.model.entity.Profile;
import com.lamnguyen.profile_service.protos.ProfileUserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {IAddressMapper.class, IGrpcMapper.class})
public interface IProfileMapper {
	Profile toProfile(SaveProfileUserMessage message);

	Profile toProfile(SaveProfileRequest request);

	List<ProfileAdminResponse> toProfileAdminResponses(List<Profile> profiles);

	ProfileDto toProfileDto(Profile profile);

	@Mapping(source = "birthday", target = "birthday", qualifiedByName = "convertLocalDateTimeToStringValue")
	ProfileUserResponse toUserResponse(ProfileDto profile);
}
