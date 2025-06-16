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
import com.lamnguyen.product_service.domain.request.IdCollectionRequest;
import com.lamnguyen.product_service.domain.request.TitleCollectionRequest;
import com.lamnguyen.product_service.domain.response.CollectionResponse;
import com.lamnguyen.product_service.domain.response.ProductResponse;
import com.lamnguyen.product_service.mapper.ICollectionMapper;
import com.lamnguyen.product_service.model.Collection;
import com.lamnguyen.product_service.model.Product;
import com.lamnguyen.product_service.repository.ICollectionRepository;
import com.lamnguyen.product_service.service.business.ICollectionManageService;
import com.lamnguyen.product_service.service.business.IProductService;
import com.lamnguyen.product_service.service.redis.ICollectionRedisManager;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.data.mongodb.core.aggregation.ConditionalOperators;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CollectionManageServiceImpl implements ICollectionManageService {
    ICollectionRepository collectionRepository;
    ICollectionMapper collectionMapper;
    ICollectionRedisManager cacheManager;
    IProductService productService;
    MongoTemplate mongoTemplate;

    @Override
    public boolean existsById(String id) {
        return collectionRepository.existsById(id);
    }

    @Override
    public void create(TitleCollectionRequest request) {
        var collection = collectionMapper.toCollection(request);
        var inserted = collectionRepository.insert(collection);
        cacheManager.save(inserted.getId(), collectionMapper.toCollectionSaveRedisDto(inserted));
    }

    @Override
    public List<CollectionResponse> getAll() {
        var aggregation = Aggregation.newAggregation(
                Aggregation.project("id", "title", "type", "createAt")
                        .and(ConditionalOperators.ifNull("products")
                                .then(Collections.emptyList())).as("products"),
                Aggregation.project("id", "title", "type", "createAt")
                        .and(ArrayOperators.Size.lengthOfArray("products"))
                        .as("totalProducts")
        );
        return mongoTemplate.aggregate(aggregation, Collection.class, CollectionResponse.class).getMappedResults();
    }

    @Override
    public void delete(IdCollectionRequest request) {
        cacheManager.delete(request.id());
    }

    @Override
    public List<ProductResponse> getAllProductByCollectionId(String id) {
        var collection = cacheManager.get(id).orElseGet(() -> cacheManager.cache(id, id, () -> {
            var data = collectionRepository.findById(id);
            return data.map(collectionMapper::toCollectionSaveRedisDto);
        }).orElseThrow(() -> ApplicationException.createException(ExceptionEnum.COLLECTION_NOT_FOUND)));
        return collection.getProducts().stream().map(productService::getProductById).collect(Collectors.toList());
    }

    @Override
    @Async
    public void addProductId(String collectionId, String productId) {
        var collection = collectionRepository.findById(collectionId).orElseThrow(() -> ApplicationException.createException(ExceptionEnum.COLLECTION_NOT_FOUND));
        collection.getProducts().add(Product.builder().id(productId).build());
        collectionRepository.save(collection);
        cacheManager.delete(collectionId);
    }

    @Override
    @Async
    public void removeProductId(String collectionId, String productId) {
        var collection = collectionRepository.findById(collectionId).orElseThrow(() -> ApplicationException.createException(ExceptionEnum.COLLECTION_NOT_FOUND));
        collection.getProducts().removeIf(product -> product.getId().equals(productId));
        collectionRepository.save(collection);
        cacheManager.delete(collectionId);
    }

    @Override
    @Async
    public void removeProductId(Collection collection, String productId) {
        collection.getProducts().removeIf(product -> product.getId().equals(productId));
        collectionRepository.save(collection);
        cacheManager.delete(collection.getId());
    }
}
