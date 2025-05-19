/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:07 AM-19/05/2025
 * User: kimin
 **/

package com.lamnguyen.order_service.repository;

import com.lamnguyen.order_service.model.OrderStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrderStatusRepository extends JpaRepository<OrderStatusEntity, Long> {
}
