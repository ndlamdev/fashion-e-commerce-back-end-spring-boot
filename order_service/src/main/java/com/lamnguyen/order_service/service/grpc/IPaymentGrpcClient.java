/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 7:05 AM-19/05/2025
 * User: kimin
 **/

package com.lamnguyen.order_service.service.grpc;

import com.lamnguyen.order_service.protos.PaymentRequest;
import com.lamnguyen.order_service.protos.PaymentResponse;

public interface IPaymentGrpcClient {
	PaymentResponse pay(PaymentRequest paymentRequest);

	void cancelPay(long orderId, long payOsOrderCode);

	void paySuccess(long orderId, long payOsOrderCode);
}
