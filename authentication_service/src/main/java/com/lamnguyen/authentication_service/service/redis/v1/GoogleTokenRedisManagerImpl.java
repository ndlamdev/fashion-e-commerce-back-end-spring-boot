/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 2:29 PM - 02/03/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.authentication_service.service.redis.v1;

import com.lamnguyen.authentication_service.service.redis.IGoogleTokenRedisManager;
import com.lamnguyen.authentication_service.utils.property.ApplicationProperty;
import com.lamnguyen.authentication_service.utils.property.OtherAuthProperty.GoogleProperty;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class GoogleTokenRedisManagerImpl implements IGoogleTokenRedisManager {
    RedisTemplate<String, Object> redisTemplate;
    GoogleProperty googleProperty;
    ApplicationProperty applicationProperty;

    @Override
    public void setRegisterTokenIdUsingGoogle(String id) {
        redisTemplate.opsForValue().set(googleProperty.getKeyRegisterTokenUsingGoogle() + id, 1, applicationProperty.getExpireRegisterToken(), TimeUnit.MINUTES);
    }

    @Override
    public boolean existRegisterTokenIdUsingGoogle(String id) {
        return redisTemplate.opsForValue().get(googleProperty.getKeyRegisterTokenUsingGoogle() + id) != null;
    }
}
