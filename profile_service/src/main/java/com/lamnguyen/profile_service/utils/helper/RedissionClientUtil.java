/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 1:28 PM - 11/04/2025
 * User: kimin
 **/

package com.lamnguyen.profile_service.utils.helper;

import com.lamnguyen.profile_service.service.redis.CacheFunction;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RedissionClientUtil {
	RedissonClient redissonClient;

	public <T, R> Optional<R> synchronize(String key, CacheFunction<R> callbackLocked, CacheFunction<R> callbackUnlocked) {
		String lockKey = "LOCK:" + key;
		RLock lock = redissonClient.getLock(lockKey);

		boolean isLocked = false;
		try {
			isLocked = lock.tryLock(10, 30, TimeUnit.SECONDS);

			if (isLocked) {
				return callbackLocked.invoke();
			} else {
				Thread.sleep(100);
				return callbackUnlocked.invoke();
			}
		} catch (InterruptedException e) {
			return null;
		} finally {
			if (isLocked) {
				lock.unlock();
			}
		}
	}
}