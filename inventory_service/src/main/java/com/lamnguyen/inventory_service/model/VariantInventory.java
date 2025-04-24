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
public class VariantInventory extends MongoBaseDocument {
	/**
	 * The ID of the product this inventory belongs to
	 */
	private String productId;

	/**
	 * A map of option types to their values for this specific variant
	 * For example: {SIZE: "M", COLOR: "Red"}
	 */
	private Map<OptionType, String> options;

	/**
	 * Whether this variant is available for purchase
	 */
	private boolean available;

	/**
	 * The quantity available in stock
	 */
	private int quantity;

	/**
	 * The SKU (Stock Keeping Unit) for this variant
	 */
	private String sku;
}
