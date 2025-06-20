/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 3:25 PM-23/05/2025
 * User: kimin
 **/
package com.lamnguyen.order_service.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OrderIdRequest(
		@JsonProperty("order_id")
		long orderId
) {
}
