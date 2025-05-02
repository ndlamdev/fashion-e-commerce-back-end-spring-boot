/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354818
 * Create at: 3:34 PM - 26/02/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.cart_service.config.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ExceptionEnum {
	UNAUTHORIZED(80001, "Unauthorized"),
	COLLECTION_NOT_FOUND(80002, "Collection not found"),
	PRODUCT_NOT_FOUND(80003, "Product not found"),
	ID_PRODUCT_NOT_FOUND(80004, "ID product not found"),
	DUPLICATE(80006, "Duplicate value"),
	UNVALIDATED(88888, "Unvalidated"),
	;

	int code;
	String message;

	ExceptionEnum(int code, String message) {
		this.code = code;
		this.message = message;
	}
}
