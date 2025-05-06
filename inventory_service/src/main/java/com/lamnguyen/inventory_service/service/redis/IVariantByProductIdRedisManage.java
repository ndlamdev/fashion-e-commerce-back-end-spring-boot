/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 4:56 CH-24/04/2025
 * User: Administrator
 **/

package com.lamnguyen.inventory_service.service.redis;

import com.lamnguyen.inventory_service.model.VariantProduct;

import java.util.Optional;

public interface IVariantByProductIdRedisManage extends ICacheManage<VariantProduct[]> {
	Optional<VariantProduct[]> cache(String productId, CallbackDB<VariantProduct[]> callDB);

	void delete(String productId);

	void save(String productId, VariantProduct[] data);
}
