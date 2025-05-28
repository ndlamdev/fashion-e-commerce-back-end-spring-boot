/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 7:13 AM-19/05/2025
 * User: kimin
 **/

package com.lamnguyen.order_service.domain.dto;

import com.lamnguyen.order_service.utils.enums.OrderStatus;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusDto {
	long id;
	OrderStatus status;
	String note;
	boolean lock;
	LocalDateTime updateAt;
}
