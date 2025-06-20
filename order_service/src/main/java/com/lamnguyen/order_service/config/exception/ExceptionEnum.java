/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354818
 * Create at: 3:34 PM - 26/02/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.order_service.config.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ExceptionEnum {
	UNAUTHORIZED(60001, "Unauthorized"),
	NOT_FAIL_VARIANT(60002, "Not Fail Variant"),
	PAY_FAIL(60003, "Pay Fail"),
	NOT_FOUND(60004, "Order not Found"),
	CREATE_ORDER_FAIL(60005, "Create Order Fail"),
	;

	int code;
	String message;

	ExceptionEnum(int code, String message) {
		this.code = code;
		this.message = message;
	}
}
