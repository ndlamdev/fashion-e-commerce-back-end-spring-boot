/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 5:20 AM - 19/04/2025
 * User: kimin
 **/

package com.lamnguyen.profile_service.service.redis;

import com.lamnguyen.profile_service.utils.RedissionClientUtil;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public abstract class ARedisManager<T> implements IRedisManager<T> {
	RedisTemplate<String, T> redisTemplate;
	RedissionClientUtil redissonClient;

	public ARedisManager(RedisTemplate<String, T> redisTemplate, RedissionClientUtil redissonClient) {
		this.redisTemplate = redisTemplate;
		this.redissonClient = redissonClient;
	}

	@Override
	public Optional<T> get(String key) {
		var data = redisTemplate.opsForValue().get(key);
		return Optional.ofNullable(data);
	}

	@Override
	public void set(String key, T data, long duration, TimeUnit unit) {
		redisTemplate.opsForValue().set(key, data, duration, unit);
	}

	@Override
	public void delete(String key) {
		redisTemplate.delete(key);
	}

	@Override
	public boolean exist(String key) {
		return get(key).isPresent();
	}

	@Override
	public Optional<T> cache(String keyLock, String key, CacheFunction<T> callDB, long duration, TimeUnit unit) {
		return redissonClient.synchronize(keyLock, () -> {
			var cached = get(key);
			if (cached.isPresent()) {
				return cached;
			}

			var dto = callDB.invoke();
			if (dto.isEmpty()) return Optional.empty();
			set(key, dto.get(), duration, unit);
			return dto;
		}, () -> get(key));
	}
}
