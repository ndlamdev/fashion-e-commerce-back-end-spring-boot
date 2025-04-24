/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:15 AM-24/04/2025
 * User: kimin
 **/
package com.lamnguyen.inventory_service.service.business;

import com.lamnguyen.inventory_service.message.CreateVariantEvent;
import com.lamnguyen.inventory_service.model.VariantProduct;
import com.lamnguyen.inventory_service.utils.enums.OptionType;

import java.util.List;
import java.util.Map;

/**
 * Interface for inventory management service
 */
public interface IInventoryService {
    
    /**
     * Process a CreateVariantEvent to create inventory records
     * @param event the CreateVariantEvent
     */
    void createVariantProduct(CreateVariantEvent event);
    
    /**
     * Update inventory quantity
     * @param productId the product ID
     * @param options the options map
     * @param quantity the new quantity
     * @return true if updated, false if not found
     */
    boolean updateInventoryQuantity(String productId, Map<OptionType, String> options, int quantity);
    
    /**
     * Get available inventory for a product
     * @param productId the product ID
     * @return list of available inventories
     */
    List<VariantProduct> getAvailableInventory(String productId);
    
    /**
     * Get all inventory for a product
     * @param productId the product ID
     * @return list of all inventories
     */
    List<VariantProduct> getAllInventory(String productId);
}