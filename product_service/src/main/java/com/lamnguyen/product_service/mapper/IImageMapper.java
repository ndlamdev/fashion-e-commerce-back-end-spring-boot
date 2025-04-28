/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 3:30 PM - 23/04/2025
 * User: kimin
 **/

package com.lamnguyen.product_service.mapper;

import com.google.protobuf.StringValue;
import com.google.protobuf.Timestamp;
import com.lamnguyen.product_service.domain.response.ImageResponse;
import com.lamnguyen.product_service.protos.MediaInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Mapper(componentModel = "spring")
public interface IImageMapper {
	@Named("toImageId")
	default String toImageIdHelper(ImageResponse image) {
		if (image == null) return null;
		return image.getId();
	}

	@Named("toImageDto")
	default ImageResponse toImageResponse(String id) {
		if (id == null) return null;
		return ImageResponse.builder().id(id).build();
	}

	@Mapping(source = "createAt", target = "createAt", qualifiedByName = "toLocalDateTime")
	@Mapping(source = "updateAt", target = "updateAt", qualifiedByName = "toLocalDateTime")
	@Mapping(source = "createBy", target = "createBy", qualifiedByName = "toString")
	@Mapping(source = "updateBy", target = "updateBy", qualifiedByName = "toString")
	ImageResponse toImageResponse(MediaInfo info);

	@Named("toString")
	default String toString(StringValue stringValue) {
		if (stringValue == null) return null;
		return stringValue.getValue();
	}

	@Named("toLocalDateTime")
	default LocalDateTime toLocalDatetime(Timestamp timestamp) {
		if (timestamp == null) return null;
		return LocalDateTime.ofEpochSecond(timestamp.getSeconds(), timestamp.getNanos(), ZoneOffset.UTC);
	}
}
