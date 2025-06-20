/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354818
 * Create at: 3:34 PM - 26/02/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.payment_service.config.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ExceptionEnum {
	UNAUTHORIZED(80001, "Unauthorized"),
	NOT_FOUND(80002, "Payment not found"),
	;

	int code;
	String message;

	ExceptionEnum(int code, String message) {
		this.code = code;
		this.message = message;
	}
}
