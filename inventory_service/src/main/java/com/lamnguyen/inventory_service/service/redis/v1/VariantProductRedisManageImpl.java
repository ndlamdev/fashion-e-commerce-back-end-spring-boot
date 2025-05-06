/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 4:57 CH-24/04/2025
 * User: Administrator
 **/

package com.lamnguyen.inventory_service.service.redis.v1;

import com.lamnguyen.inventory_service.model.VariantProduct;
import com.lamnguyen.inventory_service.service.redis.ACacheManage;
import com.lamnguyen.inventory_service.service.redis.CallbackDB;
import com.lamnguyen.inventory_service.service.redis.IVariantByProductIdRedisManage;
import com.lamnguyen.inventory_service.utils.property.RedissionClientUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class VariantProductRedisManageImpl extends ACacheManage<VariantProduct[]> implements IVariantByProductIdRedisManage {
	public VariantProductRedisManageImpl(RedisTemplate<String, VariantProduct[]> template, RedissionClientUtil redissonClient) {
		super(template, redissonClient);
	}

	@Override
	public Optional<VariantProduct[]> cache(String keyLock, String keyCache, CallbackDB<VariantProduct[]> callDB) {
		return cache(generateKey(keyLock), generateKey(keyCache), callDB, 60, TimeUnit.MINUTES);
	}

	@Override
	public Optional<VariantProduct[]> cache(String productId, CallbackDB<VariantProduct[]> callDB) {
		return cache(productId, productId, callDB);
	}

	@Override
	public void delete(String productId) {
		template.delete(generateKey(productId));
	}

	@Override
	public void save(String productId, VariantProduct[] data) {
		save(generateKey(productId), data, 60, TimeUnit.MINUTES);
	}

	private String generateKey(String id) {
		return generateHashKey("VARIANT_PRODUCT", id);
	}
}
