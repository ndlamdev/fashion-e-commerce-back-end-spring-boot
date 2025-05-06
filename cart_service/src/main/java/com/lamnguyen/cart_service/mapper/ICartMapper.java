/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 7:28 AM-03/05/2025
 * User: kimin
 **/

package com.lamnguyen.cart_service.mapper;

import com.lamnguyen.cart_service.domain.dto.CartDto;
import com.lamnguyen.cart_service.model.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ICartMapper {
	CartDto toCartDto(Cart cart);

	@Mappings({
			@Mapping(target = "createBy", ignore = true),
			@Mapping(target = "updateBy", ignore = true),
	})
	Cart toCart(CartDto cart);
}
