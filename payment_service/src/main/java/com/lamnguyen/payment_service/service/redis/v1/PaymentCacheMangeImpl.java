/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 5:52 PM-28/05/2025
 * User: kimin
 **/

package com.lamnguyen.payment_service.service.redis.v1;

import com.lamnguyen.payment_service.model.Payment;
import com.lamnguyen.payment_service.service.redis.ACacheManage;
import com.lamnguyen.payment_service.service.redis.CallbackDB;
import com.lamnguyen.payment_service.service.redis.IPaymentCacheMange;
import com.lamnguyen.payment_service.utils.helper.RedissionClientUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class PaymentCacheMangeImpl extends ACacheManage<Payment> implements IPaymentCacheMange {
	public PaymentCacheMangeImpl(RedisTemplate<String, Payment> template, RedissionClientUtil redissonClient) {
		super(template, redissonClient);
	}

	@Override
	public Optional<Payment> cache(String keyLock, String keyCache, CallbackDB<Payment> callDB) {
		return super.cache(generatePaymentKey(keyLock), generatePaymentKey(keyCache), callDB, 60, TimeUnit.MINUTES);
	}

	@Override
	public void save(String key, Payment data) {
		super.save(generatePaymentKey(key), data, 60, TimeUnit.MINUTES);
	}

	@Override
	public void delete(String key) {
		super.delete(generatePaymentKey(key));
	}

	@Override
	public Optional<Payment> get(String key) {
		return super.get(generatePaymentKey(key));
	}

	private String generatePaymentKey(String orderId) {
		return generateHashKey("PAYMENT", generateKey("ORDER_ID", orderId));
	}

	@Override
	public Optional<Payment> cacheByOrderId(long orderId, CallbackDB<Payment> callDB) {
		return cache(String.valueOf(orderId), String.valueOf(orderId), callDB);
	}

	@Override
	public void deleteByOrderId(long orderId) {
		delete(String.valueOf(orderId));
	}

	@Override
	public Optional<Payment> getByOrderId(long orderId) {
		return get(String.valueOf(orderId));
	}
}
