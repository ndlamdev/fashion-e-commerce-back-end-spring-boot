/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 3:21 AM-07/05/2025
 * User: Administrator
 **/

package com.lamnguyen.inventory_service.service.redis.v1;

import com.lamnguyen.inventory_service.model.VariantProduct;
import com.lamnguyen.inventory_service.service.redis.ACacheManage;
import com.lamnguyen.inventory_service.service.redis.CallbackDB;
import com.lamnguyen.inventory_service.service.redis.IVariantRedisManage;
import com.lamnguyen.inventory_service.utils.property.RedissionClientUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class VariantRedisManageImpl extends ACacheManage<VariantProduct> implements IVariantRedisManage {
	public VariantRedisManageImpl(RedisTemplate<String, VariantProduct> template, RedissionClientUtil redissonClient) {
		super(template, redissonClient);
	}


	@Override
	public Optional<VariantProduct> cache(String keyLock, String keyCache, CallbackDB<VariantProduct> callDB) {
		return cache(keyLock, keyCache, callDB, 60, TimeUnit.SECONDS);
	}

	@Override
	public void delete(String key) {
		this.template.delete(generateKey(key));
	}

	@Override
	public void save(String key, VariantProduct data) {
		save(generateKey(key), data, 60, TimeUnit.MINUTES);
	}

	private String generateKey(String variantId) {
		return generateHashKey("VARIANT", variantId);
	}
}
