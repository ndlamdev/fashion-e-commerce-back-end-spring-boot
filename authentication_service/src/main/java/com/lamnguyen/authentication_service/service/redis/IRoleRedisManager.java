/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 8:58 AM - 13/04/2025
 * User: kimin
 **/

package com.lamnguyen.authentication_service.service.redis;

import com.lamnguyen.authentication_service.domain.dto.RoleDto;

import java.util.Optional;
import java.util.function.Function;

public interface IRoleRedisManager {
	String PREFIX = "ROLE:";

	Optional<RoleDto> getRole(String name);

	Optional<RoleDto> cache(String name, Function<String, Optional<RoleDto>> function);

	default String getKey(String name) {
		return PREFIX + name;
	}
}
