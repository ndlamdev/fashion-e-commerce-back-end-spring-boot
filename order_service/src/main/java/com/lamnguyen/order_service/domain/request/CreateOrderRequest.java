/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 2:25 PM-18/05/2025
 * User: kimin
 **/

package com.lamnguyen.order_service.domain.request;

import com.lamnguyen.order_service.utils.enums.PaymentMethod;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateOrderRequest {
	@NotBlank
	String name;
	@NotBlank
	String email;
	@NotBlank
	String phone;
	@NotBlank
	String address;
	@NotBlank
	String ward;
	@NotBlank
	String district;
	@NotBlank
	String province;
	String note;
	PaymentMethod method;
	@NotEmpty
	List<CreateOrderItemRequest> items;
}
