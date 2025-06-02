/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 12:57 PM - 09/04/2025
 * User: kimin
 **/

package com.lamnguyen.product_service.service.business.v1;

import com.lamnguyen.product_service.config.exception.ApplicationException;
import com.lamnguyen.product_service.config.exception.ExceptionEnum;
import com.lamnguyen.product_service.domain.request.DataProductRequest;
import com.lamnguyen.product_service.mapper.IOptionMapper;
import com.lamnguyen.product_service.mapper.IProductMapper;
import com.lamnguyen.product_service.model.Product;
import com.lamnguyen.product_service.repository.IProductRepository;
import com.lamnguyen.product_service.service.business.ICollectionManageService;
import com.lamnguyen.product_service.service.business.IProductManageService;
import com.lamnguyen.product_service.service.grpc.IMediaGrpcClient;
import com.lamnguyen.product_service.service.kafka.producer.IVariantService;
import com.lamnguyen.product_service.service.redis.IProductResponseRedisManager;
import com.lamnguyen.product_service.service.redis.v1.ProductDtoRedisManagerImpl;
import com.lamnguyen.product_service.utils.helper.ValidationUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ProductManageServiceImpl implements IProductManageService {
	IProductRepository productRepository;
	IProductMapper productMapper;
	ICollectionManageService collectionManageService;
	IProductResponseRedisManager productResponseRedisManager;
	IMediaGrpcClient mediaGrpcClient;
	ValidationUtil validationUtil;
	private final ProductServiceImpl productServiceImpl;
	IVariantService variantService;
	IOptionMapper optionMapper;
	private final ProductDtoRedisManagerImpl productDtoRedisManagerImpl;

	@Override
	public void create(DataProductRequest request) {
		var product = productMapper.toProduct(request);
		validateProduct(product);
		updateAllImageContain(product);
		var inserted = productRepository.insert(product);
		collectionManageService.addProductId(request.getCollection(), inserted.getId());
		var options = optionMapper.toDataVariantOptions(inserted.getOptions());
		variantService.createVariant(inserted.getId(), request.getComparePrice(), request.getRegularPrice(), options);
	}

	@Override
	public void update(String id, DataProductRequest request) {
		var oldProduct = productServiceImpl.getProductById(id);
		var product = productMapper.toProduct(request);
		product.setId(id);
		validateProduct(product);

		if (!oldProduct.getCollection().equals(product.getCollection().getId())) {
			collectionManageService.removeProductId(oldProduct.getCollection(), product.getId());
			collectionManageService.addProductId(request.getCollection(), product.getId());
		}
		updateAllImageContain(product);
		productRepository.save(product);
		var options = optionMapper.toDataVariantOptions(request.getOptions());
		variantService.updateVariant(oldProduct.getId(), request.getComparePrice(), request.getRegularPrice(), options);
		cleanCache(id);
	}

	@Override
	public void lock(String id, boolean isLock) {
		var product = productRepository.lock(id, isLock);
		cleanCache(id);
		collectionManageService.removeProductId(product.getCollection(), id);
	}

	private void validateProduct(Product product) {
		if (!collectionManageService.existsById(product.getCollection().getId()))
			throw ApplicationException.createException(ExceptionEnum.COLLECTION_NOT_FOUND);
		validationUtil.validate(product);
		var imagesChecked = mediaGrpcClient.existsByIds(product.getImages());
		var imagesExists = imagesChecked.entrySet().stream().filter(Map.Entry::getValue).map(Map.Entry::getKey).toList();
		product.setImages(imagesExists);
		if (product.getOptionsValues() != null) {
			product.getOptionsValues().forEach(optionsValue -> {
				optionsValue.getOptions().forEach(option -> {
					var imageOptionsChecked = mediaGrpcClient.existsByIds(option.getImages());
					var imageOptionsExists = imageOptionsChecked.entrySet().stream().filter(Map.Entry::getValue).map(Map.Entry::getKey).toList();
					option.setImages(imageOptionsExists);
				});
			});
		}
		if (product.getIconThumbnail() != null) {
			var thumbnailExits = mediaGrpcClient.existsById(product.getIconThumbnail());
			if (!thumbnailExits) product.setIconThumbnail(null);
		}
	}

	private void updateAllImageContain(Product product) {
		var allImage = new ArrayList<>(product.getImages());
		product.getOptionsValues().forEach(imageOptionsValueDto -> {
			imageOptionsValueDto.getOptions().forEach(optionItemDto -> {
				allImage.addAll(optionItemDto.getImages());
			});
		});
		product.setAllImageContains(allImage);
	}

	private void cleanCache(String id) {
		productResponseRedisManager.delete(id);
		productDtoRedisManagerImpl.delete(id);
	}
}
