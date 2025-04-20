/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 1:59 PM - 26/02/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.authentication_service.mapper;

import com.lamnguyen.authentication_service.domain.dto.GooglePayloadDto;
import com.lamnguyen.authentication_service.domain.dto.ProfileUserDto;
import com.lamnguyen.authentication_service.domain.request.RegisterAccountRequest;
import com.lamnguyen.authentication_service.event.SaveProfileUserEvent;
import com.lamnguyen.authentication_service.protos.ProfileUserResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IProfileUserMapper {
	SaveProfileUserEvent toUserDetail(RegisterAccountRequest request);

	SaveProfileUserEvent toUserDetail(GooglePayloadDto request);

	ProfileUserDto toProfileUserDto(ProfileUserResponse request);
}