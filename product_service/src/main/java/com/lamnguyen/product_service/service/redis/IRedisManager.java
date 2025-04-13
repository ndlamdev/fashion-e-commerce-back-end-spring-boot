/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 2:23 PM - 02/03/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.product_service.service.redis;

public interface IRedisManager {
	default String generateKey(String prop, long userId, String... others) {
		return prop + "_" + userId + (others.length == 0 ? "" : "_" + String.join("_", others));
	}

	default String generateHashKey(String prop, long userId, String... others) {
		return prop + ":" + userId + (others.length == 0 ? "" : "_" + String.join("_", others));
	}

	default String generateKey(String prop, String id, String... others) {
		return prop + "_" + id + (others.length == 0 ? "" : "_" + String.join("_", others));
	}

	default String generateHashKey(String prop, String id, String... others) {
		return prop + ":" + id + (others.length == 0 ? "" : "_" + String.join("_", others));
	}
}
