/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 4:41 PM-02/05/2025
 * User: kimin
 **/

package com.lamnguyen.order_service.service.redis.v1;

import com.lamnguyen.order_service.service.redis.ACacheManage;
import com.lamnguyen.order_service.service.redis.CallbackDB;
import com.lamnguyen.order_service.service.redis.IBaseRedisManage;
import com.lamnguyen.order_service.utils.helper.RedissionClientUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BaseRedisManageImpl extends ACacheManage<Object> implements IBaseRedisManage {
	public BaseRedisManageImpl(RedisTemplate<String, Object> template, RedissionClientUtil redissonClient) {
		super(template, redissonClient);
	}

	@Override
	public Optional<Object> cache(String keyLock, String keyCache, CallbackDB<Object> callDB) {
		return Optional.empty();
	}

	@Override
	public void delete(String key) {

	}

	@Override
	public void save(String key, Object data) {

	}
}
