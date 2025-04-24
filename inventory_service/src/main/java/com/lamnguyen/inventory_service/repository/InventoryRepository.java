/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:15 AM-24/04/2025
 * User: kimin
 **/
package com.lamnguyen.inventory_service.repository;

import com.lamnguyen.inventory_service.model.VariantInventory;
import com.lamnguyen.inventory_service.utils.enums.OptionType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Repository interface for VariantInventory document
 */
@Repository
public interface InventoryRepository extends MongoRepository<VariantInventory, String> {
    
    /**
     * Find inventory by product ID
     * @param productId the product ID
     * @return list of inventories for the product
     */
    List<VariantInventory> findByProductId(String productId);
    
    /**
     * Find inventory by product ID and options
     * @param productId the product ID
     * @param options the options map
     * @return the inventory if found
     */
    Optional<VariantInventory> findByProductIdAndOptions(String productId, Map<OptionType, String> options);
    
    /**
     * Find available inventories by product ID
     * @param productId the product ID
     * @return list of available inventories for the product
     */
    List<VariantInventory> findByProductIdAndAvailableTrue(String productId);
    
    /**
     * Find inventory by SKU
     * @param sku the SKU
     * @return the inventory if found
     */
    Optional<VariantInventory> findBySku(String sku);
}