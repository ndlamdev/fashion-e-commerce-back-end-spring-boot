/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 3:19 PM - 23/04/2025
 * User: kimin
 **/

package com.lamnguyen.product_service.mapper;

import com.lamnguyen.product_service.domain.dto.ImageOptionsValueDto;
import com.lamnguyen.product_service.domain.response.ImageOptionsValueResponse;
import com.lamnguyen.product_service.model.ImageOptionsValue;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {IOptionItemMapper.class})
public interface IImageOptionsValueMapper {
	ImageOptionsValueResponse toImageOptionsValueResponse(ImageOptionsValue imageOptionsValue);

	ImageOptionsValue toImageOptionsValue(ImageOptionsValueResponse imageOptionsValue);

	ImageOptionsValueDto toImageOptionsValueDto(ImageOptionsValue imageOptionsValue);

	ImageOptionsValue toImageOptionsValue(ImageOptionsValueDto imageOptionsValue);

	ImageOptionsValueResponse toImageOptionsValueResponse(ImageOptionsValueDto imageOptionsValue);
}
