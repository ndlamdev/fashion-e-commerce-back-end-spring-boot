/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 6:52 AM-19/05/2025
 * User: kimin
 **/

package com.lamnguyen.payment_service.repository;

import com.lamnguyen.payment_service.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IPaymentRepository extends JpaRepository<Payment, Long> {
	Optional<Payment> findByOrderId(long orderId);

	Optional<Payment> findByOrderCode(long orderCode);
}
