/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 6:54 PM - 09/04/2025
 * User: kimin
 **/

package com.lamnguyen.order_service.service.redis;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

public interface ICacheManage<R> extends IRedisManager {
	Optional<R> get(String key);

	Optional<R> cache(String keyLock, String keyCache, CallbackDB<R> callDB);

	Optional<R> cache(String keyLock, String keyCache, CallbackDB<R> callDB, long duration, TimeUnit unit);

	void delete(String key);

	void save(String key, R data);

	void save(String key, R data, long duration, TimeUnit unit);
}
