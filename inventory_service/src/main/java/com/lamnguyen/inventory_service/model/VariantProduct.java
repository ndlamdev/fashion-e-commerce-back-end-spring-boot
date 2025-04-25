/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:15 AM-24/04/2025
 * User: kimin
 **/
package com.lamnguyen.inventory_service.model;

import com.lamnguyen.inventory_service.utils.enums.OptionType;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Map;

/**
 * MongoDB document class for inventory management.
 * This class represents the inventory for a specific variant of a product.
 */
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document(collection = "inventories")
public class VariantProduct extends MongoBaseDocument {
	@Field(name = "product_id")
	private String productId;
	private String title;
	@Field(name = "regular_price")
	private double regularPrice;
	@Field(name = "compare_price")
	private double comparePrice;
	private String sku;
	private int quantity;
	private Map<OptionType, String> options;
	@Field(name = "product_allow_buy_when_clocked")
	private boolean productAllowBuyWhenClocked; // Cho phép mua khi đang lock
	@Field(name = "product_exclude_discount")
	private boolean productExcludeDiscount; // Cho phép áp dụng discount khi sản phẩm có discount
	@Field(name = "product_apply_allowance_inventory")
	private boolean productApplyAllowanceInventory; // Cho phép mua khi sản phẩm để hết
	int pending; // Số lượng sản phẩm có thể đáp ứng khi cho phép người dùng mua khi biến thể của sản phẩm hết hàng. Dùng khi `productApplyAllowanceInventory` = true
	@Field(name = "product_visibility")
	@Builder.Default
	private boolean productVisibility = true; // sản phẩm có đang không bị lock không
	@Field("is_delete")
	private boolean delete; // Biến thể đã bị xóa khỏi sản phẩm

}
