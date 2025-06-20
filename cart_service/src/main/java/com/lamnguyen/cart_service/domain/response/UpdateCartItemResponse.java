/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 6:16 AM-08/05/2025
 * User: kimin
 **/

package com.lamnguyen.cart_service.domain.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCartItemResponse {
	long cartItemId;
	int quantity;
}
