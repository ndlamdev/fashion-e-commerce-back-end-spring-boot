/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 2:23 PM - 02/03/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.authentication_service.service.redis;

public interface IRedisManager {
	default String generateKey(String prop, long userId, String... others) {
		return prop + "_" + userId + (others.length == 0 ? "" : "_" + String.join("_", others));
	}
}
