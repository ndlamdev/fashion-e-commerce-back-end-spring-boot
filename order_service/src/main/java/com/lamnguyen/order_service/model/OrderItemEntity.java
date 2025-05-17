/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 4:29 PM-17/05/2025
 * User: kimin
 **/

package com.lamnguyen.order_service.model;

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
@Table(name = "order_items")
public class OrderItemEntity extends MysqlBaseEntity {
	String productId;
	String variantId;
	int quantity;
	double comparePrice;
	double regularPrice;

	@ManyToOne
	@JoinColumn(name = "order_id")
	OrderEntity order;
}
