/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 8:19 PM-23/04/2025
 * User: kimin
 **/

package com.lamnguyen.media_service.service.redis;

import com.lamnguyen.media_service.domain.dto.MediaDto;

import java.util.Optional;

public interface IMediaRedisManage extends ICacheManage<MediaDto> {
	Optional<MediaDto> cacheById(String id, CallbackDB<MediaDto> callDB);

	void deleteById(String id);

	Optional<MediaDto> getById(String id);

	Optional<MediaDto> cacheByName(String name, CallbackDB<MediaDto> callDB);

	void deleteByName(String name);

	Optional<MediaDto> getByName(String name);
}
