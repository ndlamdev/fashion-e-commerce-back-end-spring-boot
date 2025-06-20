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
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {IProductMapper.class})
public interface ICollectionMapper {
	@Mappings({
			@Mapping(target = "id", ignore = true),
			@Mapping(target = "lock", ignore = true),
			@Mapping(target = "createBy", ignore = true),
			@Mapping(target = "createAt", ignore = true),
			@Mapping(target = "updateBy", ignore = true),
			@Mapping(target = "updateAt", ignore = true),
			@Mapping(target = "products", ignore = true),
	})
	Collection toCollection(TitleCollectionRequest titleCollectionRequest);

	@Mappings({
			@Mapping(target = "id", ignore = true),
			@Mapping(target = "createBy", ignore = true),
			@Mapping(target = "createAt", ignore = true),
			@Mapping(target = "updateBy", ignore = true),
			@Mapping(target = "updateAt", ignore = true),
			@Mapping(target = "products", ignore = true),
	})
	Collection toCollection(UpdateCollectionRequest updateCollectionRequest);

	Collection toCollection(CollectionDto dto);

	CollectionDto toCollectionDto(Collection collection);

	@Mapping(source = "products", target = "products", qualifiedByName = "toSetProductIds")
	CollectionSaveRedisDto toCollectionSaveRedisDto(Collection collection);

	@Named("toSetProductIds")
	default Set<String> toSetProductIds(Set<Product> products) {
		return products.stream().map(Product::getId).collect(Collectors.toSet());
	}

	@Named("toSetProducts")
	default Set<Product> toSetProducts(Set<String> products) {
		return products.stream().map(it -> Product.builder().id(it).build()).collect(Collectors.toSet());
	}

	@Mapping(source = "products", target = "products", qualifiedByName = "toSetProducts")
	Collection toCollection(CollectionSaveRedisDto collectionSaveRedisDto);
}
