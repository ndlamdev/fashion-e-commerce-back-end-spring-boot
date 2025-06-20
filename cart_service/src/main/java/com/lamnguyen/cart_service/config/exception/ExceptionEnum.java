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
	UNAUTHORIZED(60001, "Unauthorized"),
	COLLECTION_NOT_FOUND(60002, "Collection not found"),
	CART_NOT_FOUND(60003, "Cart not found"),
	ID_PRODUCT_NOT_FOUND(60004, "ID product not found"),
	DUPLICATE(60006, "Duplicate value"),
	UNVALIDATED(68888, "Unvalidated"),
	CART_ITEM_NOT_FOUND(60007, "Cart item not found"),
	NOT_ENOUGH_QUANTITY(60008, "Not enough quantity"),
	;

	int code;
	String message;

	ExceptionEnum(int code, String message) {
		this.code = code;
		this.message = message;
	}
}
