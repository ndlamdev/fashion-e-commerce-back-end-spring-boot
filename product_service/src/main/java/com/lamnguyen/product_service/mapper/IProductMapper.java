/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 1:55 PM-09/04/2025
 * User: kimin
 **/

package com.lamnguyen.product_service.mapper;

import com.lamnguyen.product_service.config.exception.ApplicationException;
import com.lamnguyen.product_service.config.exception.ExceptionEnum;
import com.lamnguyen.product_service.domain.dto.ProductDto;
import com.lamnguyen.product_service.domain.request.DataProductRequest;
import com.lamnguyen.product_service.domain.response.ProductResponse;
import com.lamnguyen.product_service.model.Collection;
import com.lamnguyen.product_service.model.Product;
import org.mapstruct.*;

import java.text.Normalizer;
import java.util.regex.Pattern;

@Mapper(componentModel = "spring", uses = {IImageOptionsValueMapper.class, ICollectionMapper.class, IImageMapper.class})
public interface IProductMapper {
	@Mapping(source = "collection", target = "collection", qualifiedByName = "toCollection")
	@Mapping(source = "title", target = "seoAlias", qualifiedByName = "toSeoAlias")
	Product toProduct(DataProductRequest request);

	@Mapping(source = "collection.id", target = "collection")
	@Mapping(source = "iconThumbnail", target = "iconThumbnail", qualifiedByName = "toImageDto")
	@Mapping(source = "images", target = "images", qualifiedByName = "toImageDto")
	ProductResponse toProductResponse(Product product);

	@Mapping(source = "collection", target = "collection")
	@Mapping(source = "iconThumbnail", target = "iconThumbnail", ignore = true)
	@Mapping(source = "images", target = "images", ignore = true)
	ProductResponse toProductResponse(ProductDto product);

	@Mapping(source = "collection.id", target = "collection")
	ProductDto toProductDto(Product product);

	@Mapping(source = "iconThumbnail", target = "iconThumbnail", qualifiedByName = "toImageId")
	@Mapping(source = "images", target = "images", qualifiedByName = "toImageId")
	@Mapping(source = "collection", target = "collection.id")
	Product toProduct(ProductResponse response);

	@Named("toCollection")
	default Collection toCollection(String id) {
		return Collection.builder().id(id).build();
	}

	@Named("toSeoAlias")
	default String toSeoAlias(String title) {
		// Bước 1: Chuyển toàn bộ thành chữ thường
		String lowerCase = title.toLowerCase();

		// Bước 2: Chuẩn hóa Unicode để tách dấu ra khỏi ký tự
		String normalized = Normalizer.normalize(lowerCase, Normalizer.Form.NFD);

		// Bước 3: Xóa các dấu (ký tự thuộc nhóm dấu thanh và mũ)
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		String withoutDiacritics = pattern.matcher(normalized).replaceAll("");

		// Bước 4: Thay thế các ký tự đặc biệt riêng của tiếng Việt
		withoutDiacritics = withoutDiacritics.replaceAll("đ", "d");

		// Bước 5: Thay khoảng trắng bằng dấu gạch dưới
		return withoutDiacritics.replaceAll("\\s+", "_");
	}

	@AfterMapping
	default void afterMappingProduct(@MappingTarget Product product, DataProductRequest request) {
		checkValueOptionHelper(product, request);
		checkValueProductTagHelper(product, request);
	}

	default void checkValueOptionHelper(Product product, DataProductRequest request) {
		if (request.getOptions() == null) return;

		if (request.getOptions().size() != product.getOptions().size()) {
			throw ApplicationException.createException(ExceptionEnum.DUPLICATE, "Duplicate option");
		}

		a:
		for (var requestOption : request.getOptions()) {
			for (var productOption : product.getOptions()) {
				if (requestOption.getType().equals(productOption.getType())) {
					if (requestOption.getValues().size() != productOption.getValues().size()) {
						throw ApplicationException.createException(ExceptionEnum.DUPLICATE, "Duplicate value in option " + productOption.getType());
					}
					continue a;
				}
			}
		}
	}

	default void checkValueProductTagHelper(Product product, DataProductRequest request) {
		if (request.getTags() == null || request.getTags().size() == product.getTags().size()) return;

		throw ApplicationException.createException(ExceptionEnum.DUPLICATE, "Duplicate tag");
	}
}
