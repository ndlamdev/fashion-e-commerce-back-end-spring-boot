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
import com.lamnguyen.product_service.domain.response.ImageResponse;
import com.lamnguyen.product_service.domain.response.ProductResponse;
import com.lamnguyen.product_service.domain.response.QuickProductResponse;
import com.lamnguyen.product_service.model.Collection;
import com.lamnguyen.product_service.model.Product;
import com.lamnguyen.product_service.protos.ProductInCartDto;
import org.mapstruct.*;

import java.text.Normalizer;
import java.util.regex.Pattern;

@Mapper(componentModel = "spring", uses = {IImageOptionsValueMapper.class, ICollectionMapper.class, IImageMapper.class, IOptionMapper.class, IGrpcMapper.class})
public interface IProductMapper {
	@Mappings({
			@Mapping(target = "id", ignore = true),
			@Mapping(target = "lock", ignore = true),
			@Mapping(target = "createBy", ignore = true),
			@Mapping(target = "createAt", ignore = true),
			@Mapping(target = "updateBy", ignore = true),
			@Mapping(target = "updateAt", ignore = true),
			@Mapping(target = "available", ignore = true),
			@Mapping(source = "collection", target = "collection", qualifiedByName = "toCollection"),
			@Mapping(source = "title", target = "seoAlias", qualifiedByName = "toSeoAlias"),
			@Mapping(source = "title", target = "titleSearch", qualifiedByName = "toTitleSearch"),
	})
	Product toProduct(DataProductRequest request);

	@Mapping(source = "collection.id", target = "collection")
	@Mapping(source = "iconThumbnail", target = "iconThumbnail", qualifiedByName = "toImageDto")
	@Mapping(source = "images", target = "images", qualifiedByName = "toImageDto")
	@Mapping(target = "variants", ignore = true)
	@Mapping(target = "review", ignore = true)
	ProductResponse toProductResponse(Product product);

	@Mapping(source = "iconThumbnail", target = "iconThumbnail", ignore = true)
	@Mapping(source = "images", target = "images", ignore = true)
	@Mapping(target = "variants", ignore = true)
	@Mapping(target = "review", ignore = true)
	ProductResponse toProductResponse(ProductDto product);

	@Mapping(source = "collection.id", target = "collection")
	ProductDto toProductDto(Product product);

	@Mapping(source = "iconThumbnail", target = "iconThumbnail", qualifiedByName = "toImageId")
	@Mapping(source = "images", target = "images", qualifiedByName = "toImageId")
	@Mapping(source = "collection", target = "collection.id")
	Product toProduct(ProductResponse response);

	@Mapping(source = "images", target = "image", ignore = true)
	QuickProductResponse toQuickProductResponse(Product response);

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

		// Bước 5: Thay khoảng trắng bằng dấu gạch ngang
		return withoutDiacritics.replaceAll("\\s+", "-");
	}

	@Named("toTitleSearch")
	default String toTitleSearch(String title) {
		return toSeoAlias(title).replaceAll("-", " ");
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

	@Mapping(target = "image", source = "image")
	@Mapping(target = "id", source = "product.id")
	@Mapping(target = "lock", source = "product.lock")
	@Mapping(target = "unknownFields", ignore = true)
	@Mapping(target = "allFields", ignore = true)
	ProductInCartDto toProductInCartDto(Product product, ImageResponse image);

	@Mapping(target = "image", source = "response.images.first")
	@Mapping(target = "id", source = "response.id")
	@Mapping(target = "lock", source = "response.lock")
	@Mapping(target = "unknownFields", ignore = true)
	@Mapping(target = "allFields", ignore = true)
	ProductInCartDto toProductInCartDto(ProductResponse response);

	com.lamnguyen.product_service.protos.ProductDto toProductDto(
			ProductResponse response,
			IImageMapper imageMapper,
			IImageOptionsValueMapper imageOptionsValueMapper,
			IOptionMapper optionMapper,
			IOptionItemMapper optionItemMapper,
			IGrpcMapper grpcMapper
	);


	@AfterMapping
	default void afterMapping(
			ProductResponse response,
			@MappingTarget com.lamnguyen.product_service.protos.ProductDto.Builder builder,
			IImageMapper imageMapper,
			IImageOptionsValueMapper imageOptionsValueMapper,
			IOptionMapper optionMapper,
			IOptionItemMapper optionItemMapper,
			IGrpcMapper grpcMapper
	) {
		if (response.getImages() != null)
			builder.addAllImages(response.getImages().stream().map(imageMapper::toImage).toList());
		if (response.getOptions() != null)
			builder.addAllOptions(response.getOptions().stream().map(data -> optionMapper.toOptionDto(data, grpcMapper)).toList());
		if (response.getTags() != null)
			builder.addAllTags(response.getTags().stream().map(data -> com.lamnguyen.product_service.protos.ProductTag.valueOf(data.name())).toList());
		if (response.getOptionsValues() != null)
			builder.addAllOptionsValues(response.getOptionsValues().stream().map(data -> imageOptionsValueMapper.toImageOptionsValueDto(data, imageMapper, optionItemMapper)).toList());
	}
}
