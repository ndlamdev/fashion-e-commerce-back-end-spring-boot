/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 8:36 PM-23/04/2025
 * User: kimin
 **/

package com.lamnguyen.media_service.mapper;

import com.lamnguyen.media_service.domain.dto.MediaDto;
import com.lamnguyen.media_service.model.Media;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IMediaMapper {
	MediaDto toDto(Media media);
}
