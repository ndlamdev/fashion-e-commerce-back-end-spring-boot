/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 5:00 AM-19/05/2025
 * User: kimin
 **/

package com.lamnguyen.order_service.controller.user;

import com.lamnguyen.order_service.domain.request.CreateOrderRequest;
import com.lamnguyen.order_service.domain.request.OrderIdRequest;
import com.lamnguyen.order_service.domain.response.CreateOrderSuccessResponse;
import com.lamnguyen.order_service.domain.response.SubOrder;
import com.lamnguyen.order_service.service.business.IOrderService;
import com.lamnguyen.order_service.utils.annotation.ApiMessageResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping("/order/v1")
public class OrderController {
	IOrderService orderService;

	@PostMapping()
	@ApiMessageResponse("create order success")
	@PreAuthorize("hasAnyAuthority('ROLE_BASE', 'ROLE_ADMIN', 'USER_CREATE_ORDER')")
	public CreateOrderSuccessResponse createOrder(@RequestBody @Valid CreateOrderRequest order, HttpServletRequest request) {
		return orderService.createOrder(order);
	}

	@PostMapping("/cancel")
	@ApiMessageResponse("Cancel order success")
	@PreAuthorize("hasAnyAuthority('ROLE_BASE', 'ROLE_ADMIN', 'USER_CANCEL_ORDER')")
	public void cancelOrder(@RequestBody OrderIdRequest orderIdRequest) {
		orderService.cancelOrder(orderIdRequest.orderId());
	}

	@GetMapping("/history")
	@ApiMessageResponse("Get order history success")
	@PreAuthorize("hasAnyAuthority('ROLE_BASE', 'ROLE_ADMIN', 'USER_GET_HISTORY_ORDER')")
	public Page<SubOrder> getOrderHistory(@PageableDefault(size = 10) Pageable pageable) {
		return orderService.getSubOrder(pageable);
	}
}
