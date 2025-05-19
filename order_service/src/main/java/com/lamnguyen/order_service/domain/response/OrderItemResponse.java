/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 7:11 AM-19/05/2025
 * User: kimin
 **/

package com.lamnguyen.order_service.domain.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class OrderItemResponse {
	long id;
	String productId;
	String variantId;
	int quantity;
	double comparePrice;
	double regularPrice;
}
