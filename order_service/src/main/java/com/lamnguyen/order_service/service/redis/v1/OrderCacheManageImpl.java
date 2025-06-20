/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 4:59 PM-28/05/2025
 * User: kimin
 **/

package com.lamnguyen.order_service.service.redis.v1;

import com.lamnguyen.order_service.domain.dto.OrderDto;
import com.lamnguyen.order_service.service.redis.ACacheManage;
import com.lamnguyen.order_service.service.redis.CallbackDB;
import com.lamnguyen.order_service.service.redis.IOrderCacheManage;
import com.lamnguyen.order_service.utils.helper.RedissionClientUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class OrderCacheManageImpl extends ACacheManage<OrderDto> implements IOrderCacheManage {
	public OrderCacheManageImpl(RedisTemplate<String, OrderDto> template, RedissionClientUtil redissonClient) {
		super(template, redissonClient);
	}

	@Override
	public Optional<OrderDto> get(String key) {
		return super.get(getKeyOrderId(key));
	}

	@Override
	public Optional<OrderDto> cache(String keyLock, String keyCache, CallbackDB<OrderDto> callDB) {
		return super.cache(getKeyOrderId(keyLock), getKeyOrderId(keyCache), callDB, 60, TimeUnit.MINUTES);
	}

	@Override
	public void save(String key, OrderDto data) {
		super.save(getKeyOrderId(key), data, 60, TimeUnit.MINUTES);
	}

	@Override
	public void delete(String key) {
		super.delete(getKeyOrderId(key));
	}

	@Override
	public Optional<OrderDto> get(long orderId) {
		return get(String.valueOf(orderId));
	}

	@Override
	public Optional<OrderDto> cache(long orderId, CallbackDB<OrderDto> callDB) {
		return cache(String.valueOf(orderId), String.valueOf(orderId), callDB);
	}

	@Override
	public void save(long orderId, OrderDto data) {
		save(String.valueOf(orderId), data);
	}

	@Override
	public void delete(long orderId) {
		delete(String.valueOf(orderId));
	}

	private String getKeyOrderId(String orderId) {
		return generateHashKey("ORDER", orderId);
	}
}
