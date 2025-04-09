/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 1:55 PM - 09/04/2025
 * User: kimin
 **/

package com.lamnguyen.product_service.mapper;

import com.lamnguyen.product_service.domain.dto.ProductDTO;
import com.lamnguyen.product_service.domain.request.CreateProductRequest;
import com.lamnguyen.product_service.model.Collection;
import com.lamnguyen.product_service.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IProductMapper {
	@Mapping(source = "collections", target = "collections", qualifiedByName = "toCollection", ignore = true)
	Product toProduct(CreateProductRequest request);

	@Mapping(source = "collections", target = "collections", qualifiedByName = "toCollectionIds", ignore = true)
	@Mapping(ignore = true, source = "variants", target = "variants")
	ProductDTO toDto(Product product);

	@Named("toCollection")
	default List<Collection> toCollection(List<String> collectionIds) {
		return collectionIds.stream().<Collection>map(it -> Collection.builder().id(it).build()).toList();
	}

	@Named("toCollectionIds")
	default List<String> toCollectionIds(List<Collection> collectionIds) {
		return collectionIds.stream().<String>map(Collection::getId).toList();
	}
}
