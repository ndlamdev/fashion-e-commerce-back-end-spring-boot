/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 7:22 AM-03/05/2025
 * User: kimin
 **/

package com.lamnguyen.cart_service.service.business;

import com.lamnguyen.cart_service.domain.dto.CartDto;
import com.lamnguyen.cart_service.domain.response.CartResponse;
import com.lamnguyen.cart_service.domain.response.UpdateCartItemResponse;

public interface ICartService {
	CartResponse getCart();

	CartDto createCart(long userId);

	void addVariantToCart(String variantId);

	UpdateCartItemResponse updateCartItem(long cartItemId, int quantity);

	void removeCartItem(long cartItemId);

	void removeCartItem(long userId, String variantId);
}
