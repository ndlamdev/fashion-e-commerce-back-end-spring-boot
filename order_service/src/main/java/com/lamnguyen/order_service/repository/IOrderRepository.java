/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 9:10 AM-19/05/2025
 * User: kimin
 **/

package com.lamnguyen.order_service.repository;

import com.lamnguyen.order_service.domain.response.SubOrder;
import com.lamnguyen.order_service.model.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IOrderRepository extends JpaRepository<OrderEntity, Long> {
	@Query("""
			select new com.lamnguyen.order_service.domain.response.SubOrder(o.id, o.createAt, sum(oi.quantity * oi.regularPrice), os.status)
			from OrderEntity o
			join OrderStatusEntity os on o.id = os.order.id
			join OrderItemEntity oi on o.id = oi.order.id
			where o.userId = ?1
				and o.lock = false
				and o.delete = false
				and os.id = (
					select max(os2.id)
					from OrderStatusEntity os2
					where os2.order.id = o.id
				)
			group by o.id, o.createAt, os.status
			""")
	List<SubOrder> findHistoryOrderByUserIdAndLockIsFalseAndDeleteIsFalse(long userId);

	@Query("""
			select new com.lamnguyen.order_service.domain.response.SubOrder(o.id, o.createAt, sum(oi.quantity * oi.regularPrice), os.status)
			from OrderEntity o
			join OrderStatusEntity os on o.id = os.order.id
			join OrderItemEntity oi on o.id = oi.order.id
			where o.userId = ?1
				and o.delete = false
				and os.id = (
					select max(os2.id)
					from OrderStatusEntity os2
					where os2.order.id = o.id
				)
			group by o.id, o.createAt, os.status
			""")
	List<SubOrder> findHistoryOrderByUserIdAndDeleteIsFalse(long userId);

	Optional<OrderEntity> findByIdAndDeleteIsFalse(Long orderId);

	Optional<OrderEntity> findByIdAndDeleteIsFalseAndLockIsFalse(Long orderId);
}
