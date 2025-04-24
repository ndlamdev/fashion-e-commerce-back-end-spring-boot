/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 12:35 PM-24/04/2025
 * User: kimin
 **/

package com.lamnguyen.inventory_service.mapper;

import com.lamnguyen.inventory_service.model.VariantProduct;
import com.lamnguyen.inventory_service.protos.VariantProductInfo;
import com.lamnguyen.inventory_service.utils.enums.OptionType;
import org.mapstruct.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface IInventoryMapper {
	@Mapping(source = "options", target = "options", ignore = true)
	VariantProductInfo toVariantProductInfo(VariantProduct products);

	List<VariantProductInfo> toVariantProductInfo(List<VariantProduct> products);

	@AfterMapping
	default void handleOptions(VariantProduct product,
	                           @MappingTarget VariantProductInfo.Builder builder) {
		if (product == null) return;
		Map<String, String> optionResult = LinkedHashMap.newLinkedHashMap(product.getOptions().size());

		for (Map.Entry<OptionType, String> entry : product.getOptions().entrySet()) {
			String key = entry.getKey().name();
			String value = entry.getValue();
			optionResult.put(key, value);
		}

		builder.putAllOptions(optionResult);
	}
}
