/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 4:12 AM-07/05/2025
 * User: Administrator
 **/

package com.lamnguyen.cart_service.mapper;

import com.lamnguyen.cart_service.domain.dto.OptionDto;
import com.lamnguyen.cart_service.utils.enums.OptionType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {IGrpcMapper.class})
public interface IOptionMapper {
	@Mapping(source = "type", target = "type", qualifiedByName = "toType")
	@Mapping(source = "valuesList", target = "values")
	OptionDto toDto(com.lamnguyen.cart_service.protos.OptionDto option);

	@Named("toType")
	default OptionType toType(com.lamnguyen.cart_service.protos.OptionType type) {
		return OptionType.valueOf(type.name());
	}
}
