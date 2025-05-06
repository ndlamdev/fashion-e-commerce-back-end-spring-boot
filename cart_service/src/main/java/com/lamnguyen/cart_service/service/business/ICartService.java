/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 7:22 AM-03/05/2025
 * User: kimin
 **/

package com.lamnguyen.cart_service.service.business;

import com.lamnguyen.cart_service.domain.dto.CartDto;

public interface ICartService {
	CartDto getCartByUserId(long userId);

	CartDto createCart(long userId);

	void addVariantToCart(long userId, String variantId);
}
