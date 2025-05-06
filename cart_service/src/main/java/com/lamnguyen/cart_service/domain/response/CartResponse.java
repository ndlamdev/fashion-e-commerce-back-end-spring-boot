/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 11:08 AM-05/05/2025
 * User: kimin
 **/

package com.lamnguyen.cart_service.domain.response;

import java.util.List;

public class CartResponse {
	long id;
	Long userId;
	List<CartItemResponse> cartItems;
	boolean lock;
}
