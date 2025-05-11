/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 6:54 PM - 09/04/2025
 * User: kimin
 **/

package com.lamnguyen.cart_service.service.redis;

import com.lamnguyen.cart_service.utils.helper.RedissionClientUtil;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@FieldDefaults(level = AccessLevel.PROTECTED, makeFinal = true)
public abstract class ACacheManage<R> implements ICacheManage<R> {
	RedisTemplate<String, R> template;
	RedissionClientUtil redissonClient;
	long duration;
	TimeUnit timeUnit;

	public ACacheManage(RedisTemplate<String, R> template, RedissionClientUtil redissonClient, long duration, TimeUnit timeUnit) {
		this.template = template;
		this.redissonClient = redissonClient;
		this.duration = duration;
		this.timeUnit = timeUnit;
	}

	public ACacheManage(RedisTemplate<String, R> template, RedissionClientUtil redissonClient) {
		this(template, redissonClient, 1, TimeUnit.HOURS);
	}

	@Override
	public Optional<R> get(String key) {
		var data = Optional.ofNullable(template.opsForValue().get(key));
		if (data.isEmpty()) return Optional.empty();
		this.template.expire(key, duration, timeUnit);
		return data;
	}

	@Override
	public void delete(String key) {
		this.template.delete(key);
	}

	@Override
	public final void save(String key, R data, long duration, TimeUnit unit) {
		template.opsForValue().set(key, data, duration, unit);
	}

	@Override
	public final Optional<R> cache(String keyLock, String keyCache, CallbackDB<R> callDB, long duration, TimeUnit unit) {
		return redissonClient.synchronize(keyLock, () -> {
			var cached = get(keyCache);
			if (cached.isPresent()) {
				return cached;
			}

			var dto = callDB.call();
			if (dto.isEmpty()) return Optional.empty();
			save(keyCache, dto.get(), duration, unit);
			return dto;
		}, () -> get(keyCache));
	}
}
