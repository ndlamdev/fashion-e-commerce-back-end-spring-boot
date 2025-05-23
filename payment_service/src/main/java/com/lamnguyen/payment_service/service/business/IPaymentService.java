/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 2:22 PM-18/05/2025
 * User: kimin
 **/

package com.lamnguyen.payment_service.service.business;

import com.lamnguyen.payment_service.protos.PaymentRequest;
import com.lamnguyen.payment_service.protos.PaymentResponse;

public interface IPaymentService {
	PaymentResponse pay(PaymentRequest data);

	void cancelPayByOrderId(long orderId) throws Exception;


	void paySuccess(long payOsOrderCode);
}
