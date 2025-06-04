/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 9:33 AM-19/05/2025
 * User: kimin
 **/

package com.lamnguyen.order_service.service.grpc.v1;

import com.lamnguyen.order_service.protos.OrderIdRequest;
import com.lamnguyen.order_service.protos.PaymentRequest;
import com.lamnguyen.order_service.protos.PaymentResponse;
import com.lamnguyen.order_service.protos.PaymentServiceGrpc;
import com.lamnguyen.order_service.service.grpc.IPaymentGrpcClient;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class PaymentGrpcClientImpl implements IPaymentGrpcClient {
	@GrpcClient("fashion-e-commerce-payment-service")
	public PaymentServiceGrpc.PaymentServiceBlockingStub paymentServiceBlockingStub;

	@Override
	public PaymentResponse pay(PaymentRequest paymentRequest) {
		return paymentServiceBlockingStub.pay(paymentRequest);
	}

	@Override
	public void cancelPay(long orderId) {
		var request = OrderIdRequest.newBuilder()
				.setId(orderId)
				.build();
		var ignored = paymentServiceBlockingStub.cancelOrder(request);
	}

	@Override
	public PaymentResponse getPaymentStatus(long orderId) {
		var request = OrderIdRequest.newBuilder()
				.setId(orderId)
				.build();
		return paymentServiceBlockingStub.getPaymentStatus(request);
	}
}
