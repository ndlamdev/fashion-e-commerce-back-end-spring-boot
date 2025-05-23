/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 5:03 PM-02/05/2025
 * User: kimin
 **/

package com.lamnguyen.order_service.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lamnguyen.order_service.utils.enums.PaymentMethod;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@SuperBuilder
public class OrderResponse {
	long id;
	@JsonProperty("customer_id")
	Long customerId;
	String name;
	String phone;
	String email;
	@JsonProperty("address_detail")
	String addressDetail;
	String ward;
	String district;
	String province;
	String note;
	List<OrderItemResponse> items;
	List<OrderStatusResponse> statuses;
	@JsonProperty("update_at")
	LocalDateTime updateAt;
}
