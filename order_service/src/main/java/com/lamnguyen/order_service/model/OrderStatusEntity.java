/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 4:40 PM-17/05/2025
 * User: kimin
 **/

package com.lamnguyen.order_service.model;

import com.lamnguyen.order_service.utils.enums.OrderStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "statuses")
public class OrderStatusEntity extends BaseEntity {
	OrderStatus status;
	String note;

	@ManyToOne
	@JoinColumn(name = "order_id")
	OrderEntity orders;
}
