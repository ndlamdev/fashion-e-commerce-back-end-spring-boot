/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 2:41 PM-18/05/2025
 * User: kimin
 **/

package com.lamnguyen.order_service.mapper;

import com.lamnguyen.order_service.domain.dto.GeneralInfoDto;
import com.lamnguyen.order_service.domain.request.CreateOrderRequest;
import com.lamnguyen.order_service.domain.response.OrderDetailResponse;
import com.lamnguyen.order_service.domain.dto.OrderDto;
import com.lamnguyen.order_service.domain.response.OrderItemResponse;
import com.lamnguyen.order_service.model.OrderEntity;
import com.lamnguyen.order_service.model.OrderItemEntity;
import com.lamnguyen.order_service.model.OrderStatusEntity;
import com.lamnguyen.order_service.protos.GeneralInfo;
import com.lamnguyen.order_service.protos.GeneralInfoOrBuilder;
import com.lamnguyen.order_service.protos.OrderItemRequest;
import com.lamnguyen.order_service.protos.PaymentRequest;
import com.lamnguyen.order_service.service.grpc.v1.OrderGrpcServerImpl;
import com.lamnguyen.order_service.utils.enums.OrderStatus;
import com.lamnguyen.order_service.utils.enums.PaymentMethod;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", uses = {IOrderItemMapper.class, IOrderItemMapper.class, IGrpcMapper.class})
public interface IOrderMapper {
	@Mapping(source = "order.address", target = "addressDetail")
	OrderEntity toEntity(CreateOrderRequest order, long userId, List<OrderItemEntity> items);

	@Mapping(target = "orderId", source = "order.id")
	@Mapping(target = "name", source = "order.name")
	@Mapping(target = "email", source = "order.email")
	@Mapping(target = "phone", source = "order.phone")
	@Mapping(target = "address", source = "order.addressDetail")
	@Mapping(target = "note", source = "order.note")
	@Mapping(target = "returnUrl", source = "orderRequest.payment.returnUrl")
	@Mapping(target = "cancelUrl", source = "orderRequest.payment.cancelUrl")
	@Mapping(target = "method", source = "orderRequest.payment.method")
	PaymentRequest toPaymentRequest(OrderEntity order, CreateOrderRequest orderRequest, List<OrderItemRequest> items);

	@AfterMapping
	default void afterMapping(@MappingTarget OrderEntity orderEntity, long userId, List<OrderItemEntity> items) {
		orderEntity.setStatuses(List.of(OrderStatusEntity.builder()
				.order(orderEntity)
				.status(OrderStatus.PENDING)
				.note("Đơn hàng đang chờ xử lý")
				.build()));
		items.forEach(it -> it.setOrder(orderEntity));
		orderEntity.setItems(items);
		orderEntity.setUserId(userId);
	}

	@AfterMapping
	default void afterMapping(@MappingTarget PaymentRequest.Builder builder, List<OrderItemRequest> items) {
		builder.addAllItems(items);
	}

	OrderDto toDto(OrderEntity entity);

	@Mapping(target = "id", source = "entity.id")
	@Mapping(target = "paymentResponse", ignore = true)
	@Mapping(target = "itemDetails", source = "entity.items")
	OrderDetailResponse toOrderDetailResponse(OrderEntity entity, com.lamnguyen.order_service.protos.PaymentResponse paymentResponse);

	@Mapping(target = "id", source = "dto.id")
	@Mapping(target = "paymentResponse", ignore = true)
	@Mapping(target = "itemDetails", source = "dto.items")
	OrderDetailResponse toOrderDetailResponse(OrderDto dto, com.lamnguyen.order_service.protos.PaymentResponse paymentResponse);

	@AfterMapping
	default void afterMapping(@MappingTarget OrderDetailResponse response, com.lamnguyen.order_service.protos.PaymentResponse paymentResponse) {
		if (paymentResponse != null) {
			var checkoutUrl = paymentResponse.getCheckoutUrl().getValue();
			var payment = com.lamnguyen.order_service.domain.response.PaymentResponse.builder()
					.orderCode(paymentResponse.getOrderCode().getValue())
					.status(paymentResponse.getStatus().name())
					.method(PaymentMethod.valueOf(paymentResponse.getMethod().name()))
					.checkoutUrl(checkoutUrl.isBlank() ? null : checkoutUrl)
					.build();
			response.setPaymentResponse(payment);
		}
	}

	GeneralInfo toGeneralInfo(GeneralInfoDto dto);
}
