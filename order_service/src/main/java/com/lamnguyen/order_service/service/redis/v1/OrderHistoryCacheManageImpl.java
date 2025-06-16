/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 4:10 PM-28/05/2025
 * User: kimin
 **/

package com.lamnguyen.order_service.service.redis.v1;

import com.lamnguyen.order_service.domain.response.SubOrder;
import com.lamnguyen.order_service.service.redis.ACacheManage;
import com.lamnguyen.order_service.service.redis.CallbackDB;
import com.lamnguyen.order_service.service.redis.IOrderHistoryCacheManage;
import com.lamnguyen.order_service.utils.helper.RedissionClientUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class OrderHistoryCacheManageImpl extends ACacheManage<SubOrder> implements IOrderHistoryCacheManage {

	public OrderHistoryCacheManageImpl(RedisTemplate<String, SubOrder> template, RedissionClientUtil redissonClient) {
		super(template, redissonClient);
	}

	@Override
	public Optional<SubOrder> get(String key) {
		return super.get(getKeySubOrderId(key));
	}

	@Override
	public Optional<SubOrder> cache(String keyLock, String keyCache, CallbackDB<SubOrder> callDB) {
		return super.cache(getKeySubOrderId(keyLock), getKeySubOrderId(keyCache), callDB, 60, TimeUnit.MINUTES);
	}

	@Override
	public void delete(String key) {
		super.delete(getKeySubOrderId(key));
	}

	@Override
	public void save(String key, SubOrder data) {
		super.save(getKeySubOrderId(key), data, 60, TimeUnit.MINUTES);
	}

	private String getKeySubOrderId(String orderId) {
		return generateHashKey("ORDER_HISTORY_ID", orderId);
	}

	private String getKeyListSubOrder(long userId) {
		return generateHashKey("ORDER_HISTORY", generateKey("USER_ID", String.valueOf(userId)));
	}

	@Override
	public Optional<List<SubOrder>> getAllByUserId(long userId) {
		var values = this.template.opsForList().range(getKeyListSubOrder(userId), 0, -1);
		if (values == null || values.isEmpty()) return Optional.empty();
		return Optional.of(values);
	}

	@Override
	public Optional<List<SubOrder>> cacheAllByUserId(long userId, CallbackDB<List<SubOrder>> callDB) {
		var key = getKeyListSubOrder(userId);
		return this.redissonClient.synchronize(key, () -> {
			var cached = getAllByUserId(userId);
			if (cached.isPresent()) {
				return cached;
			}

			var dto = callDB.call();
			if (dto.isEmpty() || dto.get().isEmpty()) return Optional.empty();
			this.template.opsForList().leftPushAll(key, dto.get());
			this.template.expire(key, 60, TimeUnit.MINUTES);
			return dto;
		}, () -> getAllByUserId(userId));
	}

	@Override
	public void deleteAllByUserId(long userId) {
		this.template.delete(getKeyListSubOrder(userId));
	}
}
