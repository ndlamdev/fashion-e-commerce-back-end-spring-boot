/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 7:11 AM-03/05/2025
 * User: kimin
 **/

package com.lamnguyen.cart_service.domain.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartDto {
	long id;
	Long userId;
	List<CartItemDto> cartItems;
	boolean lock;
	LocalDateTime createAt;
	LocalDateTime updateAt;
}
