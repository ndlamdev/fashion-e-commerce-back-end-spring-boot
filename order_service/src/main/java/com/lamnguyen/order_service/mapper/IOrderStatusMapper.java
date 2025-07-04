/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 9:40 AM-19/05/2025
 * User: kimin
 **/

package com.lamnguyen.order_service.mapper;

import com.lamnguyen.order_service.domain.dto.OrderStatusDto;
import com.lamnguyen.order_service.model.OrderStatusEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IOrderStatusMapper {
	OrderStatusDto toOrderStatusResponse(OrderStatusEntity status);
}
