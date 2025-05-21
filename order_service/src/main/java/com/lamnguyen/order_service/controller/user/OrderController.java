/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 5:00 AM-19/05/2025
 * User: kimin
 **/

package com.lamnguyen.order_service.controller.user;

import com.lamnguyen.order_service.domain.request.CreateOrderRequest;
import com.lamnguyen.order_service.domain.response.CreateOrderSuccessResponse;
import com.lamnguyen.order_service.service.business.IOrderService;
import com.lamnguyen.order_service.utils.annotation.ApiMessageResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping("/order/v1")
public class OrderController {
	IOrderService orderService;

	@PostMapping()
	@ApiMessageResponse("create order success")
	@PreAuthorize("hasAnyAuthority('ROLE_BASE', 'ROLE_ADMIN', 'CREATE_ORDER')")
	public CreateOrderSuccessResponse createOrder(@RequestBody @Valid CreateOrderRequest order, HttpServletRequest request) {
		return orderService.createOrder(order, baseUrl(request));
	}

	@GetMapping("/cancel")
	@ApiMessageResponse("Cancel order success")
	public void cancelOrder(@RequestParam("order-id") long orderCode) {
		orderService.cancelOrder(orderCode);
	}

	@GetMapping("/pay-success")
	@ApiMessageResponse("Pay order success")
	public void payOrderSuccess(@RequestParam("order-id") long orderCode) {
		orderService.paySuccess(orderCode);
	}

	private String baseUrl(HttpServletRequest request) {
		return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	}
}
