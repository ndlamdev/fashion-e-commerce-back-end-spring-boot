/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 9:16 AM-05/05/2025
 * User: kimin
 **/

package com.lamnguyen.cart_service.repository;

import com.lamnguyen.cart_service.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICartItemRepository extends JpaRepository<CartItem, Long> {
	Optional<CartItem> findByCartIdAndVariantId(long cartId, String variantId);

	boolean existsByCartIdAndVariantId(long cartId, String variantId);

	Optional<CartItem> findByIdAndCartId(long id, long cartId);
}
