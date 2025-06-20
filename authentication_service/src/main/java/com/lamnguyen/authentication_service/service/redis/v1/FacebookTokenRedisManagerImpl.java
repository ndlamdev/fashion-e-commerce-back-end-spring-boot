/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 2:29 PM - 02/03/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.authentication_service.service.redis.v1;

import com.lamnguyen.authentication_service.service.redis.IFacebookTokenRedisManager;
import com.lamnguyen.authentication_service.utils.property.ApplicationProperty;
import com.lamnguyen.authentication_service.utils.property.OtherAuthProperty.FacebookProperty;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class FacebookTokenRedisManagerImpl implements IFacebookTokenRedisManager {
	RedisTemplate<String, Object> redisTemplate;
	FacebookProperty facebookProperty;
	ApplicationProperty applicationProperty;

	@Override
	public void setRegisterTokenIdUsingFacebook(String id) {
		redisTemplate.opsForValue().set(facebookProperty.getKeyRegisterTokenUsingFacebook() + id, 1, applicationProperty.getExpireRegisterToken(), TimeUnit.MINUTES);
	}

	@Override
	public boolean existRegisterTokenIdUsingFacebook(String id) {
		return redisTemplate.opsForValue().get(facebookProperty.getKeyRegisterTokenUsingFacebook() + id) != null;
	}

	@Override
	public void setAccessTokenFacebook(String token) {
		redisTemplate.opsForValue().set(facebookProperty.getKeyAccessTokenFacebook() + token, 1, 5, TimeUnit.HOURS);
	}

	@Override
	public boolean existAccessTokenFacebook(String token) {
		return redisTemplate.opsForValue().get(facebookProperty.getKeyAccessTokenFacebook() + token) != null;
	}
}
