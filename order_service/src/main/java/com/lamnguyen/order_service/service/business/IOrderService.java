/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 4:42 PM-02/05/2025
 * User: kimin
 **/

package com.lamnguyen.order_service.service.business;

import com.lamnguyen.order_service.domain.request.CreateOrderRequest;
import com.lamnguyen.order_service.domain.response.OrderDetailResponse;
import com.lamnguyen.order_service.domain.response.SubOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IOrderService {
	OrderDetailResponse createOrder(CreateOrderRequest order);

	OrderDetailResponse createOrder(long userId, CreateOrderRequest order);

	void cancelOrder(long orderId);

	void deleteOrder(long orderId);

	Page<SubOrder> getSubOrder(Pageable pageable);

	Page<SubOrder> getSubOrderAllUser(Pageable pageable);

	OrderDetailResponse getOrderDetail(long orderId);

	Page<SubOrder> getSubOrder(long userId, Pageable pageable);

	OrderDetailResponse getOrderDetailAdmin(long orderId);

	void lockOrder(long orderId, boolean lock);

	void softDeleteOrder(long orderId, boolean delete);
}
