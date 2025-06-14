/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 2:32 AM-07/05/2025
 * User: Administrator
 **/

package com.lamnguyen.order_service.mapper;

import com.lamnguyen.order_service.domain.response.ProductResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {IGrpcMapper.class})
public interface IProductMapper {
	ProductResponse toProductResponse(com.lamnguyen.order_service.protos.ProductInCartDto product);
}
