/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 2:41 PM-18/05/2025
 * User: kimin
 **/

package com.lamnguyen.payment_service.mapper;

import com.lamnguyen.payment_service.protos.OrderItemRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import vn.payos.type.ItemData;

@Mapper(componentModel = "spring")
public interface IOrderItemMapper {
	@Mapping(target = "name", source = "title")
	ItemData toItemData(OrderItemRequest data);
}
