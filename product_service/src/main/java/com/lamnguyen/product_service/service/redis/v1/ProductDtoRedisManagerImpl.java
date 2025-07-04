/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 9:43 AM - 13/04/2025
 * User: kimin
 **/

package com.lamnguyen.product_service.service.redis.v1;

import com.lamnguyen.product_service.domain.dto.ProductDto;
import com.lamnguyen.product_service.service.redis.ACacheManage;
import com.lamnguyen.product_service.service.redis.CallbackDB;
import com.lamnguyen.product_service.service.redis.IProductDtoRedisManager;
import com.lamnguyen.product_service.utils.helper.RedissionClientUtil;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductDtoRedisManagerImpl extends ACacheManage<ProductDto> implements IProductDtoRedisManager {
	public ProductDtoRedisManagerImpl(RedisTemplate<String, ProductDto> template, RedissionClientUtil redissonClient) {
		super(template, redissonClient);
	}

	@Override
	public Optional<ProductDto> get(String key) {
		return super.get(generateKeyCache(key));
	}

	@Override
	public Optional<ProductDto> cache(String keyLock, String keyCache, CallbackDB<ProductDto> callDB) {
		return cache(keyLock, generateKeyCache(keyCache), callDB, 60, TimeUnit.MINUTES);
	}

	@Override
	public void save(String key, ProductDto data) {
		super.save(generateKeyCache(key), data, 60, TimeUnit.MINUTES);
	}

	@Override
	public void delete(String key) {
		super.delete(generateKeyCache(key));
	}

	public String generateKeyCache(String keyCache) {
		return generateHashKey("PRODUCT_DTO", keyCache);
	}
}
