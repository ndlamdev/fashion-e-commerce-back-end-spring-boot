/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 4:57 PM-28/05/2025
 * User: kimin
 **/

package com.lamnguyen.order_service.service.redis;

import com.lamnguyen.order_service.domain.dto.OrderDto;

import java.util.Optional;

public interface IOrderCacheManage extends ICacheManage<OrderDto> {

	Optional<OrderDto> cache(long orderId, CallbackDB<OrderDto> callDB);

	void save(long orderId, OrderDto data);

	void delete(long orderId);

	Optional<OrderDto> get(long orderId);
}
