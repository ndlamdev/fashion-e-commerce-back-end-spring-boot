/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 6:59 PM - 11/04/2025
 * User: kimin
 **/

package com.lamnguyen.authentication_service.service.redis;

import com.lamnguyen.authentication_service.util.property.TokenProperty;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@FieldDefaults(makeFinal = true, level = AccessLevel.PROTECTED)
public abstract class ATokenRedisManager implements ITokenRedisManager {
	RedisTemplate<String, Number> numberRedisTemplate;
	TokenProperty tokenProperty;

	public ATokenRedisManager(RedisTemplate<String, Number> numberRedisTemplate, TokenProperty applicationProperty) {
		this.numberRedisTemplate = numberRedisTemplate;
		this.tokenProperty = applicationProperty;
	}

	@Override
	public void addTokenIdInBlackList(long userId, String tokenId) {
		numberRedisTemplate.opsForValue().set(generateKey(tokenProperty.getTokenInBlacklistKey(), userId, tokenId), 1, tokenProperty.getExpireToken(), TimeUnit.MINUTES);
	}

	@Override
	public void setTokenId(long userId, String tokenId) {
		numberRedisTemplate.opsForValue().set(generateHashKey(tokenProperty.getTokenKey(), userId, tokenId), 1, tokenProperty.getExpireToken(), TimeUnit.MINUTES);
	}

	@Override
	public void removeTokenId(long userId, String tokenId) {
		numberRedisTemplate.delete(generateHashKey(tokenProperty.getTokenKey(), userId, tokenId));
	}

	@Override
	public boolean existTokenId(long userId, String tokenId) {
		var key = generateHashKey(tokenProperty.getTokenKey(), userId, tokenId);
		var token = numberRedisTemplate.opsForValue().get(key);
		return Objects.equals(token, 1);
	}

	@Override
	public boolean existTokenIdInBlacklist(long userId, String tokenId) {
		return Objects.equals(numberRedisTemplate.opsForValue().get(generateKey(tokenProperty.getTokenInBlacklistKey(), userId, tokenId)), 1);
	}
}
