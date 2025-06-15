/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 5:20 AM - 19/04/2025
 * User: kimin
 **/

package com.lamnguyen.profile_service.service.redis.impl;

import com.lamnguyen.profile_service.domain.dto.ProfileDto;
import com.lamnguyen.profile_service.service.redis.ARedisManager;
import com.lamnguyen.profile_service.service.redis.CacheFunction;
import com.lamnguyen.profile_service.service.redis.IProfileRedisManager;
import com.lamnguyen.profile_service.utils.helper.RedissionClientUtil;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProfileRedisManagerImpl extends ARedisManager<ProfileDto> implements IProfileRedisManager {
    public ProfileRedisManagerImpl(RedisTemplate<String, ProfileDto> redisTemplate, RedissionClientUtil redissonClient) {
        super(redisTemplate, redissonClient);
    }

    public Optional<ProfileDto> getByUserId(long id) {
        return get(getKeyUsingUserId(id));
    }

    public void deleteByUserId(long userId) {
        delete(getKeyUsingUserId(userId));
    }

    public Optional<ProfileDto> cacheByUserId(long userId, CacheFunction<ProfileDto> cacheFunction, long duration, TimeUnit unit) {
        return cache(getKeyLockUsingUserId(userId), getKeyUsingUserId(userId), cacheFunction, duration, unit);
    }

    private String getKeyUsingUserId(long userId) {
        return "PROFILE:USER_ID_" + userId;
    }

    private String getKeyLockUsingUserId(long userId) {
        return "LOCK:USER_ID_" + userId;
    }
}
