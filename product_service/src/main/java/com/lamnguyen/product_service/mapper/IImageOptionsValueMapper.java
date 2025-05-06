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
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {IOptionItemMapper.class, IGrpcMapper.class})
public interface IImageOptionsValueMapper {
	ImageOptionsValueResponse toImageOptionsValueResponse(ImageOptionsValue imageOptionsValue);

	ImageOptionsValue toImageOptionsValue(ImageOptionsValueResponse imageOptionsValue);

	ImageOptionsValueDto toImageOptionsValueDto(ImageOptionsValue imageOptionsValue);

	ImageOptionsValue toImageOptionsValue(ImageOptionsValueDto imageOptionsValue);

	ImageOptionsValueResponse toImageOptionsValueResponse(ImageOptionsValueDto imageOptionsValue);

	@Mappings({
			@Mapping(target = "mergeFrom", ignore = true),
			@Mapping(target = "clearField", ignore = true),
			@Mapping(target = "clearOneof", ignore = true),
			@Mapping(target = "unknownFields", ignore = true),
			@Mapping(target = "mergeUnknownFields", ignore = true),
			@Mapping(target = "mergeTitle", ignore = true),
			@Mapping(target = "optionValue", ignore = true),
			@Mapping(target = "removeOptions", ignore = true),
			@Mapping(target = "allFields", ignore = true),
			@Mapping(target = "optionsList", ignore = true),
			@Mapping(target = "optionsOrBuilderList", ignore = true),
			@Mapping(target = "optionsBuilderList", ignore = true),
	})
	com.lamnguyen.product_service.protos.ImageOptionsValueDto toImageOptionsValueDto(
			ImageOptionsValueResponse imageOptionsValueDto,
			IImageMapper imageMapper,
			IOptionItemMapper optionItemMapper
	);

	@AfterMapping
	default void afterMapping(
			ImageOptionsValueResponse imageOptionsValueDto,
			@MappingTarget com.lamnguyen.product_service.protos.ImageOptionsValueDto.Builder builder,
			IImageMapper imageMapper,
			IOptionItemMapper optionItemMapper
	) {
		if (imageOptionsValueDto.getOptions() == null) return;
		builder.addAllOptions(imageOptionsValueDto.getOptions().stream().map(data -> optionItemMapper.toOptionItemDto(data, imageMapper)).toList());
	}
}
