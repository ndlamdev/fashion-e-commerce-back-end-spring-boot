/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 4:42 PM-02/05/2025
 * User: kimin
 **/

package com.lamnguyen.order_service.service.business;

import com.lamnguyen.order_service.domain.request.CreateOrderRequest;
import com.lamnguyen.order_service.domain.response.CreateOrderSuccessResponse;
import com.lamnguyen.order_service.domain.response.SubOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IOrderService {
	CreateOrderSuccessResponse createOrder(CreateOrderRequest order);

	void cancelOrder(long orderId);

	void deleteOrder(long orderId);

	Page<SubOrder> getSubOrder(Pageable pageable);
}
