/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:15 AM-24/04/2025
 * User: kimin
 **/
package com.lamnguyen.inventory_service.model;

import com.lamnguyen.inventory_service.utils.enums.OptionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
	private boolean hide;
	private int pending;
	@Field(name = "product_stoppage")
	private boolean productStoppage;
	@Field(name = "product_allow_buy_hidden")
	private boolean productAllowBuyHidden;
	@Field(name = "product_exclude_discount")
	private boolean productExcludeDiscount;
	private boolean available;
	@Field(name = "product_apply_allowance_inventory")
	private boolean productApplyAllowanceInventory;
	@Field(name = "product_visibility")
	private boolean productVisibility;
}
