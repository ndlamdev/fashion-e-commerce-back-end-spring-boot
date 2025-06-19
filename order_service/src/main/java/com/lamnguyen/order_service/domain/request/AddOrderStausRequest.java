/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 12:24 PM-19/06/2025
 * User: kimin
 **/

package com.lamnguyen.order_service.domain.request;

import com.lamnguyen.order_service.utils.enums.OrderStatus;
import jakarta.validation.constraints.NotNull;

public record AddOrderStausRequest(
		@NotNull
		OrderStatus status,
		@NotNull
		String note
) {

}
