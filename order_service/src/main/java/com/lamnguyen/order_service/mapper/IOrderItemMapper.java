/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 2:41 PM-18/05/2025
 * User: kimin
 **/

package com.lamnguyen.order_service.mapper;

import com.lamnguyen.order_service.domain.response.OrderItemResponse;
import com.lamnguyen.order_service.model.OrderItemEntity;
import com.lamnguyen.order_service.protos.OrderItemRequest;
import com.lamnguyen.order_service.protos.ProductInCartDto;
import com.lamnguyen.order_service.protos.VariantProductInfo;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface IOrderItemMapper {
	@Mapping(target = "variantId", source = "id")
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "lock", ignore = true)
	OrderItemEntity toOrderItemEntity(VariantProductInfo data);

	@Mapping(target = "title", ignore = true)
	@Mapping(target = "price", ignore = true)
	@Mapping(target = "unknownFields", ignore = true)
	@Mapping(target = "allFields", ignore = true)
	OrderItemRequest toItemData(int quantity, VariantProductInfo variantInfo, ProductInCartDto product);

	OrderItemResponse toOrderStatusResponse(OrderItemEntity item);

	@AfterMapping
	default void afterMapping(@MappingTarget OrderItemRequest.Builder builder, VariantProductInfo variantInfo, ProductInCartDto product) {
		builder.setPrice((int) variantInfo.getRegularPrice());
		builder.setTitle(product.getTitle() + " / " + variantInfo.getTitle());
	}
}
