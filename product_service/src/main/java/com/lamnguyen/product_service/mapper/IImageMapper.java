/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 3:30 PM - 23/04/2025
 * User: kimin
 **/

package com.lamnguyen.product_service.mapper;

import com.lamnguyen.product_service.domain.response.ImageResponse;
import com.lamnguyen.product_service.protos.Image;
import com.lamnguyen.product_service.protos.MediaInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {IGrpcMapper.class})
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

	ImageResponse toImageResponse(MediaInfo info);

	Image toImage(ImageResponse image);
}
