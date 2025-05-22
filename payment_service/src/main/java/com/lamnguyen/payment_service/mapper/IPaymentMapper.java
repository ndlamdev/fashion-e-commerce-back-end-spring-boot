/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 6:32 AM-19/05/2025
 * User: kimin
 **/

package com.lamnguyen.payment_service.mapper;

import com.lamnguyen.payment_service.model.Payment;
import com.lamnguyen.payment_service.protos.PaymentRequest;
import com.lamnguyen.payment_service.protos.PaymentResponse;
import com.lamnguyen.payment_service.utils.enums.PaymentMethod;
import org.mapstruct.*;
import vn.payos.type.PaymentData;

@Mapper(componentModel = "spring", uses = {IGrpcMapper.class, IOrderItemMapper.class})
public interface IPaymentMapper {
	@Mapping(target = "buyerName", source = "data.name")
	@Mapping(target = "buyerEmail", source = "data.email")
	@Mapping(target = "buyerPhone", source = "data.phone")
	@Mapping(target = "buyerAddress", source = "data.address")
	@Mapping(target = "description", source = "data.note")
	@Mapping(target = "orderCode", source = "data.orderId")
	@Mapping(target = "returnUrl", source = "data.returnUrl")
	@Mapping(target = "cancelUrl", source = "data.cancelUrl")
	@Mapping(target = "items", source = "data.itemsList")
	PaymentData toPaymentData(PaymentRequest data) throws Exception;

	@AfterMapping
	default void afterMapping(PaymentRequest data, @MappingTarget PaymentData.PaymentDataBuilder paymentData) {
		data.getItemsList()
				.stream()
				.map(it -> it.getPrice() * it.getQuantity()).reduce(Integer::sum)
				.ifPresent(paymentData::amount);
		paymentData.orderCode(System.currentTimeMillis());
	}

	@Mapping(target = "method", source = "method", qualifiedByName = "toPaymentMethod")
	Payment toPayment(PaymentRequest orderRequest);

	@Mappings({
			@Mapping(target = "id", source = "payment.id"),
			@Mapping(target = "status", source = "payment.status"),
			@Mapping(target = "method", source = "payment.method"),
	})
	PaymentResponse toPaymentResponse(Payment payment, String checkoutUrl);

	@Named("toPaymentMethod")
	default PaymentMethod toPaymentMethod(com.lamnguyen.payment_service.protos.PaymentMethod paymentMethod) {
		return PaymentMethod.valueOf(paymentMethod.name());
	}
}
