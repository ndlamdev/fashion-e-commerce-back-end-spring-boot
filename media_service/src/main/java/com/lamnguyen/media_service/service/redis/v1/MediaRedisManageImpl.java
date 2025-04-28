/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 8:19 PM-23/04/2025
 * User: kimin
 **/

package com.lamnguyen.media_service.service.redis.v1;

import com.lamnguyen.media_service.domain.dto.MediaDto;
import com.lamnguyen.media_service.service.redis.ACacheManage;
import com.lamnguyen.media_service.service.redis.CallbackDB;
import com.lamnguyen.media_service.service.redis.IMediaRedisManage;
import com.lamnguyen.media_service.utils.helper.RedissionClientUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class MediaRedisManageImpl extends ACacheManage<MediaDto> implements IMediaRedisManage {
	public MediaRedisManageImpl(RedisTemplate<String, MediaDto> template, RedissionClientUtil redissonClient) {
		super(template, redissonClient);
	}

	@Override
	public Optional<MediaDto> cache(String keyLock, String keyCache, CallbackDB<MediaDto> callDB) {
		return cache(keyLock, keyCache, callDB, 60, TimeUnit.MINUTES);
	}

	@Override
	public void delete(String key) {
		this.template.delete(generateKey(key));
	}

	@Override
	public void save(String key, MediaDto data) {
		save(generateKey(key), data, 60, TimeUnit.MINUTES);
	}

	private String generateKey(String key) {
		return generateHashKey("MEDIA", key);
	}

	@Override
	public Optional<MediaDto> cache(String id, CallbackDB<MediaDto> callDB) {
		return cache(generateKey(id), generateKey(id), callDB);
	}

	@Override
	public void save(MediaDto data) {
		this.save(data.getId(), data);
	}
}
