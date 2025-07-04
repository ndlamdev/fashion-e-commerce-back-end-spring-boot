/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 9:15 AM-05/05/2025
 * User: kimin
 **/

package com.lamnguyen.cart_service.service.business;

public interface ICartItemService {
	void addCartItem(long cartId, String variantId, int quantity);

	int updateQuantityCartItem(long cartId, long id, int quantity);

	void removeCartItem(long cartId, long id);

	void removeCartItem(long cartId, String variantId);
}
