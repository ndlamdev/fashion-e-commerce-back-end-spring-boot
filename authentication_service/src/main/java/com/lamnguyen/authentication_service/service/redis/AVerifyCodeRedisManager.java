/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 6:18 PM - 11/04/2025
 * User: kimin
 **/

package com.lamnguyen.authentication_service.service.redis;

import com.lamnguyen.authentication_service.util.property.OtpProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@FieldDefaults(level = AccessLevel.PROTECTED, makeFinal = true)
@Getter
@Setter
public abstract class AVerifyCodeRedisManager implements IVerifyCodeRedisManager {
	RedisTemplate<String, Number> numberRedisTemplate;
	RedisTemplate<String, String> stringRedisTemplate;
	OtpProperty otpProperty;

	public AVerifyCodeRedisManager(RedisTemplate<String, Number> numberRedisTemplate, RedisTemplate<String, String> stringRedisTemplate, OtpProperty otpProperty) {
		this.numberRedisTemplate = numberRedisTemplate;
		this.stringRedisTemplate = stringRedisTemplate;
		this.otpProperty = otpProperty;
	}

	@Override
	public void setCode(long userId, String code) {
		stringRedisTemplate.opsForValue().set(codeKey(userId), code, otpProperty.getExpire(), TimeUnit.MINUTES);
		numberRedisTemplate.opsForValue().set(generateKey(otpProperty.getTotalTryKey(), userId), 0, otpProperty.getExpire(), TimeUnit.MINUTES);
		var totalResendKey = generateKey(otpProperty.getTotalResendKey(), userId);
		var value = numberRedisTemplate.opsForValue().get(totalResendKey);
		if (value != null) {
			stringRedisTemplate.opsForValue().increment(totalResendKey);
			return;
		}

		numberRedisTemplate.opsForValue().set(totalResendKey, 0, otpProperty.getTotalResendExpire(), TimeUnit.MINUTES);
	}

	@Override
	public Optional<String> getCode(long userId) {
		var value = stringRedisTemplate.opsForValue().get(codeKey(userId));
		return value != null ? Optional.of(value) : Optional.empty();
	}

	@Override
	public void removeCode(long userId) {
		stringRedisTemplate.delete(codeKey(userId));
		stringRedisTemplate.delete(generateKey(otpProperty.getTotalResendKey(), userId));
		stringRedisTemplate.delete(generateKey(otpProperty.getTotalTryKey(), userId));
	}

	@Override
	public int increaseTotalTry(long userId) {
		var value = numberRedisTemplate.opsForValue().increment(generateKey(otpProperty.getTotalTryKey(), userId));
		return value != null ? value.intValue() : -1;
	}

	@Override
	public int getTotalResendCode(long userId) {
		var key = generateKey(otpProperty.getTotalResendKey(), userId);
		var value = (Number) numberRedisTemplate.opsForValue().get(key);
		return value == null ? 0 : value.intValue();
	}

	private String codeKey(Long userId) {
		return otpProperty.getKey() + ":CODE_" + userId;
	}
}
