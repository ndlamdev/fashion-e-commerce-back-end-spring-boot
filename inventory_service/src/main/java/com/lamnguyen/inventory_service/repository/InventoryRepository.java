/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:15 AM-24/04/2025
 * User: kimin
 **/
package com.lamnguyen.inventory_service.repository;

import com.lamnguyen.inventory_service.model.VariantProduct;
import com.lamnguyen.inventory_service.utils.enums.OptionType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Repository interface for VariantProduct document
 */
@Repository
public interface InventoryRepository extends MongoRepository<VariantProduct, String> {

	/**
	 * Find inventory by product ID
	 *
	 * @param productId the product ID
	 * @return list of inventories for the product
	 */
	List<VariantProduct> findByProductIdAndDeleteFalseAndLockFalse(String productId);

	/**
	 * Find inventory by product ID and options
	 *
	 * @param productId the product ID
	 * @param options   the options map
	 * @return the inventory if found
	 */
	Optional<VariantProduct> findByProductIdAndOptions(String productId, Map<OptionType, String> options);

	/**
	 * Find available inventories by product ID
	 *
	 * @param productId the product ID
	 * @return list of available inventories for the product
	 */
	List<VariantProduct> findByProductIdAndDeleteIsFalse(String productId);

	/**
	 * Find inventory by SKU
	 *
	 * @param sku the SKU
	 * @return the inventory if found
	 */
	Optional<VariantProduct> findBySku(String sku);
}