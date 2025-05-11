/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 9:43 AM - 13/04/2025
 * User: kimin
 **/

package com.lamnguyen.product_service.service.redis.v1;

import com.lamnguyen.product_service.domain.response.ProductResponse;
import com.lamnguyen.product_service.service.redis.ACacheManage;
import com.lamnguyen.product_service.service.redis.CallbackDB;
import com.lamnguyen.product_service.service.redis.IProductResponseRedisManager;
import com.lamnguyen.product_service.utils.helper.RedissionClientUtil;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductResponseRedisManagerImpl extends ACacheManage<ProductResponse> implements IProductResponseRedisManager {
	public ProductResponseRedisManagerImpl(RedisTemplate<String, ProductResponse> template, RedissionClientUtil redissonClient) {
		super(template, redissonClient);
	}

	@Override
	public Optional<ProductResponse> get(String key) {
		return super.get(generateKeyCache(key));
	}

	@Override
	public Optional<ProductResponse> cache(String keyLock, String keyCache, CallbackDB<ProductResponse> callDB) {
		return cache(keyLock, generateKeyCache(keyCache), callDB, 60, TimeUnit.MINUTES);
	}

	@Override
	public void save(String key, ProductResponse data) {
		save(generateKeyCache(key), data, 60, TimeUnit.MINUTES);
	}

	@Override
	public void delete(String key) {
		super.delete(generateKeyCache(key));
	}

	public String generateKeyCache(String keyCache) {
		return generateHashKey("PRODUCT", keyCache);
	}

	@Override
	public Optional<ProductResponse> cache(String id, CallbackDB<ProductResponse> callDB) {
		return cache(id, id, callDB);
	}
}
