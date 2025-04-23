/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 2:29 PM - 02/03/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.authentication_service.service.redis.v1;

import com.lamnguyen.authentication_service.service.redis.IFacebookTokenRedisManager;
import com.lamnguyen.authentication_service.utils.property.OtherAuthProperty.FacebookProperty;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class FacebookTokenRedisManagerImpl implements IFacebookTokenRedisManager {
	RedisTemplate<String, Object> redisTemplate;
	FacebookProperty applicationProperty;

	@Override
	public void setRegisterTokenIdUsingFacebook(String id) {
		redisTemplate.opsForValue().set(applicationProperty.keyRegisterTokenUsingFacebook() + id, 1);
	}

	@Override
	public boolean existRegisterTokenIdUsingFacebook(String id) {
		return redisTemplate.opsForValue().get(applicationProperty.keyRegisterTokenUsingFacebook() + id) != null;
	}

	@Override
	public void setAccessTokenFacebook(String token) {
		redisTemplate.opsForValue().set(applicationProperty.keyAccessTokenFacebook() + token, 1);
	}

	@Override
	public boolean existAccessTokenFacebook(String token) {
		return redisTemplate.opsForValue().get(applicationProperty.keyAccessTokenFacebook() + token) != null;
	}
}
