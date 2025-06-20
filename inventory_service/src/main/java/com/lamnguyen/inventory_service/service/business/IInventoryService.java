/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:15 AM-24/04/2025
 * User: kimin
 **/
package com.lamnguyen.inventory_service.service.business;

import com.lamnguyen.inventory_service.domain.response.VariantResponse;
import com.lamnguyen.inventory_service.message.DataVariantEvent;
import com.lamnguyen.inventory_service.model.VariantProduct;
import com.lamnguyen.inventory_service.protos.VariantAndInventoryInfo;

import java.util.List;
import java.util.Map;

public interface IInventoryService {
	void createVariantProduct(DataVariantEvent event);

	List<VariantProduct> getAllInventoryByProductId(String productId);

	void updateVariantProduct(DataVariantEvent event);

	boolean existsVariantProductId(String variantId);

	String getProductId(String variantId);

	VariantProduct getVariantProductById(String variantId);

	List<VariantProduct> updateVariantProducts(Map<String, Integer> quantities);

	Map<String, VariantAndInventoryInfo> getVariantAndInventoryInfo(List<String> productIds);

    List<VariantResponse> getAll();

	void updateQuantity(String id, int quantity);
}