/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 6:36 PM-17/05/2025
 * User: kimin
 **/

package com.lamnguyen.order_service.mapper;

import com.lamnguyen.order_service.domain.response.VariantProductResponse;
import com.lamnguyen.order_service.protos.VariantProductInfo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IVariantProductMapper {
	VariantProductResponse toResponse(VariantProductInfo variantProduct);
}
