/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 7:11 AM-19/05/2025
 * User: kimin
 **/

package com.lamnguyen.order_service.domain.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.lamnguyen.order_service.protos.ProductInCartDto;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class OrderItemResponse {
	long id;
	ProductResponse product;
	VariantProductResponse variant;
	int quantity;
	double comparePrice;
	double regularPrice;
}
