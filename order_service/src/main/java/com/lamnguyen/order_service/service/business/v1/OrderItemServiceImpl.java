/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 3:12 PM-23/05/2025
 * User: kimin
 **/

package com.lamnguyen.order_service.service.business.v1;

import com.lamnguyen.order_service.repository.IOrderItemRepository;
import com.lamnguyen.order_service.service.business.IOrderItemService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderItemServiceImpl implements IOrderItemService {
	IOrderItemRepository orderItemRepository;

	@Override
	@Transactional
	public void deleteAllByOrderId(long orderId) {
		orderItemRepository.deleteAllByOrder_Id(orderId);
	}
}
