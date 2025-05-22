/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 2:41 PM-18/05/2025
 * User: kimin
 **/

package com.lamnguyen.order_service.mapper;

import com.google.protobuf.StringValue;
import com.lamnguyen.order_service.domain.request.CreateOrderRequest;
import com.lamnguyen.order_service.domain.response.CreateOrderSuccessResponse;
import com.lamnguyen.order_service.domain.response.OrderResponse;
import com.lamnguyen.order_service.model.OrderEntity;
import com.lamnguyen.order_service.model.OrderItemEntity;
import com.lamnguyen.order_service.model.OrderStatusEntity;
import com.lamnguyen.order_service.protos.OrderItemRequest;
import com.lamnguyen.order_service.protos.PaymentRequest;
import com.lamnguyen.order_service.protos.PaymentResponse;
import com.lamnguyen.order_service.utils.enums.OrderStatus;
import com.lamnguyen.order_service.utils.enums.PaymentMethod;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {IOrderItemMapper.class, IOrderItemMapper.class, IGrpcMapper.class})
public interface IOrderMapper {
	@Mapping(source = "order.address", target = "addressDetail")
	OrderEntity toEntity(CreateOrderRequest order, long customerId, List<OrderItemEntity> items);

	@Mapping(target = "orderId", source = "order.id")
	@Mapping(target = "name", source = "order.name")
	@Mapping(target = "email", source = "order.email")
	@Mapping(target = "phone", source = "order.phone")
	@Mapping(target = "address", source = "order.addressDetail")
	@Mapping(target = "note", source = "order.note")
	PaymentRequest toPaymentRequest(OrderEntity order, PaymentMethod method, List<OrderItemRequest> items, String baseUrl);

	@AfterMapping
	default void afterMapping(@MappingTarget OrderEntity orderEntity, long customerId, List<OrderItemEntity> items) {
		orderEntity.setStatuses(List.of(OrderStatusEntity.builder()
				.orders(orderEntity)
				.status(OrderStatus.PENDING)
				.note("Đang xử lý")
				.build()));
		orderEntity.setItems(items);
		orderEntity.setCustomerId(customerId);
	}

	@AfterMapping
	default void afterMapping(@MappingTarget PaymentRequest.Builder builder, OrderEntity order, List<OrderItemRequest> items, String baseUrl) {
		builder.addAllItems(items);
		builder.setCancelUrl(baseUrl + "/order/v1/cancel?order-id=" + order.getId());
		builder.setReturnUrl(baseUrl + "/order/v1/pay-success?order-id=" + order.getId());
	}

	OrderResponse toResponse(OrderEntity entity);

	@Mapping(target = "id", source = "entity.id")
	@Mapping(target = "paymentResponse", ignore = true)
	CreateOrderSuccessResponse toCreateOrderSuccessResponse(OrderEntity entity, com.lamnguyen.order_service.protos.PaymentResponse paymentResponse, String returnUrl);

	@AfterMapping
	default void afterMapping(@MappingTarget CreateOrderSuccessResponse response, com.lamnguyen.order_service.protos.PaymentResponse paymentResponse, String returnUrl) {
		if (paymentResponse != null) {
			var payment = com.lamnguyen.order_service.domain.response.PaymentResponse.builder()
					.method(PaymentMethod.valueOf(paymentResponse.getMethod().name()))
					.returnUrl(returnUrl)
					.checkoutUrl(paymentResponse.getCheckoutUrl().getValue())
					.build();
			response.setPaymentResponse(payment);
		}
	}
}
