/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 8:59 AM - 13/04/2025
 * User: kimin
 **/

package com.lamnguyen.authentication_service.service.redis.v1;

import com.lamnguyen.authentication_service.domain.dto.RoleDto;
import com.lamnguyen.authentication_service.service.redis.IRoleRedisManager;
import com.lamnguyen.authentication_service.utils.RedissionClientUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleRedisManagerImpl implements IRoleRedisManager {
	RedisTemplate<String, RoleDto> roleDtoRedisTemplate;
	RedissionClientUtil redissonClient;

	@Override
	public Optional<RoleDto> getRole(String name) {
		return Optional.ofNullable(roleDtoRedisTemplate.opsForValue().get(getKey(name)));
	}

	@Override
	public Optional<RoleDto> cache(String name, Function<String, Optional<RoleDto>> callDB) {
		return redissonClient.synchronize(name, name, (data) -> {
			var cached = getRole(getKey(name));
			if (cached.isPresent()) {
				return cached;
			}

			var role = callDB.apply(name);
			if (role.isEmpty()) return Optional.empty();
			roleDtoRedisTemplate.opsForValue().set(getKey(name), role.get(), Duration.ofMinutes(60));
			return role;
		}, (data) -> {
			var cached = roleDtoRedisTemplate.opsForValue().get(getKey(name));
			return Optional.ofNullable(cached);
		});
	}
}
