/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 9:43 AM - 13/04/2025
 * User: kimin
 **/

package com.lamnguyen.product_service.service.redis.v1;

import com.lamnguyen.product_service.domain.dto.CollectionSaveRedisDto;
import com.lamnguyen.product_service.service.redis.ACacheManage;
import com.lamnguyen.product_service.service.redis.CallbackDB;
import com.lamnguyen.product_service.service.redis.ICollectionRedisManager;
import com.lamnguyen.product_service.utils.enums.CollectionType;
import com.lamnguyen.product_service.utils.helper.RedissionClientUtil;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CollectionRedisManagerImpl extends ACacheManage<CollectionSaveRedisDto> implements ICollectionRedisManager {
	public CollectionRedisManagerImpl(RedisTemplate<String, CollectionSaveRedisDto> template, RedissionClientUtil redissonClient) {
		super(template, redissonClient);
	}

	@Override
	public Optional<CollectionSaveRedisDto> get(String key) {
		return super.get(generateKeyCache(key));
	}

	@Override
	public Optional<CollectionSaveRedisDto> cache(String keyLock, String keyCache, CallbackDB<CollectionSaveRedisDto> callDB) {
		return cache(generateKeyCache(keyLock), generateKeyCache(keyCache), callDB, 60, TimeUnit.MINUTES);
	}

	@Override
	public void save(String key, CollectionSaveRedisDto data) {
		save(generateKeyCache(key), data, 60, TimeUnit.MINUTES);
	}

	@Override
	public void delete(String key) {
		super.delete(generateKeyCache(key));
	}

	public String generateKeyCache(String keyCache) {
		return generateHashKey("COLLECTION", keyCache);
	}

	public String generateKeyCache(CollectionType type) {
		return generateHashKey("COLLECTION_TYPE", type.name());
	}

	public List<CollectionSaveRedisDto> getByCollectionType(CollectionType type) {
		return this.template.opsForList().range(generateKeyCache(type), 0, -1);
	}

	@Override
	public List<CollectionSaveRedisDto> cache(CollectionType type, CallbackDB<List<CollectionSaveRedisDto>> callDB) {
		return redissonClient.synchronize(generateKeyCache(type), () -> {
			var cached = getByCollectionType(type);
			if (cached != null && !cached.isEmpty()) {
				return Optional.of(cached);
			}

			var dto = callDB.call();
			if (dto.isEmpty() || dto.get().isEmpty()) return Optional.empty();
			this.template.opsForList().leftPushAll(generateKeyCache(type), dto.get());
			return dto;
		}, () -> Optional.ofNullable(getByCollectionType(type))).orElseGet(ArrayList::new);
	}

	@Override
	public void deleteCollection(CollectionType type) {
		this.template.delete(generateKeyCache(type));
	}

	@Override
	public void deleteCollections() {
		for (CollectionType value : CollectionType.values()) {
			deleteCollection(value);
		}
	}
}
