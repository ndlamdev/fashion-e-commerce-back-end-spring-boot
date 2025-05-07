/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 12:53 PM-07/05/2025
 * User: kimin
 **/
package com.lamnguyen.cart_service.domain.request;

public record UpdateCartItemRequest(
		long id,
		int quantity
) {
}
