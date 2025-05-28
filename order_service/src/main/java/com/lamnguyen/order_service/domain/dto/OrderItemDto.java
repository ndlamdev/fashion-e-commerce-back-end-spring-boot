/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 7:11 AM-19/05/2025
 * User: kimin
 **/

package com.lamnguyen.order_service.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto  {
	long id;
	String productId;
	String variantId;
	int quantity;
	@JsonProperty("compare_price")
	double comparePrice;
	@JsonProperty("discount_price")
	double regularPrice;
}
