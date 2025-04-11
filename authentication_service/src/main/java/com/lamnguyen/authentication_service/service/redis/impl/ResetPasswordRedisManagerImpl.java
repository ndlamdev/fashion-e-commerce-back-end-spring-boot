/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 2:29 PM - 02/03/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.authentication_service.service.redis.impl;

import com.lamnguyen.authentication_service.service.redis.AVerifyCodeRedisManager;
import com.lamnguyen.authentication_service.service.redis.IResetPasswordRedisManager;
import com.lamnguyen.authentication_service.util.property.OtpProperty;
import com.lamnguyen.authentication_service.util.property.TokenProperty;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ResetPasswordRedisManagerImpl extends AVerifyCodeRedisManager implements IResetPasswordRedisManager {
	TokenProperty.ResetPasswordTokenProperty tokenProperty;

	public ResetPasswordRedisManagerImpl(RedisTemplate<String, Number> numberRedisTemplate, RedisTemplate<String, String> stringRedisTemplate, OtpProperty.ResetPasswordVerification otpProperty, TokenProperty.ResetPasswordTokenProperty tokenProperty) {
		super(numberRedisTemplate, stringRedisTemplate, otpProperty);
		this.tokenProperty = tokenProperty;
	}

	@Override
	public boolean existTokenResetPasswordInBlacklist(long userId, String tokenId) {
		return Objects.equals(numberRedisTemplate.opsForValue().get(generateKey(tokenProperty.getTokenInBlacklistKey(), userId, tokenId)), 1);
	}

	@Override
	public void addTokenResetPasswordInBlacklist(long userId, String tokenId) {
		numberRedisTemplate.opsForValue().set(generateKey(tokenProperty.getTokenInBlacklistKey(), userId, tokenId), 1, tokenProperty.getExpireToken(), TimeUnit.MINUTES);
	}
}
