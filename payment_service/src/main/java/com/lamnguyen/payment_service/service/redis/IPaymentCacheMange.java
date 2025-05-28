/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 5:51 PM-28/05/2025
 * User: kimin
 **/

package com.lamnguyen.payment_service.service.redis;

import com.lamnguyen.payment_service.model.Payment;

import java.util.Optional;

public interface IPaymentCacheMange extends ICacheManage<Payment> {
	Optional<Payment> cacheByOrderId(long orderId, CallbackDB<Payment> callDB);

	void deleteByOrderId(long orderId);

	Optional<Payment> getByOrderId(long orderId);
}
