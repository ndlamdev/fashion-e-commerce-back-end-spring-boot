/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 2:32 AM-07/05/2025
 * User: Administrator
 **/

package com.lamnguyen.cart_service.mapper;

import com.lamnguyen.cart_service.domain.dto.ProductDto;
import com.lamnguyen.cart_service.protos.ProductInCartDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {IGrpcMapper.class, IOptionMapper.class})
public interface IProductMapper {
	@Mapping(source = "optionsList", target = "options")
	@Mapping(source = "imagesList", target = "images")
	ProductDto toProductDto(ProductInCartDto product);
}
