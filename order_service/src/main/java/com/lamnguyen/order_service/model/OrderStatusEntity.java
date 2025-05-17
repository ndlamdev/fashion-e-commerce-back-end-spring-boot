/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 4:40 PM-17/05/2025
 * User: kimin
 **/

package com.lamnguyen.order_service.model;

import com.lamnguyen.order_service.utils.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "statuses")
public class OrderStatusEntity extends MysqlBaseEntity {
	OrderStatus status;
	String note;

	@ManyToMany
	@JoinTable(
			name = "status_orders",
			joinColumns = @JoinColumn(name = "order_id"),
			inverseJoinColumns = @JoinColumn(name = "status_id"))
	List<OrderEntity> orders;
}
