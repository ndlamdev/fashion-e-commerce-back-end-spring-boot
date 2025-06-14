/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 2:41 PM-18/05/2025
 * User: kimin
 **/

package com.lamnguyen.order_service.mapper;

import com.lamnguyen.order_service.domain.dto.OrderItemDto;
import com.lamnguyen.order_service.domain.response.OrderItemResponse;
import com.lamnguyen.order_service.model.OrderItemEntity;
import com.lamnguyen.order_service.protos.OrderItemRequest;
import com.lamnguyen.order_service.protos.TitleProduct;
import com.lamnguyen.order_service.protos.VariantProductInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IOrderItemMapper {
	@Mapping(target = "variantId", source = "data.id")
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "lock", ignore = true)
	@Mapping(target = "quantity", source = "quantity")
	OrderItemEntity toOrderItemEntity(VariantProductInfo data, int quantity);

	OrderItemDto toOrderStatusResponse(OrderItemEntity item);

	default OrderItemRequest toItemData(int quantity, VariantProductInfo variantInfo, TitleProduct product) {
		var builder = OrderItemRequest.newBuilder();
		builder.setPrice((int) variantInfo.getRegularPrice());
		String title = product.getTitle().getValue() + " / " + variantInfo.getTitle();
		builder.setTitle(title);
		builder.setQuantity(quantity);
		return builder.build();
	}

	@Mapping(target = "product.id", source = "productId")
	@Mapping(target = "variant.id", source = "variantId")
	OrderItemResponse toResponse(OrderItemDto dto);

	@Mapping(target = "product.id", source = "productId")
	@Mapping(target = "variant.id", source = "variantId")
	OrderItemResponse toResponse(OrderItemEntity dto);
}
