/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 6:22 PM-19/05/2025
 * User: kimin
 **/

package com.lamnguyen.cart_service.message;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeleteCartItemsMessage {
	long userId;
	List<String> variantIds;
}
