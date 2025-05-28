/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 4:09 PM-28/05/2025
 * User: kimin
 **/

package com.lamnguyen.order_service.service.redis;

import com.lamnguyen.order_service.domain.response.SubOrder;

import java.util.List;
import java.util.Optional;

public interface IOrderHistoryCacheManage extends ICacheManage<SubOrder> {
	Optional<List<SubOrder>> getAllByUserId(long userId);

	Optional<List<SubOrder>> cacheAllByUserId(long userId, CallbackDB<List<SubOrder>> callDB);

	void deleteAllByUserId(long userId);
}
