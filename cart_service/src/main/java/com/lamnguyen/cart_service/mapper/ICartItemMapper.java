/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 3:04 AM-07/05/2025
 * User: Administrator
 **/

package com.lamnguyen.cart_service.mapper;

import com.lamnguyen.cart_service.domain.dto.CartItemDto;
import com.lamnguyen.cart_service.domain.response.CartItemResponse;
import com.lamnguyen.cart_service.model.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ICartItemMapper {
	CartItemDto toDto(CartItem cartItem);

	@Mapping(source = "productId", target = "product.id")
	@Mapping(source = "variantId", target = "variant.id")
	CartItemResponse toCartItemResponse(CartItemDto cartItem);
}
