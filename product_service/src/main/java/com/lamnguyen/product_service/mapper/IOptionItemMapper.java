/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 3:29 PM - 23/04/2025
 * User: kimin
 **/

package com.lamnguyen.product_service.mapper;

import com.lamnguyen.product_service.domain.response.OptionItemResponse;
import com.lamnguyen.product_service.domain.dto.OptionItemDto;
import com.lamnguyen.product_service.model.OptionItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {IImageMapper.class})
public interface IOptionItemMapper {
	@Mapping(source = "images", target = "images", ignore = true)
	OptionItemResponse toOptionItemResponse(OptionItem optionItem);

	@Mapping(source = "images", target = "images", qualifiedByName = "toImageId")
	OptionItem toOptionItem(OptionItemResponse optionItem);

	@Mapping(source = "images", target = "images", ignore = true)
	OptionItemResponse toOptionItemResponse(OptionItemDto optionItem);
}
