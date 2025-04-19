/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 5:15 AM - 19/04/2025
 * User: kimin
 **/

package com.lamnguyen.profile_service.service.redis;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

public interface IRedisManager<T> {
	Optional<T> get(String key);

	void set(String key, T data, long duration, TimeUnit unit);

	void delete(String key);

	boolean exist(String key);

	Optional<T> cache(String keyLock, String key, CacheFunction<T> cacheFunction, long duration, TimeUnit unit);
}
