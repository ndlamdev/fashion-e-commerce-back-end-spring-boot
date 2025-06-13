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
	@Mapping(target = "items", source = "data.itemsList")
	PaymentData toPaymentData(PaymentRequest data, long orderCode) throws Exception;

	@AfterMapping
	default void afterMapping(PaymentRequest data, @MappingTarget PaymentData.PaymentDataBuilder paymentData) {
		var amount = data.getItemsList()
				.stream()
				.map(it -> it.getPrice() * it.getQuantity()).reduce(Integer::sum)
				.orElse(0);
		System.out.println("amount = " + amount);
		paymentData.amount(2000);
	}

	@Mapping(target = "method", source = "method", qualifiedByName = "toPaymentMethod")
	Payment toPayment(PaymentRequest orderRequest);

	@Mappings({
			@Mapping(target = "id", source = "payment.id"),
			@Mapping(target = "status", source = "payment.status"),
			@Mapping(target = "method", source = "payment.method"),
			@Mapping(target = "orderCode", source = "payment.orderCode"),
	})
	PaymentResponse toPaymentResponse(Payment payment, String checkoutUrl);

	@Named("toPaymentMethod")
	default PaymentMethod toPaymentMethod(com.lamnguyen.payment_service.protos.PaymentMethod paymentMethod) {
		return PaymentMethod.valueOf(paymentMethod.name());
	}

	@AfterMapping
	default void afterMapping(@MappingTarget Payment payment) {
		payment.setOrderCode(System.currentTimeMillis());
	}
}
