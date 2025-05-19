/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 4:29 PM-17/05/2025
 * User: kimin
 **/

package com.lamnguyen.order_service.model;

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
@Table(name = "orders")
public class OrderEntity extends BaseEntity {
	@Column(name = "customer_id", nullable = false)
	Long customerId;
	String name;
	String phone;
	String email;
	String addressDetail;
	String ward;
	String district;
	String province;
	String note;
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	List<OrderItemEntity> items;
	@OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
	List<OrderStatusEntity> statuses;
}
