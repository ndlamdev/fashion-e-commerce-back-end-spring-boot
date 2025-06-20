/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 11:09 AM-05/05/2025
 * User: kimin
 **/

package com.lamnguyen.cart_service.domain.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
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
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CartItemResponse {
	long id;
	VariantProductDto variant;
	ProductDto product;
	int quantity;
	boolean lock;
	LocalDateTime createAt;
	LocalDateTime updateAt;
}
