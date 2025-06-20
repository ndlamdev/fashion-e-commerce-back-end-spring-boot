/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 11:09 AM-05/05/2025
 * User: kimin
 **/

package com.lamnguyen.cart_service.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lamnguyen.cart_service.domain.dto.ProductDto;
import com.lamnguyen.cart_service.domain.dto.VariantProductDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartItemResponse {
	long id;
	VariantProductDto variant;
	ProductDto product;
	int quantity;
	boolean lock;
	@JsonProperty("create_at")
	LocalDateTime createAt;
	@JsonProperty("update_at")
	LocalDateTime updateAt;
}
