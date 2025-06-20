/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:38 AM-24/04/2025
 * User: kimin
 **/

package com.lamnguyen.product_service.mapper;

import com.lamnguyen.product_service.domain.dto.OptionDto;
import com.lamnguyen.product_service.event.DataVariantEvent;
import com.lamnguyen.product_service.model.Option;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", uses = {IGrpcMapper.class})
public interface IOptionMapper {
	DataVariantEvent.Option toCreateVariantOption(Option option);

	List<DataVariantEvent.Option> toDataVariantOptions(Set<Option> options);

	List<DataVariantEvent.Option> toDataVariantOptions(List<OptionDto> options);

	com.lamnguyen.product_service.protos.OptionDto toOptionDto(OptionDto optionDto, IGrpcMapper mapper);

	@AfterMapping
	default void afterMapping(OptionDto optionDto,
	                          @MappingTarget com.lamnguyen.product_service.protos.OptionDto.Builder builder,
	                          IGrpcMapper mapper) {
		builder.addAllValues(optionDto.getValues().stream().map(mapper::toStringValue).toList());
	}
}
