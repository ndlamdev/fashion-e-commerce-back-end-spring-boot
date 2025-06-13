/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 3:11 PM-23/05/2025
 * User: kimin
 **/

package com.lamnguyen.order_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lamnguyen.order_service.model.OrderItemEntity;

@Repository
public interface IOrderItemRepository extends JpaRepository<OrderItemEntity, Long> {
	void deleteAllByOrder_Id(long orderId);
}
