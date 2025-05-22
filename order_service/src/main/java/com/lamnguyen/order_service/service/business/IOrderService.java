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
import com.lamnguyen.order_service.domain.response.OrderResponse;

public interface IOrderService {
    CreateOrderSuccessResponse createOrder(CreateOrderRequest order, String baseUrl);

    void paySuccess(long orderId, long orderOrderCode);

    void cancelOrder(long orderId, long orderOrderCode);
}
