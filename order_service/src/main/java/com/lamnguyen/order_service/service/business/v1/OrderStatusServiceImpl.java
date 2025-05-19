/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:05 AM-19/05/2025
 * User: kimin
 **/

package com.lamnguyen.order_service.service.business.v1;

import com.lamnguyen.order_service.model.OrderEntity;
import com.lamnguyen.order_service.model.OrderStatusEntity;
import com.lamnguyen.order_service.repository.IOrderStatusRepository;
import com.lamnguyen.order_service.service.business.IOrderStatusService;
import com.lamnguyen.order_service.utils.enums.OrderStatus;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderStatusServiceImpl implements IOrderStatusService {
	IOrderStatusRepository orderStatusRepository;

	@Override
	public OrderStatusEntity addStatus(long orderId, OrderStatus status, String note) {
		return orderStatusRepository.save(OrderStatusEntity.builder()
				.orders(OrderEntity.builder().id(orderId).build())
				.note(note)
				.status(status)
				.build());
	}
}
