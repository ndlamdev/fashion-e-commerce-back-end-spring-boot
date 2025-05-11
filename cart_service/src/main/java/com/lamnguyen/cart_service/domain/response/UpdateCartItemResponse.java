/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 6:16 AM-08/05/2025
 * User: kimin
 **/

package com.lamnguyen.cart_service.domain.response;

import lombok.Builder;

@Builder
public record UpdateCartItemResponse(
		long cartItemId,
		int quantity
) {

}
