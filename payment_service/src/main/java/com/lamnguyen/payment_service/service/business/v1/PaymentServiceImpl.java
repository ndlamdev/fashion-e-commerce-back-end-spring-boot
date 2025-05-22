/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 2:31 PM-18/05/2025
 * User: kimin
 **/

package com.lamnguyen.payment_service.service.business.v1;

import com.lamnguyen.payment_service.config.PayOsConfig;
import com.lamnguyen.payment_service.mapper.IPaymentMapper;
import com.lamnguyen.payment_service.protos.PaymentRequest;
import com.lamnguyen.payment_service.protos.PaymentResponse;
import com.lamnguyen.payment_service.repository.IPaymentRepository;
import com.lamnguyen.payment_service.service.business.IPaymentService;
import com.lamnguyen.payment_service.utils.enums.PaymentStatus;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import vn.payos.PayOS;
import vn.payos.type.PaymentData;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Log4j2
public class PaymentServiceImpl implements IPaymentService {
	IPaymentMapper paymentMapper;
	PayOS payOS;
	PayOsConfig payOsConfig;
	IPaymentRepository paymentRepository;

	@Override
	public PaymentResponse pay(PaymentRequest data) {
		String checkoutUrl = null;
		var payment = paymentMapper.toPayment(data);
		try {
			checkoutUrl = switch (data.getMethod()) {
				case PAY_OS -> getCheckoutUrlPayOs(data);
				case CASH, MOMO, ZALO_PAY, UNRECOGNIZED -> null;
			};

			payment.setStatus(PaymentStatus.PENDING);
		} catch (Exception e) {
			log.error("Pay error: {}", e.getMessage());
			payment.setStatus(PaymentStatus.FAIL);
		}
		payment = paymentRepository.save(payment);
		return paymentMapper.toPaymentResponse(payment, checkoutUrl);
	}

	private String getCheckoutUrlPayOs(PaymentRequest data) throws Exception {
		var payData = paymentMapper.toPaymentData(data);
		return payOS.createPaymentLink(payData).getCheckoutUrl();
	}

	@Override
	public void cancelPay(long orderId, long payOsOrderCode) throws Exception {
		var entity = paymentRepository.findByOrderId(orderId);
		entity.setStatus(PaymentStatus.CANCELED);
		payOS.cancelPaymentLink(payOsOrderCode, "");
	}

	@Override
	public void paySuccess(long orderId, long payOsOrderCode) {
		var entity = paymentRepository.findByOrderId(orderId);
		entity.setStatus(PaymentStatus.DONE);
	}
}
