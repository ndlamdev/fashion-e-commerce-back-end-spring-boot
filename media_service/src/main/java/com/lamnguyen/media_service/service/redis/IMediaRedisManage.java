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

public interface IMediaRedisManage {
	Optional<MediaDto> get(String id);

	Optional<MediaDto> cache(String id, CallbackDB<MediaDto> callDB);

	void delete(String id);

	void save(MediaDto data);
}
