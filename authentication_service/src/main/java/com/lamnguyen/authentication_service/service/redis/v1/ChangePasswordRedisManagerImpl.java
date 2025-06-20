/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 2:29 PM - 02/03/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.authentication_service.service.redis.v1;

import com.lamnguyen.authentication_service.service.redis.IChangePasswordRedisManager;
import com.lamnguyen.authentication_service.utils.property.TokenProperty;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ChangePasswordRedisManagerImpl implements IChangePasswordRedisManager {
	RedisTemplate<String, Number> numberRedisTemplate;
	TokenProperty.ResetPasswordTokenProperty tokenProperty;

	@Override
	public Long getDateTimeChangePassword(long userId) {
		var number = numberRedisTemplate.opsForValue().get(generateKey(tokenProperty.getDateTimeChangePasswordKey(), userId));
		if (number == null) return null;
		return number.longValue();
	}

	@Override
	public void setDateTimeChangePassword(long userId, LocalDateTime dateTime) {
		numberRedisTemplate.opsForValue().set(generateKey(tokenProperty.getDateTimeChangePasswordKey(), userId), LocalDateTime.now().toEpochSecond(ZoneOffset.UTC), tokenProperty.getExpireToken(), TimeUnit.MINUTES);
	}
}
