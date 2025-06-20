/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 7:08 AM-03/05/2025
 * User: kimin
 **/

package com.lamnguyen.cart_service.repository;

import com.lamnguyen.cart_service.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface
ICartRepository extends JpaRepository<Cart, Long> {
	Optional<Cart> findByUserId(long userId);
}
