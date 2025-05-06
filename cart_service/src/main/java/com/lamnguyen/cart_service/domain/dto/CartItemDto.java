/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 7:12 AM-03/05/2025
 * User: kimin
 **/

package com.lamnguyen.cart_service.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto {
	long id;
	String variantId;
	String productId;
	int quantity;
	boolean lock;
	@JsonProperty("create_at")
	LocalDateTime createAt;
	@JsonProperty("update_at")
	LocalDateTime updateAt;
}
