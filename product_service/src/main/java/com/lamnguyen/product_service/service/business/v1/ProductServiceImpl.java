/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 3:24 PM - 09/04/2025
 * User: kimin
 **/

package com.lamnguyen.product_service.service.business.v1;

import com.lamnguyen.product_service.config.exception.ApplicationException;
import com.lamnguyen.product_service.config.exception.ExceptionEnum;
import com.lamnguyen.product_service.domain.response.ImageOptionsValueResponse;
import com.lamnguyen.product_service.domain.response.ImageResponse;
import com.lamnguyen.product_service.domain.response.ProductResponse;
import com.lamnguyen.product_service.mapper.*;
import com.lamnguyen.product_service.protos.ProductDto;
import com.lamnguyen.product_service.protos.ProductInCartDto;
import com.lamnguyen.product_service.repository.IProductRepository;
import com.lamnguyen.product_service.service.business.IProductService;
import com.lamnguyen.product_service.service.grpc.IMediaGrpcClient;
import com.lamnguyen.product_service.service.grpc.IVariantGrpcClient;
import com.lamnguyen.product_service.service.redis.IProductRedisManager;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ProductServiceImpl implements IProductService {
	IProductMapper productMapper;
	IProductRepository productRepository;
	IProductRedisManager redisManager;
	IVariantGrpcClient variantService;
	IMediaGrpcClient mediaGrpcClient;
	IImageOptionsValueMapper imageOptionsValueMapper;
	IImageMapper imageMapper;
	IGrpcMapper grpcMapper;
	IOptionMapper optionMapper;
	IOptionItemMapper optionItemMapper;

	@Override
	public ProductResponse getProductById(String id) {
		var productDto = redisManager.get(id)
				.or(() -> redisManager.cache(id, () -> productRepository.findById(id).map(productMapper::toProductDto)))
				.orElseThrow(() -> ApplicationException.createException(ExceptionEnum.PRODUCT_NOT_FOUND));
		var result = productMapper.toProductResponse(productDto);
		result.setVariants(variantService.getVariantsByProductId(id));
		if (productDto.getIconThumbnail() != null) {
			var iconThumbnail = productDto.getIconThumbnail();
			var mediaResponse = mediaGrpcClient.getImageDto(List.of(iconThumbnail));
			result.setIconThumbnail(mediaResponse.getOrDefault(iconThumbnail, null));
		}

		if (productDto.getImages() != null && !productDto.getImages().isEmpty()) {
			var mediaResponse = mediaGrpcClient.getImageDto(productDto.getImages());
			result.setImages(mediaResponse.values().stream().toList());
		}

		if (productDto.getOptionsValues() != null && !productDto.getOptionsValues().isEmpty()) {
			var imageOptionsValueResponseArrayList = new ArrayList<ImageOptionsValueResponse>();
			productDto.getOptionsValues().forEach(imageOptionsValueDto -> {
				var imageOptionsValueResponse = imageOptionsValueMapper.toImageOptionsValueResponse(imageOptionsValueDto);
				var mapImageOptionItem = new HashMap<String, List<ImageResponse>>();
				imageOptionsValueDto.getOptions().forEach(optionItemDto -> {
					if (optionItemDto.getImages() == null || optionItemDto.getImages().isEmpty()) {
						return;
					}
					var mediaResponse = mediaGrpcClient.getImageDto(optionItemDto.getImages());
					mapImageOptionItem.put(optionItemDto.getTitle(), mediaResponse.values().stream().toList());
				});

				imageOptionsValueResponse.getOptions().forEach(optionItemDto -> {
					var images = mapImageOptionItem.getOrDefault(optionItemDto.getTitle(), null);
					if (images != null) optionItemDto.setImages(images);
					imageOptionsValueResponseArrayList.add(imageOptionsValueResponse);
				});
			});
			result.setOptionsValues(imageOptionsValueResponseArrayList);
		}

		return result;
	}

	@Override
	public ProductDto getProductProtoById(String id) {
		var response = getProductById(id);
		return productMapper.toProductDto(
				response,
				imageMapper,
				imageOptionsValueMapper,
				optionMapper,
				optionItemMapper,
				grpcMapper);
	}

	@Override
	public ProductInCartDto getProductInCartById(String id) {
		var productDto = redisManager.get(id)
				.or(() -> redisManager.cache(id, () -> productRepository.findById(id).map(productMapper::toProductDto)))
				.orElseThrow(() -> ApplicationException.createException(ExceptionEnum.PRODUCT_NOT_FOUND));
		var result = productMapper.toProductResponse(productDto);
		result.setVariants(variantService.getVariantsByProductId(id));

		if (productDto.getImages() != null && !productDto.getImages().isEmpty()) {
			var mediaResponse = mediaGrpcClient.getImageDto(productDto.getImages());
			result.setImages(mediaResponse.values().stream().toList());
		}

		return productMapper.toProductInCartDto(
				result,
				imageMapper,
				optionMapper,
				grpcMapper);
	}
}
