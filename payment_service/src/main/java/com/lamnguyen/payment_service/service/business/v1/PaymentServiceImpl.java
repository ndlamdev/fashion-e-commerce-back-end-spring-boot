/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 2:31 PM-18/05/2025
 * User: kimin
 **/

package com.lamnguyen.payment_service.service.business.v1;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.lamnguyen.payment_service.config.exception.ApplicationException;
import com.lamnguyen.payment_service.config.exception.ExceptionEnum;
import com.lamnguyen.payment_service.mapper.IPaymentMapper;
import com.lamnguyen.payment_service.model.Payment;
import com.lamnguyen.payment_service.protos.PaymentRequest;
import com.lamnguyen.payment_service.protos.PaymentResponse;
import com.lamnguyen.payment_service.repository.IPaymentRepository;
import com.lamnguyen.payment_service.service.business.IPaymentService;
import com.lamnguyen.payment_service.service.redis.IPaymentCacheMange;
import com.lamnguyen.payment_service.utils.enums.PaymentMethod;
import com.lamnguyen.payment_service.utils.enums.PaymentStatus;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import vn.payos.PayOS;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Log4j2
public class PaymentServiceImpl implements IPaymentService {
	IPaymentMapper paymentMapper;
	PayOS payOS;
	IPaymentRepository paymentRepository;
	IPaymentCacheMange paymentCacheMange;

	@Override
	public PaymentResponse pay(PaymentRequest data) {
		String checkoutUrl = null;
		var payment = paymentMapper.toPayment(data);
		try {
			checkoutUrl = switch (data.getMethod()) {
				case PAY_OS -> getCheckoutUrlPayOs(data, payment.getOrderCode());
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

	private String getCheckoutUrlPayOs(PaymentRequest data, long orderCode) throws Exception {
		var payData = paymentMapper.toPaymentData(data, orderCode);
		return payOS.createPaymentLink(payData).getCheckoutUrl();
	}

	@Override
	public void cancelPayByOrderId(long orderId) throws Exception {
		var entity = paymentRepository.findByOrderId(orderId).orElseThrow(() -> ApplicationException.createException(ExceptionEnum.NOT_FOUND));
		if (entity.getMethod() == PaymentMethod.PAY_OS)
			payOS.cancelPaymentLink(entity.getOrderCode(), "Cancel order " + orderId);
		updatePaymentStatus(entity, PaymentStatus.CANCELED);
	}

	@Override
	public void paySuccess(long payOsOrderCode) {
		var entity = paymentRepository.findByOrderCode(payOsOrderCode).orElseThrow(() -> ApplicationException.createException(ExceptionEnum.NOT_FOUND));
		updatePaymentStatus(entity, PaymentStatus.DONE);
	}

	private void updatePaymentStatus(Payment payment, PaymentStatus status) {
		payment.setStatus(status);
		paymentRepository.save(payment);
		paymentCacheMange.deleteByOrderId(payment.getOrderId());
	}

	@Override
	public Long getOrderIdByOrderCode(long orderCode) {
		return paymentRepository.findByOrderCode(orderCode).map(Payment::getOrderId).orElse(null);
	}

	@Override
	public PaymentResponse getPaymentStatusByOrderId(long orderId) {
		var payment = paymentCacheMange.getByOrderId(orderId)
				.or(() -> this.fetchPaymentFromRepository(orderId))
				.orElseThrow(() -> ApplicationException.createException(ExceptionEnum.NOT_FOUND));
		return paymentMapper.toPaymentResponse(payment, null);
	}

	private Optional<Payment> fetchPaymentFromRepository(long orderId) {
		return paymentCacheMange.cacheByOrderId(
				orderId,
				() -> paymentRepository.findByOrderId(orderId)
		);
	}
}
