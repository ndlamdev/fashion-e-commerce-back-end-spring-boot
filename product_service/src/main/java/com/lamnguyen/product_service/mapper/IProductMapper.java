/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 1:55 PM - 09/04/2025
 * User: kimin
 **/

package com.lamnguyen.product_service.mapper;

import com.lamnguyen.product_service.domain.dto.ProductDto;
import com.lamnguyen.product_service.domain.request.CreateProductRequest;
import com.lamnguyen.product_service.model.Collection;
import com.lamnguyen.product_service.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.text.Normalizer;
import java.util.regex.Pattern;

@Mapper(componentModel = "spring", uses = {IVariantMapper.class})
public interface IProductMapper {
	@Mapping(source = "collection", target = "collection", qualifiedByName = "toCollection")
	@Mapping(source = "images", target = "images", ignore = true)
	@Mapping(source = "iconThumbnail", target = "iconThumbnail", ignore = true)
	@Mapping(source = "seoAlias", target = "title", qualifiedByName = "toSeoAlias")
	Product toProduct(CreateProductRequest request);

	@Mapping(source = "collection.id", target = "collection")
	ProductDto toProductDto(Product product);

	@Mapping(source = "collection", target = "collection", qualifiedByName = "toCollection")
	Product toProduct(ProductDto dto);

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
}
