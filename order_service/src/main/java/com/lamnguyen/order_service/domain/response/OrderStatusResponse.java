/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 7:13 AM-19/05/2025
 * User: kimin
 **/

package com.lamnguyen.order_service.domain.response;

import com.lamnguyen.order_service.utils.enums.OrderStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class OrderStatusResponse {
	long id;
	OrderStatus status;
	String note;
	boolean lock;
	LocalDateTime updateAt;
}
