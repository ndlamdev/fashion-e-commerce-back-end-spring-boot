/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 2:29 PM - 02/03/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.authentication_service.service.redis.v1;

import com.lamnguyen.authentication_service.service.redis.AVerifyCodeRedisManager;
import com.lamnguyen.authentication_service.service.redis.IRegisterCodeRedisManager;
import com.lamnguyen.authentication_service.utils.property.OtpProperty;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class RegisterCodeRedisManagerImpl extends AVerifyCodeRedisManager implements IRegisterCodeRedisManager {
	public RegisterCodeRedisManagerImpl(RedisTemplate<String, Number> numberRedisTemplate, RedisTemplate<String, String> stringRedisTemplate, OtpProperty.AccountVerification otpProperty) {
		super(numberRedisTemplate, stringRedisTemplate, otpProperty);
	}
}
