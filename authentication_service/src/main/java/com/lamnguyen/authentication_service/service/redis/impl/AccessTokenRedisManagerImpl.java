/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 2:29 PM - 02/03/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.authentication_service.service.redis.impl;

import com.lamnguyen.authentication_service.service.redis.ATokenRedisManager;
import com.lamnguyen.authentication_service.service.redis.IAccessTokenRedisManager;
import com.lamnguyen.authentication_service.util.property.TokenProperty;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AccessTokenRedisManagerImpl extends ATokenRedisManager implements IAccessTokenRedisManager {

	public AccessTokenRedisManagerImpl(RedisTemplate<String, Number> numberRedisTemplate, TokenProperty.AccessTokenProperty tokenProperty) {
		super(numberRedisTemplate, tokenProperty);
	}
}
