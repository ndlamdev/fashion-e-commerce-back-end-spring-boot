/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 5:36 AM-27/05/2025
 * User: kimin
 **/

package com.lamnguyen.order_service.domain.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.lamnguyen.order_service.utils.enums.OrderStatus;

import java.time.LocalDateTime;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record SubOrder(
		long id,
		 long userId,
		OrderStatus status,
		String fullName,
		String email,
		double amount,
		LocalDateTime date) {

}
