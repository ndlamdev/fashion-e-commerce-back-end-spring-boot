/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 6:54 PM - 09/04/2025
 * User: kimin
 **/

package com.lamnguyen.product_service.utils.redis;

import com.lamnguyen.product_service.service.redis.IRedisManager;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public interface ICacheManage<R> extends IRedisManager {
	Optional<R> get(String key);

	<T> Optional<R> cache(String keyLock, String keyCache, T input, Function<T, Optional<R>> callDB);

	<T> Optional<R> cache(String keyLock, String keyCache, T input, Function<T, Optional<R>> callDB, long duration, TimeUnit unit);

	void delete(String key);

	void save(String key, R data);

	void save(String key, R data, long duration, TimeUnit unit);
}
