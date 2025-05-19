/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 2:31 PM-18/05/2025
 * User: kimin
 **/

package com.lamnguyen.payment_service.service.business.v1;

import com.lamnguyen.payment_service.mapper.IPaymentMapper;
import com.lamnguyen.payment_service.protos.PaymentRequest;
import com.lamnguyen.payment_service.protos.PaymentResponse;
import com.lamnguyen.payment_service.repository.IPaymentRepository;
import com.lamnguyen.payment_service.service.business.IPaymentService;
import com.lamnguyen.payment_service.utils.enums.PaymentStatus;
import com.lamnguyen.payment_service.utils.helper.SignAndVerifyDataHelper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import vn.payos.PayOS;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentServiceImpl implements IPaymentService {
	IPaymentMapper paymentMapper;
	SignAndVerifyDataHelper signAndVerifyDataHelper;
	PayOS payOS;
	IPaymentRepository paymentRepository;

	@Override
	public PaymentResponse pay(PaymentRequest data) {
		String checkoutUrl = null;
		var payment = paymentMapper.toPayment(data);
		try {
			checkoutUrl = switch (data.getMethod()) {
				case PAY_OS -> getUrlPayOs(data);
				case CASH, MOMO, ZALO_PAY, UNRECOGNIZED -> null;
			};

			payment.setStatus(PaymentStatus.PENDING);
		} catch (Exception e) {
			payment.setStatus(PaymentStatus.FAIL);
		}
		payment = paymentRepository.save(payment);
		return paymentMapper.toPaymentResponse(payment, checkoutUrl);
	}

	private String getUrlPayOs(PaymentRequest data) throws Exception {
		var payData = paymentMapper.toPaymentData(data, signAndVerifyDataHelper);
		return payOS.createPaymentLink(payData).getCheckoutUrl();
	}

	@Override
	public void cancelPay(long orderId) throws Exception {
		var entity = paymentRepository.findByOrderId(orderId);
		entity.setStatus(PaymentStatus.CANCELED);
		payOS.cancelPaymentLink(orderId, "");
	}

	@Override
	public void paySuccess(long orderId) {
		var entity = paymentRepository.findByOrderId(orderId);
		entity.setStatus(PaymentStatus.DONE);
	}
}
