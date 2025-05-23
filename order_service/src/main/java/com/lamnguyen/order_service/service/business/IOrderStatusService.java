/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:04 AM-19/05/2025
 * User: kimin
 **/

package com.lamnguyen.order_service.service.business;

import com.lamnguyen.order_service.model.OrderStatusEntity;
import com.lamnguyen.order_service.utils.enums.OrderStatus;

public interface IOrderStatusService {
	OrderStatusEntity addStatus(long orderId,
	                            OrderStatus status,
	                            String note);

	void deleteAllByOrderId(long orderId);
}
