/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 7:10 AM-03/05/2025
 * User: kimin
 **/

package com.lamnguyen.cart_service.service.redis.v1;

import com.lamnguyen.cart_service.domain.dto.CartDto;
import com.lamnguyen.cart_service.service.redis.ACacheManage;
import com.lamnguyen.cart_service.service.redis.CallbackDB;
import com.lamnguyen.cart_service.service.redis.ICartRedisManage;
import com.lamnguyen.cart_service.utils.helper.RedissionClientUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class CartRedisManageImpl extends ACacheManage<CartDto> implements ICartRedisManage {
	public CartRedisManageImpl(RedisTemplate<String, CartDto> template, RedissionClientUtil redissonClient) {
		super(template, redissonClient);
	}

	@Override
	public Optional<CartDto> cache(String keyLock, String keyCache, CallbackDB<CartDto> callDB) {
		return cache(keyLock, keyCache, callDB, 60, TimeUnit.MINUTES);
	}

	@Override
	public void delete(String key) {
		this.template.delete(getCartKey(key));
	}

	@Override
	public void save(String key, CartDto data) {
		save(getCartKey(key), data, 60, TimeUnit.MINUTES);
	}

	@Override
	public Optional<CartDto> getCartByUserId(long userId) {
		return get(getCartKey(String.valueOf(userId)));
	}

	private String getCartKey(String userId) {
		return generateHashKey("CART", userId);
	}
}
