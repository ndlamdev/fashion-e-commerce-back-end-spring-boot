/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 11:10 AM-05/05/2025
 * User: kimin
 **/

package com.lamnguyen.order_service.domain.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.lamnguyen.order_service.utils.enums.OptionType;
import lombok.*;

import java.util.Map;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class VariantProductResponse {
	String id;
	private String productId;
	private String title;
	private double regularPrice;
	private double comparePrice;
	private String sku;
	private int quantity;
	private Map<OptionType, String> options;
	private boolean productAllowBuyWhenClocked; // Cho phép mua khi đang lock
	private boolean productExcludeDiscount; // Cho phép áp dụng discount khi sản phẩm có discount
	private boolean productApplyAllowanceInventory; // Cho phép mua khi sản phẩm để hết
	int pending; // Số lượng sản phẩm có thể đáp ứng khi cho phép người dùng mua khi biến thể của sản phẩm hết hàng. Dùng khi `productApplyAllowanceInventory` = true
	@Builder.Default
	private boolean productVisibility = true; // sản phẩm có đang không bị lock không
	private boolean delete; // Biến thể đã bị xóa khỏi sản phẩm
	boolean lock;
}
