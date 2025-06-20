/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 4:45 CH-24/04/2025
 * User: Administrator
 **/

package com.lamnguyen.product_service.mapper;

import com.lamnguyen.product_service.domain.response.VariantResponse;
import com.lamnguyen.product_service.protos.VariantProductInfo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IVariantMapper {
	VariantResponse toVariantResponse(VariantProductInfo variant);

	List<VariantResponse> toVariantResponse(List<VariantProductInfo> variant);
}
