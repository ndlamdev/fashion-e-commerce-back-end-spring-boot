/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 3:24 PM - 09/04/2025
 * User: kimin
 **/

package com.lamnguyen.product_service.service.business.v1;

import com.google.protobuf.StringValue;
import com.lamnguyen.product_service.config.exception.ApplicationException;
import com.lamnguyen.product_service.config.exception.ExceptionEnum;
import com.lamnguyen.product_service.domain.response.ImageOptionsValueResponse;
import com.lamnguyen.product_service.domain.response.ImageResponse;
import com.lamnguyen.product_service.domain.response.ProductResponse;
import com.lamnguyen.product_service.domain.response.QuickProductResponse;
import com.lamnguyen.product_service.mapper.*;
import com.lamnguyen.product_service.model.Product;
import com.lamnguyen.product_service.model.ProductFilterAndSort;
import com.lamnguyen.product_service.protos.ProductResponseGrpc;
import com.lamnguyen.product_service.protos.ProductInCartDto;
import com.lamnguyen.product_service.protos.TitleProduct;
import com.lamnguyen.product_service.repository.IProductRepository;
import com.lamnguyen.product_service.service.business.IProductService;
import com.lamnguyen.product_service.service.grpc.IFileSearchGrpcClient;
import com.lamnguyen.product_service.service.grpc.IMediaGrpcClient;
import com.lamnguyen.product_service.service.grpc.IVariantGrpcClient;
import com.lamnguyen.product_service.service.redis.IProductDtoRedisManager;
import com.lamnguyen.product_service.service.redis.IProductResponseRedisManager;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ProductServiceImpl implements IProductService {
	IProductMapper productMapper;
	IProductRepository productRepository;
	IProductResponseRedisManager productResponseRedisManager;
	IVariantGrpcClient variantService;
	IMediaGrpcClient mediaGrpcClient;
	IImageOptionsValueMapper imageOptionsValueMapper;
	IImageMapper imageMapper;
	IGrpcMapper grpcMapper;
	IOptionMapper optionMapper;
	IOptionItemMapper optionItemMapper;
	IFileSearchGrpcClient fileSearchGrpcClient;
	MongoTemplate mongoTemplate;
	IProductDtoRedisManager productDtoRedisManager;

	@Override
	public ProductResponse getProductById(String id) {
		return productResponseRedisManager.get(id)
				.or(() -> productResponseRedisManager.cache(id, () -> getProductByIdFromDb(id)))
				.orElseThrow(() -> ApplicationException.createException(ExceptionEnum.PRODUCT_NOT_FOUND));
	}

	private Optional<ProductResponse> getProductByIdFromDb(String id) {
		var productOptional = productRepository.findById(id);
		if (productOptional.isEmpty()) return Optional.empty();
		var product = productOptional.get();
		return Optional.of(toProductResponse(product));
	}

	private void formatProductThumbnail(ProductResponse result, Product product) {
		if (product.getIconThumbnail() != null) {
			var iconThumbnail = product.getIconThumbnail();
			var mediaResponse = mediaGrpcClient.getImageDto(List.of(iconThumbnail));
			result.setIconThumbnail(mediaResponse.getOrDefault(iconThumbnail, null));
		}
	}

	private void formatProductImages(ProductResponse result, Product product) {
		if (product.getImages() != null && !product.getImages().isEmpty()) {
			var mediaResponse = mediaGrpcClient.getImageDto(product.getImages());
			result.setImages(mediaResponse.values().stream().toList());
		}
	}

	private void formatProductOptionsValues(ProductResponse result, Product product) {
		if ((product.getOptionsValues() == null || product.getOptionsValues().isEmpty())) return;
		var imageOptionsValueResponseArrayList = new ArrayList<ImageOptionsValueResponse>(20);
		var listTask = new ArrayList<CompletableFuture<Void>>();
		product.getOptionsValues().forEach(imageOptionsValueDto -> {
			var task = CompletableFuture.runAsync(() -> {
				var imageOptionsValueResponse = imageOptionsValueMapper
						.toImageOptionsValueResponse(imageOptionsValueDto);
				var listTaskOptionImage = formatOptionsImage(imageOptionsValueResponse);
				CompletableFuture.allOf(listTaskOptionImage.toArray(CompletableFuture[]::new)).join();
				imageOptionsValueResponseArrayList.add(imageOptionsValueResponse);
			});
			listTask.add(task);
		});
		CompletableFuture.allOf(listTask.toArray(CompletableFuture[]::new)).join();
		result.setOptionsValues(imageOptionsValueResponseArrayList);
	}

	private List<CompletableFuture<Void>> formatOptionsImage(ImageOptionsValueResponse imageOptionsValueResponse) {
		var listTask = new ArrayList<CompletableFuture<Void>>();
		imageOptionsValueResponse.getOptions()
				.forEach(optionItemDto -> {
					var task = CompletableFuture.runAsync(() -> {
						if (optionItemDto.getImages() == null || optionItemDto.getImages().isEmpty())
							return;

						var mediaResponse = mediaGrpcClient.getImageDto(
								optionItemDto
										.getImages()
										.stream()
										.map(ImageResponse::getId)
										.toList()
						);
						optionItemDto.setImages(mediaResponse.values().stream().toList());
					});
					listTask.add(task);
				});
		return listTask;
	}

	@Override
	public ProductResponseGrpc getProductResponseGrpcById(String id) {
		var response = getProductById(id);
		return productMapper.toProductResponseGrpc(
				response,
				imageMapper,
				imageOptionsValueMapper,
				optionMapper,
				optionItemMapper,
				grpcMapper);
	}

	@Override
	public ProductInCartDto getProductInCartById(String id) {
		var productResponse = productResponseRedisManager.get(id).orElse(null);
		if (productResponse != null)
			return productMapper.toProductInCartDto(productResponse);
		var product = productRepository.findById(id).orElseThrow(() -> ApplicationException.createException(ExceptionEnum.PRODUCT_NOT_FOUND));
		var imageId = product.getImages().getFirst();
		var response = mediaGrpcClient.getImageDto(List.of(imageId));
		var image = response.getOrDefault(imageId, null);
		return productMapper.toProductInCartDto(product, image);
	}

	@Override
	public List<ProductResponse> getProductByIds(List<String> ids) {
		var result = new ArrayList<ProductResponse>(20);
		var listTask = new ArrayList<CompletableFuture<Void>>();
		ids.forEach(id ->
				listTask.add(CompletableFuture.runAsync(() -> {
					try {
						result.add(getProductById(id));
					} catch (Exception ignored) {
					}
				}))
		);
		CompletableFuture.allOf(listTask.toArray(CompletableFuture[]::new)).join();
		return result;
	}

	@Override
	public Page<ProductResponse> searchByImage(File fileSearch) {
		try {
			var sameFileNamesWithFileSearch = fileSearchGrpcClient.searchFile(fileSearch);
			var sameIdsWithFileSearch = mediaGrpcClient
					.findImageByFileName(sameFileNamesWithFileSearch)
					.values()
					.stream()
					.map(ImageResponse::getId)
					.toList();
			var pageable = Pageable.ofSize(10);
			var products = productRepository
					.findByAllImageContainsContains(sameIdsWithFileSearch, pageable)
					.getContent();
			return new PageImpl<>(getProductResponses(products), pageable, pageable.getPageSize());
		} finally {
			var ignored = fileSearch.delete();
		}
	}

	private ProductResponse toProductResponse(Product product) {
		var result = productMapper.toProductResponse(product);

		var listTask = new ArrayList<CompletableFuture<Void>>();

		listTask.add(CompletableFuture
				.runAsync(() -> {
					var variants = variantService.getVariantsByProductId(product.getId());
					result.setVariants(variants);
				})
		);

		listTask.add(CompletableFuture.runAsync(() ->
				formatProductThumbnail(result, product)
		));

		listTask.add(CompletableFuture.runAsync(() ->
				formatProductImages(result, product)
		));

		listTask.add(CompletableFuture.runAsync(() ->
				formatProductOptionsValues(result, product)
		));

		CompletableFuture.allOf(listTask.toArray(CompletableFuture[]::new)).join();
		return result;
	}

	@Override
	public Page<ProductResponse> search(Pageable pageable, ProductFilterAndSort filterAndSort) {
		System.out.println(filterAndSort);
		var query = createQuery(filterAndSort);
		var products = mongoTemplate.find(query, Product.class);
		var content = products.stream().skip(pageable.getOffset()).limit(pageable.getPageSize()).toList();
		return new PageImpl<>(getProductResponses(content), pageable, products.size());
	}

	@Override
	public List<QuickProductResponse> quickSearch(String title) {
		var product = productRepository.findAllByTitleSearchRegex(productMapper.toSeoAlias(title).replaceAll("-", ".*"), Pageable.ofSize(10));
		var listTask = new ArrayList<CompletableFuture<Void>>();
		var result = new ArrayList<QuickProductResponse>(product.getSize());
		product.getContent().forEach(p -> listTask.add(CompletableFuture.runAsync(() -> {
			var mediaId = p.getImages().getFirst();
			var mediaResponse = mediaGrpcClient.getImageDto(List.of(mediaId));
			var data = productMapper.toQuickProductResponse(p);
			data.setImage(mediaResponse.getOrDefault(mediaId, null));
			result.add(data);
		})));
		CompletableFuture.allOf(listTask.toArray(CompletableFuture[]::new)).join();
		return result;
	}

	private List<ProductResponse> getProductResponses(List<Product> products) {
		var result = new ArrayList<ProductResponse>(products.size());
		var listTask = new ArrayList<CompletableFuture<Void>>();
		products.forEach(product -> {
			listTask.add(CompletableFuture.runAsync(() -> {
				try {
					var response = productResponseRedisManager.get(product.getId())
							.or(() ->
									productResponseRedisManager.cache(
											product.getId(),
											() -> Optional.of(toProductResponse(product))
									)
							).orElseThrow();
					result.add(response);
				} catch (Exception ignored) {
				}
			}));
		});

		CompletableFuture.allOf(listTask.toArray(CompletableFuture[]::new)).join();

		return result;
	}

	private Query createQuery(ProductFilterAndSort filterAndSort) {
		Query query = new Query();


		if (filterAndSort.title() != null) {
			var titleQuery = productMapper.toSeoAlias(filterAndSort.title()).replaceAll("-", ".*");
			query.addCriteria(
					Criteria.where("title_search")
							.regex(titleQuery, "i")
			);
		}

		query.addCriteria(Criteria.where("is_lock").is(false));

		if (filterAndSort.colors() != null && !filterAndSort.colors().isEmpty())
			query.addCriteria(
					Criteria.where("options")
							.elemMatch(
									Criteria.where("type")
											.is("COLOR")
											.andOperator(filterAndSort.colors().stream().map(color -> Criteria.where("values").regex("^" + Pattern.quote(color) + "$", "i")).toList())
							)
			);

		if (filterAndSort.sizes() != null && !filterAndSort.sizes().isEmpty())
			query.addCriteria(
					Criteria.where("options")
							.elemMatch(
									Criteria.where("type")
											.is("SIZE")
											.andOperator(filterAndSort.sizes().stream().map(size -> Criteria.where("values").regex("^" + Pattern.quote(size) + "$", "i")).toList())
							)
			);

		if (filterAndSort.sort() != null) {
			query.addCriteria(Criteria.where("tags").in(filterAndSort.sort().tag().name()));
			query.with(Sort.by(filterAndSort.sort().direction(), filterAndSort.sort().tag().name()));
		}

		return query;
	}

	@Override
	public TitleProduct getTitleProductById(String productId) {
		var productDto = productDtoRedisManager
				.get(productId)
				.or(() -> productDtoRedisManager
						.cache(
								productId,
								productId,
								() -> productRepository
										.findById(productId)
										.map(productMapper::toProductDto)
						)
				)
				.orElseThrow(() -> ApplicationException.createException(ExceptionEnum.PRODUCT_NOT_FOUND));
		return TitleProduct.newBuilder()
				.setTitle(StringValue.newBuilder().setValue(productDto.getTitle()).build())
				.setId(productDto.getId())
				.build();
	}
}
