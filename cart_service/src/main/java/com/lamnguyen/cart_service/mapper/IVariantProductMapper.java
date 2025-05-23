/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 6:36 PM-17/05/2025
 * User: kimin
 **/

package com.lamnguyen.cart_service.mapper;

import com.lamnguyen.cart_service.domain.dto.VariantProductDto;
import com.lamnguyen.cart_service.protos.VariantProductInfo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IVariantProductMapper {
	VariantProductDto toDto(VariantProductInfo variantProduct);
}
