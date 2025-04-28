/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 1:59 PM - 26/02/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.authentication_service.mapper;

import com.lamnguyen.authentication_service.domain.dto.FacebookPayloadDto;
import com.lamnguyen.authentication_service.domain.dto.GooglePayloadDto;
import com.lamnguyen.authentication_service.domain.dto.ProfileUserDto;
import com.lamnguyen.authentication_service.domain.request.RegisterAccountRequest;
import com.lamnguyen.authentication_service.domain.request.RegisterAccountWithFacebookRequest;
import com.lamnguyen.authentication_service.event.SaveProfileUserEvent;
import com.lamnguyen.authentication_service.protos.ProfileUserResponse;
import com.lamnguyen.authentication_service.service.business.facebook.IFacebookGraphClient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {IGrpcMapper.class})
public interface IProfileUserMapper {
	SaveProfileUserEvent toSaveProfileUserEvent(RegisterAccountRequest request);

	SaveProfileUserEvent toSaveProfileUserEvent(RegisterAccountWithFacebookRequest request);

	SaveProfileUserEvent toSaveProfileUserEvent(GooglePayloadDto request);

	ProfileUserDto toProfileUserDto(ProfileUserResponse request);

	@Mapping(source = "avatar.data", target = "avatar", qualifiedByName = "convertPictureObjectToUrl")
	FacebookPayloadDto toFacebookPayloadDto(IFacebookGraphClient.ProfileUserFacebookResponse request);

	@Named("convertPictureObjectToUrl")
	default String convertPictureObjectToUrl(IFacebookGraphClient.ProfileUserFacebookResponse.Picture.PictureData pictureData) {
		return pictureData.url() + "?height=" + pictureData.height() + "&width=" + pictureData.width() + "&is_silhouette=" + pictureData.isSilhouette();
	}
}