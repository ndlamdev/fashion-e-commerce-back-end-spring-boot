/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 5:03 PM-02/05/2025
 * User: kimin
 **/

package com.lamnguyen.order_service.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class OrderDto {
	long id;
	Long userId;
	String name;
	String phone;
	String email;
	String addressDetail;
	String ward;
	String district;
	String province;
	String note;
	List<OrderItemDto> items;
	List<OrderStatusDto> statuses;
	LocalDateTime updateAt;
}
