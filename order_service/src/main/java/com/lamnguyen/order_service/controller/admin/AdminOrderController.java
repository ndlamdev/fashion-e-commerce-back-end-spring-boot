/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 5:00 AM-19/05/2025
 * User: kimin
 **/

package com.lamnguyen.order_service.controller.admin;

import com.lamnguyen.order_service.domain.request.AddOrderStausRequest;
import com.lamnguyen.order_service.domain.request.OrderIdRequest;
import com.lamnguyen.order_service.domain.response.OrderDetailResponse;
import com.lamnguyen.order_service.domain.response.SubOrder;
import com.lamnguyen.order_service.service.business.IOrderService;
import com.lamnguyen.order_service.service.business.IOrderStatusService;
import com.lamnguyen.order_service.utils.annotation.ApiMessageResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping("/admin/order/v1")
public class AdminOrderController {
	IOrderService orderService;
	IOrderStatusService orderStatusService;

	@PostMapping("/cancel")
	@ApiMessageResponse("Cancel order success")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ADMIN_CANCEL_ORDER')")
	public void cancelOrder(@RequestBody OrderIdRequest orderIdRequest) {
		orderService.cancelOrder(orderIdRequest.orderId());
	}

	@GetMapping("/user/{user-id}/history")
	@ApiMessageResponse("Get order history success")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ADMIN_GET_HISTORY_ORDER')")
	public List<SubOrder> getOrderHistoryByUserId(@PathVariable("user-id") long userId) {
		return orderService.getSubOrder(userId);
	}

	@GetMapping()
	@ApiMessageResponse("Get order history success")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ADMIN_GET_ORDER')")
	public List<SubOrder> getOrders() {
		return orderService.getSubOrderAllUser();
	}

	@GetMapping("/abandoned-checkout")
	@ApiMessageResponse("Get order history success")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ADMIN_GET_HISTORY_ORDER_ABANDONED_CHECKOUT')")
	public List<SubOrder> getOrderHistoryAbandonedCheckout() {
		return orderService.getSubOrderAbandonedCheckoutAllUser();
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

	@PostMapping("/{id}")
	@ApiMessageResponse("Update order status success")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ADMIN_DELETE_ORDER')")
	public void updateStatus(@PathVariable long id, @RequestBody @Valid AddOrderStausRequest request) {
		orderStatusService.addStatus(id, request.status(), request.note());
	}

	@DeleteMapping("/{id}/order-status/{order-status-id}")
	@ApiMessageResponse("Delete order success")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ADMIN_DELETE_ORDER')")
	public void deleteOrderStatus(@PathVariable("id") long orderId, @PathVariable("order-status-id") long orderStatusId) {
		orderStatusService.deleteOrderStatusByOrderIdAndId(orderId, orderStatusId);
	}
}
