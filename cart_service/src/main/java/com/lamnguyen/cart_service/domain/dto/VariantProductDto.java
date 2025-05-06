/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 11:10 AM-05/05/2025
 * User: kimin
 **/

package com.lamnguyen.cart_service.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lamnguyen.cart_service.utils.enums.OptionType;
import lombok.*;

import java.util.Map;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VariantProductDto {
	String id;
	private ProductDto product;
	private String title;
	@JsonProperty("regular_price")
	private double regularPrice;
	@JsonProperty("compare_price")
	private double comparePrice;
	private String sku;
	private int quantity;
	private Map<OptionType, String> options;
	@JsonProperty("product_allow_buy_when_clocked")
	private boolean productAllowBuyWhenClocked; // Cho phép mua khi đang lock
	@JsonProperty("product_exclude_discount")
	private boolean productExcludeDiscount; // Cho phép áp dụng discount khi sản phẩm có discount
	@JsonProperty("product_apply_allowance_inventory")
	private boolean productApplyAllowanceInventory; // Cho phép mua khi sản phẩm để hết
	int pending; // Số lượng sản phẩm có thể đáp ứng khi cho phép người dùng mua khi biến thể của sản phẩm hết hàng. Dùng khi `productApplyAllowanceInventory` = true
	@JsonProperty("product_visibility")
	private boolean productVisibility = true; // sản phẩm có đang không bị lock không
	@JsonProperty("is_delete")
	private boolean delete; // Biến thể đã bị xóa khỏi sản phẩm
	boolean lock;
}
