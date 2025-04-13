/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 6:54 PM - 09/04/2025
 * User: kimin
 **/

package com.lamnguyen.product_service.utils.redis;

import com.lamnguyen.product_service.utils.RedissionClientUtil;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@FieldDefaults(level = AccessLevel.PROTECTED, makeFinal = true)
public abstract class ACacheManage<R> implements ICacheManage<R> {
	RedisTemplate<String, R> template;
	RedissionClientUtil redissonClient;

	public ACacheManage(RedisTemplate<String, R> template, RedissionClientUtil redissonClient) {
		this.template = template;
		this.redissonClient = redissonClient;
	}

	@Override
	public final Optional<R> get(String key) {
		return Optional.ofNullable(template.opsForValue().get(key));
	}

	@Override
	public final void save(String key, R data, long duration, TimeUnit unit) {
		template.opsForValue().set(key, data, duration, unit);
	}

	@Override
	public final <T> Optional<R> cache(String keyLock, String keyCache, T input, Function<T, Optional<R>> callDB, long duration, TimeUnit unit) {
		return redissonClient.synchronize(keyLock, input, (s) -> {
			var cached = get(keyCache);
			if (cached.isPresent()) {
				return cached;
			}

			var dto = callDB.apply(input);
			if (dto.isEmpty()) return Optional.empty();
			save(keyCache, dto.get(), duration, unit);
			return dto;
		}, (data) -> get(keyCache));
	}
}
