/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 12:53 PM-07/05/2025
 * User: kimin
 **/
package com.lamnguyen.cart_service.domain.request;

import com.lamnguyen.cart_service.utils.annotation.NotEqualsIntegerNumber;
import jakarta.validation.constraints.NotNull;

public record UpdateCartItemRequest(
		@NotNull
		@NotEqualsIntegerNumber(message = "Quantity cannot be zero.")
		Integer quantity
) {
}
