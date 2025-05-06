/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 11:09 AM-05/05/2025
 * User: kimin
 **/

package com.lamnguyen.cart_service.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lamnguyen.cart_service.domain.dto.VariantProductDto;

public class CartItemResponse {
	long id;
	@JsonProperty("variant_product")
	VariantProductDto variantProduct;
	int quantity;
	boolean lock;
}
