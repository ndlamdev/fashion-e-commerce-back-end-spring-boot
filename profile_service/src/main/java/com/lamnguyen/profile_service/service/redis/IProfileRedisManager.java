/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 5:19 AM - 19/04/2025
 * User: kimin
 **/

package com.lamnguyen.profile_service.service.redis;

import com.lamnguyen.profile_service.domain.dto.ProfileDto;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

public interface IProfileRedisManager extends IRedisManager<ProfileDto> {
	Optional<ProfileDto> getById(long id);

	void setById(long id, ProfileDto data, long duration, TimeUnit unit);

	void deleteById(long id);

	boolean existById(long id);

	Optional<ProfileDto> cacheByUserId(long id, CacheFunction<ProfileDto> cacheFunction, long duration, TimeUnit unit);

	Optional<ProfileDto> getByUserId(long id);

	void setByUserId(long id, ProfileDto data, long duration, TimeUnit unit);

	void deleteByUserId(long id);

	boolean existByUserId(long id);

	Optional<ProfileDto> cacheById(long id, CacheFunction<ProfileDto> cacheFunction, long duration, TimeUnit unit);
}
