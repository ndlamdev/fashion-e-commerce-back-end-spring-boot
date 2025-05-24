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

	public Optional<ProfileDto> getById(long id) {
		return get(getKeyUsingId(id));
	}

	public void setById(long id, ProfileDto data, long duration, TimeUnit unit) {
		set(getKeyUsingId(id), data, duration, unit);
	}

	public void deleteById(long id) {
		delete(getKeyUsingId(id));
	}

	public boolean existById(long id) {
		return exist(getKeyUsingId(id));
	}

	public Optional<ProfileDto> cacheById(long id, CacheFunction<ProfileDto> cacheFunction, long duration, TimeUnit unit) {
		return cache(getKeyLockUsingId(id), getKeyUsingId(id), cacheFunction, duration, unit);
	}

	public Optional<ProfileDto> getByUserId(long id) {
		return get(getKeyUsingUserId(id));
	}

	public void setByUserId(long id, ProfileDto data, long duration, TimeUnit unit) {
		set(getKeyUsingUserId(id), data, duration, unit);
	}

	public void deleteByUserId(long id) {
		delete(getKeyUsingUserId(id));
	}

	public boolean existByUserId(long id) {
		return exist(getKeyUsingUserId(id));
	}

	public Optional<ProfileDto> cacheByUserId(long id, CacheFunction<ProfileDto> cacheFunction, long duration, TimeUnit unit) {
		return cache(getKeyLockUsingUserId(id), getKeyUsingUserId(id), cacheFunction, duration, unit);
	}

	private String getKeyUsingId(long id) {
		return "PROFILE:ID_" + id;
	}

	private String getKeyLockUsingId(long id) {
		return "LOCK:ID_" + getKeyUsingId(id);
	}

	private String getKeyUsingUserId(long id) {
		return "PROFILE:USER_ID_" + id;
	}

	private String getKeyLockUsingUserId(long id) {
		return "LOCK:USER_ID_" + getKeyUsingId(id);
	}
}
