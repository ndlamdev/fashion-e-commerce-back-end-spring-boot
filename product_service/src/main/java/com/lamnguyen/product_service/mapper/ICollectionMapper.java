/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 1:00 PM - 09/04/2025
 * User: kimin
 **/

package com.lamnguyen.product_service.mapper;

import com.lamnguyen.product_service.domain.dto.CollectionDto;
import com.lamnguyen.product_service.domain.dto.CollectionSaveRedisDto;
import com.lamnguyen.product_service.domain.request.TitleCollectionRequest;
import com.lamnguyen.product_service.domain.request.UpdateCollectionRequest;
import com.lamnguyen.product_service.model.Collection;
import com.lamnguyen.product_service.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {IProductMapper.class})
public interface ICollectionMapper {
	Collection toCollection(TitleCollectionRequest titleCollectionRequest);

	Collection toCollection(UpdateCollectionRequest updateCollectionRequest);

	Collection toCollection(CollectionDto dto);

	CollectionDto toCollectionDto(Collection collection);

	@Mapping(source = "products", target = "products", qualifiedByName = "toSetProductId")
	CollectionSaveRedisDto toCollectionSaveRedisDto(Collection collection);

	@Named("toSetProductId")
	default Set<String> toSetProductId(Set<Product> products) {
		return products.stream().map(Product::getId).collect(Collectors.toSet());
	}
}
