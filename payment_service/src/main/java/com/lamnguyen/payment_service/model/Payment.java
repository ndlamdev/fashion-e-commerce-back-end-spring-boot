/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 6:25 AM-19/05/2025
 * User: kimin
 **/

package com.lamnguyen.payment_service.model;

import com.lamnguyen.payment_service.utils.enums.PaymentMethod;
import com.lamnguyen.payment_service.utils.enums.PaymentStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "Payments")
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Getter
@Setter
public class Payment extends BaseEntity {
	long orderId;
	long orderCode;
	PaymentStatus status;
	PaymentMethod method;
}
