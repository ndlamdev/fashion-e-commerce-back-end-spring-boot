/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 5:00 AM-19/05/2025
 * User: kimin
 **/

package com.lamnguyen.order_service.controller.admin;

import com.lamnguyen.order_service.domain.request.CreateOrderRequest;
import com.lamnguyen.order_service.domain.request.OrderIdRequest;
import com.lamnguyen.order_service.domain.response.OrderDetailResponse;
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
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping("/admin/order/v1")
public class AdminOrderController {
	IOrderService orderService;

	@PostMapping("/cancel")
	@ApiMessageResponse("Cancel order success")
	@PreAuthorize("hasAnyAuthority('ROLE_BASE', 'ROLE_ADMIN', 'ADMIN_CANCEL_ORDER')")
	public void cancelOrder(@RequestBody OrderIdRequest orderIdRequest) {
		orderService.cancelOrder(orderIdRequest.orderId());
	}

	@GetMapping("/user/{user-id}/history")
	@ApiMessageResponse("Get order history success")
	@PreAuthorize("hasAnyAuthority('ROLE_BASE', 'ROLE_ADMIN', 'ADMIN_GET_HISTORY_ORDER')")
	public Page<SubOrder> getOrderHistory(@PathVariable("user-id") long userId, @PageableDefault(size = 10) Pageable pageable) {
		return orderService.getSubOrder(userId, pageable);
	}

	@GetMapping("/order-detail/{id}")
	@ApiMessageResponse("Get order history success")
	@PreAuthorize("hasAnyAuthority('ROLE_BASE', 'ROLE_ADMIN', 'ADMIN_GET_ORDER_DETAIL')")
	public OrderDetailResponse getOrderDetail(@PathVariable long id) {
		return orderService.getOrderDetailAdmin(id);
	}

	@DeleteMapping("/{id}")
	@ApiMessageResponse("Delete order success")
	@PreAuthorize("hasAnyAuthority('ROLE_BASE', 'ROLE_ADMIN', 'ADMIN_DELETE_ORDER')")
	public void deleteOrder(@PathVariable long id, @RequestParam boolean delete) {
		orderService.softDeleteOrder(id, delete);
	}

	@PutMapping("/lock/{id}")
	@ApiMessageResponse("Delete order success")
	@PreAuthorize("hasAnyAuthority('ROLE_BASE', 'ROLE_ADMIN', 'ADMIN_DELETE_ORDER')")
	public void lockOrder(@PathVariable long id, @RequestParam boolean lock) {
		orderService.lockOrder(id, lock);
	}
}
