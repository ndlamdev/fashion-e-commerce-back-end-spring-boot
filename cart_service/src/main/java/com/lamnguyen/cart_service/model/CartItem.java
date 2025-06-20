/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 5:08 PM-02/05/2025
 * User: kimin
 **/

package com.lamnguyen.cart_service.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "cart_items", uniqueConstraints = @UniqueConstraint(columnNames = {
		"variant_id", "cart_id"
}))
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Getter
@Setter
public class CartItem extends BaseEntity {
	@Column(name = "variant_id")
	String variantId;
	String productId;
	@Column(name = "quantity", columnDefinition = "int default 1 not null")
	int quantity;
	@ManyToOne()
	@JoinColumn(name = "cart_id")
	Cart cart;
}
