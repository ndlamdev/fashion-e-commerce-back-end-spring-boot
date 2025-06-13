/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 5:00 AM-19/05/2025
 * User: kimin
 **/

package com.lamnguyen.order_service.controller.admin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lamnguyen.order_service.domain.request.OrderIdRequest;
import com.lamnguyen.order_service.domain.response.OrderDetailResponse;
import com.lamnguyen.order_service.domain.response.SubOrder;
import com.lamnguyen.order_service.service.business.IOrderService;
import com.lamnguyen.order_service.utils.annotation.ApiMessageResponse;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping("/admin/order/v1")
public class AdminOrderController {
	IOrderService orderService;

	@PostMapping("/cancel")
	@ApiMessageResponse("Cancel order success")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ADMIN_CANCEL_ORDER')")
	public void cancelOrder(@RequestBody OrderIdRequest orderIdRequest) {
		orderService.cancelOrder(orderIdRequest.orderId());
	}

	@GetMapping("/user/{user-id}/history")
	@ApiMessageResponse("Get order history success")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ADMIN_GET_HISTORY_ORDER')")
	public Page<SubOrder> getOrderHistoryByUserId(@PathVariable("user-id") long userId,
			@PageableDefault(size = 10) Pageable pageable) {
		return orderService.getSubOrder(userId, pageable);
	}

	@GetMapping()
	@ApiMessageResponse("Get order history success")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ADMIN_GET_ORDER')")
	public Page<SubOrder> getOrders(@PageableDefault(size = 10) Pageable pageable) {
		return orderService.getSubOrder(pageable);
	}

	@GetMapping("/order-detail/{id}")
	@ApiMessageResponse("Get order history success")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ADMIN_GET_ORDER_DETAIL')")
	public OrderDetailResponse getOrderDetail(@PathVariable long id) {
		return orderService.getOrderDetailAdmin(id);
	}

	@DeleteMapping("/{id}")
	@ApiMessageResponse("Delete order success")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ADMIN_DELETE_ORDER')")
	public void deleteOrder(@PathVariable long id, @RequestParam boolean delete) {
		orderService.softDeleteOrder(id, delete);
	}

	@PutMapping("/lock/{id}")
	@ApiMessageResponse("Delete order success")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ADMIN_DELETE_ORDER')")
	public void lockOrder(@PathVariable long id, @RequestParam boolean lock) {
		orderService.lockOrder(id, lock);
	}
}
