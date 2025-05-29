/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 4:43 PM-02/05/2025
 * User: kimin
 **/

package com.lamnguyen.order_service.service.business.v1;

import com.lamnguyen.order_service.config.exception.ApplicationException;
import com.lamnguyen.order_service.config.exception.ExceptionEnum;
import com.lamnguyen.order_service.domain.dto.OrderDto;
import com.lamnguyen.order_service.domain.request.CreateOrderItemRequest;
import com.lamnguyen.order_service.domain.request.CreateOrderRequest;
import com.lamnguyen.order_service.domain.response.OrderDetailResponse;
import com.lamnguyen.order_service.domain.response.SubOrder;
import com.lamnguyen.order_service.event.DeleteCartItemsEvent;
import com.lamnguyen.order_service.mapper.IOrderItemMapper;
import com.lamnguyen.order_service.mapper.IOrderMapper;
import com.lamnguyen.order_service.model.OrderEntity;
import com.lamnguyen.order_service.model.OrderItemEntity;
import com.lamnguyen.order_service.protos.OrderItemRequest;
import com.lamnguyen.order_service.protos.PayStatus;
import com.lamnguyen.order_service.protos.VariantProductInfo;
import com.lamnguyen.order_service.repository.IOrderRepository;
import com.lamnguyen.order_service.service.business.IOrderItemService;
import com.lamnguyen.order_service.service.business.IOrderService;
import com.lamnguyen.order_service.service.business.IOrderStatusService;
import com.lamnguyen.order_service.service.grpc.IInventoryGrpcClient;
import com.lamnguyen.order_service.service.grpc.IPaymentGrpcClient;
import com.lamnguyen.order_service.service.grpc.IProductGrpcClient;
import com.lamnguyen.order_service.service.kafka.producer.ICartKafkaService;
import com.lamnguyen.order_service.service.redis.IOrderCacheManage;
import com.lamnguyen.order_service.service.redis.IOrderHistoryCacheManage;
import com.lamnguyen.order_service.utils.enums.OrderStatus;
import com.lamnguyen.order_service.utils.helper.JwtTokenUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderServiceImpl implements IOrderService {
	IOrderMapper orderMapper;
	IOrderItemMapper orderItemMapper;
	IOrderRepository orderRepository;
	IProductGrpcClient productGrpcClient;
	IInventoryGrpcClient inventoryGrpcClient;
	IPaymentGrpcClient paymentGrpcClient;
	IOrderStatusService orderStatusService;
	JwtTokenUtil jwtTokenUtil;
	ICartKafkaService cartKafkaService;
	IOrderItemService orderItemService;
	IOrderHistoryCacheManage historyCacheManage;
	IOrderCacheManage orderCacheManage;

	@Override
	public OrderDetailResponse createOrder(CreateOrderRequest order) {
		var userId = jwtTokenUtil.getUserId();
		return createOrder(userId, order);
	}

	@Override
	public OrderDetailResponse createOrder(long userId, CreateOrderRequest order) {
		Map<String, VariantProductInfo> variants = null;
		OrderEntity entity = null;
		var mapQuantities = order.getItems().stream().collect(Collectors.toMap(CreateOrderItemRequest::getVariantId, CreateOrderItemRequest::getQuantity));
		try {
			var mapUpdateVariant = order.getItems().stream().collect(Collectors.toMap(CreateOrderItemRequest::getVariantId, it -> -it.getQuantity()));
			variants = inventoryGrpcClient.updateQuantityByVariantIds(mapUpdateVariant);
			if (variants.size() != mapUpdateVariant.size())
				throw ApplicationException.createException(ExceptionEnum.NOT_FAIL_VARIANT);

			var listOrderItemRequest = new ArrayList<OrderItemRequest>(variants.size());
			var orderItems = new ArrayList<OrderItemEntity>(variants.size());
			createOrderHelper(variants, mapQuantities, listOrderItemRequest, orderItems);
			entity = orderRepository.save(orderMapper.toEntity(order, userId, orderItems));
			var paymentRequest = orderMapper.toPaymentRequest(entity, order, listOrderItemRequest);
			var paymentResponse = paymentGrpcClient.pay(paymentRequest);
			if (paymentResponse.getStatus() == PayStatus.FAIL)
				throw ApplicationException.createException(ExceptionEnum.PAY_FAIL);

			orderStatusService.addStatus(entity.getId(), OrderStatus.SUCCESS, "Đặt hàng thành công.");
			cartKafkaService.deleteCartItems(DeleteCartItemsEvent.builder()
					.userId(userId)
					.variantIds(mapQuantities.keySet().stream().toList())
					.build());
			historyCacheManage.deleteAllByUserId(userId);
			return orderMapper.toOrderDetailResponse(entity, paymentResponse);
		} catch (Exception e) {
			if (variants != null)
				rollback(variants, order.getItems());
			if (entity != null)
				orderStatusService.addStatus(entity.getId(), OrderStatus.CANCEL, "Lỗi thanh toán");
			throw new RuntimeException(e);
		}
	}

	private void createOrderHelper(Map<String, VariantProductInfo> variants,
	                               Map<String, Integer> mapQuantities,
	                               List<OrderItemRequest> listOrderItemRequest,
	                               List<OrderItemEntity> orderItems) {
		var listTask = new ArrayList<CompletableFuture<Void>>();
		variants.values().forEach(
				variant ->
						listTask.add(CompletableFuture.runAsync(() -> {
							var product = productGrpcClient.getTitleProduct(variant.getProductId());
							listOrderItemRequest.add(orderItemMapper.toItemData(mapQuantities.getOrDefault(variant.getId(), 1), variant, product));
							orderItems.add(orderItemMapper.toOrderItemEntity(variant, mapQuantities.getOrDefault(variant.getId(), 1)));
						}))
		);
		CompletableFuture.allOf(listTask.toArray(CompletableFuture[]::new)).join();
	}

	private void rollback(Map<String, VariantProductInfo> variants, List<CreateOrderItemRequest> items) {
		var mapUpdateVariant = items.stream().filter(it -> variants.containsKey(it.getVariantId())).collect(Collectors.toMap(CreateOrderItemRequest::getVariantId, CreateOrderItemRequest::getQuantity));
		inventoryGrpcClient.updateQuantityByVariantIds(mapUpdateVariant);
	}

	@Override
	public void cancelOrder(long orderId) {
		var order = orderRepository.findById(orderId).orElseThrow(() -> ApplicationException.createException(ExceptionEnum.NOT_FOUND));
		paymentGrpcClient.cancelPay(orderId);
		orderStatusService.addStatus(order.getId(), OrderStatus.CANCEL, "Hủy đơn hàng");
		historyCacheManage.deleteAllByUserId(order.getUserId());
		orderCacheManage.delete(orderId);
	}

	@Override
	public void deleteOrder(long orderId) {
		var order = orderRepository.findById(orderId).orElseThrow(() -> ApplicationException.createException(ExceptionEnum.NOT_FOUND));
		orderItemService.deleteAllByOrderId(orderId);
		orderStatusService.deleteAllByOrderId(orderId);
		orderRepository.deleteById(orderId);
		historyCacheManage.deleteAllByUserId(order.getUserId());
		orderCacheManage.delete(orderId);
	}

	@Override
	public Page<SubOrder> getSubOrder(Pageable pageable) {
		var userId = jwtTokenUtil.getUserId();
		var subOrders = historyCacheManage.getAllByUserId(userId)
				.or(() -> cacheHistoryOrder(userId))
				.orElseGet(ArrayList::new);
		return new PageImpl<>(
				subOrders.stream().skip(pageable.getOffset()).limit(pageable.getPageSize()).toList(),
				pageable, subOrders.size()
		);
	}

	private Optional<List<SubOrder>> cacheHistoryOrder(long userId) {
		return historyCacheManage
				.cacheAllByUserId(
						userId,
						() -> Optional
								.ofNullable(
										orderRepository.findHistoryOrderByUserIdAndLockIsFalseAndDeleteIsFalse(userId)
								))
				;
	}

	@Override
	public OrderDetailResponse getOrderDetail(long orderId) {
		var orderDto = orderCacheManage.get(orderId)
				.or(() -> cacheOrderDetail(orderId))
				.orElseThrow(() -> ApplicationException.createException(ExceptionEnum.NOT_FOUND));
		var paymentStatus = paymentGrpcClient.getPaymentStatus(orderId);
		return orderMapper.toOrderDetailResponse(orderDto, paymentStatus);
	}

	private Optional<? extends OrderDto> cacheOrderDetail(long orderId) {
		return orderCacheManage
				.cache(orderId,
						() -> orderRepository
								.findByIdAndDeleteIsFalseAndLockIsFalse(orderId)
								.map(orderMapper::toDto)
				);
	}

	@Override
	public Page<SubOrder> getSubOrder(long userId, Pageable pageable) {
		var subOrders = historyCacheManage.getAllByUserId(userId)
				.or(() -> cacheHistoryOrderAdmin(userId))
				.orElseGet(ArrayList::new);
		return new PageImpl<>(
				subOrders.stream().skip(pageable.getOffset()).limit(pageable.getPageSize()).toList(),
				pageable, subOrders.size()
		);
	}

	private Optional<List<SubOrder>> cacheHistoryOrderAdmin(long userId) {
		return historyCacheManage
				.cacheAllByUserId(
						userId,
						() -> Optional
								.ofNullable(
										orderRepository.findHistoryOrderByUserIdAndDeleteIsFalse(userId)
								))
				;
	}

	@Override
	public OrderDetailResponse getOrderDetailAdmin(long orderId) {
		var orderDto = orderCacheManage.get(orderId)
				.or(() -> cacheOrderDetailAdmin(orderId))
				.orElseThrow(() -> ApplicationException.createException(ExceptionEnum.NOT_FOUND));
		var paymentStatus = paymentGrpcClient.getPaymentStatus(orderId);
		return orderMapper.toOrderDetailResponse(orderDto, paymentStatus);
	}

	private Optional<? extends OrderDto> cacheOrderDetailAdmin(long orderId) {
		return orderCacheManage
				.cache(orderId,
						() -> orderRepository
								.findByIdAndDeleteIsFalse(orderId)
								.map(orderMapper::toDto)
				);
	}

	@Override
	public void lockOrder(long orderId, boolean lock) {
		var order = orderRepository.findById(orderId).orElseThrow(() -> ApplicationException.createException(ExceptionEnum.NOT_FOUND));
		order.setLock(lock);
		historyCacheManage.deleteAllByUserId(order.getUserId());
		orderCacheManage.delete(orderId);
	}

	@Override
	public void softDeleteOrder(long orderId, boolean delete) {
		var order = orderRepository.findById(orderId).orElseThrow(() -> ApplicationException.createException(ExceptionEnum.NOT_FOUND));
		order.setDelete(delete);
		historyCacheManage.deleteAllByUserId(order.getUserId());
		orderCacheManage.delete(orderId);
	}
}
