/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:15 AM-24/04/2025
 * User: kimin
 **/
package com.lamnguyen.inventory_service.service.business.v1;

import com.lamnguyen.inventory_service.config.exception.ApplicationException;
import com.lamnguyen.inventory_service.config.exception.ExceptionEnum;
import com.lamnguyen.inventory_service.domain.dto.VariantAndInventoryInfoDto;
import com.lamnguyen.inventory_service.mapper.IInventoryMapper;
import com.lamnguyen.inventory_service.message.DataVariantEvent;
import com.lamnguyen.inventory_service.model.VariantProduct;
import com.lamnguyen.inventory_service.protos.VariantAndInventoryInfo;
import com.lamnguyen.inventory_service.repository.InventoryRepository;
import com.lamnguyen.inventory_service.service.business.IInventoryService;
import com.lamnguyen.inventory_service.service.redis.IVariantByProductIdRedisManage;
import com.lamnguyen.inventory_service.service.redis.IVariantRedisManage;
import com.lamnguyen.inventory_service.utils.enums.OptionType;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * Implementation of the inventory management service
 */
@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InventoryServiceImpl implements IInventoryService {
    InventoryRepository inventoryRepository;
    IVariantByProductIdRedisManage variantProductRedisManage;
    IVariantRedisManage variantRedisManage;
    MongoTemplate mongoTemplate;
    IInventoryMapper inventoryMapper;

    @Override
    public void createVariantProduct(DataVariantEvent event) {
        List<DataVariantEvent.Option> options = event.options();

        // Generate all possible combinations of options
        List<Map<OptionType, String>> optionCombinations = generateOptionCombinations(options);

        // Create inventory records for each combination
        optionCombinations.forEach(optionCombination -> insertNewVariant(event.id(), event.comparePrice(), event.regularPrice(), optionCombination));
    }

    @Override
    public boolean updateInventoryQuantity(String productId, Map<OptionType, String> options, int quantity) {
        return inventoryRepository
                .findByProductIdAndOptions(productId, options)
                .map(inventory -> {
                    inventoryRepository.save(inventory);
                    log.info("Updated inventory quantity for product {} with options {} to {}", productId, options, quantity);
                    return true;
                }).orElse(false);
    }


    @Override
    public List<VariantProduct> getAllInventoryByProductId(String productId) {
        return Arrays.stream(variantProductRedisManage.get(productId).or(() -> variantProductRedisManage
                        .cache(productId, () -> Optional.of(inventoryRepository
                                .findByProductIdAndDeleteFalseAndLockFalse(productId)
                                .toArray(VariantProduct[]::new))))
                .orElseGet(() -> new VariantProduct[0])).toList();
    }

    @Override
    public void updateVariantProduct(DataVariantEvent event) {
        var oldVariantProduct = getAllInventoryByProductId(event.id());
        var mapOldVariantProduct = oldVariantProduct
                .stream()
                .collect(Collectors.toMap(VariantProduct::getSku, variantProduct -> variantProduct));
        List<Map<OptionType, String>> optionCombinations = generateOptionCombinations(event.options());
        var mapOptionCombinations = optionCombinations.stream()
                .collect(Collectors.toMap(map -> generateSku(event.id(), map),
                        map -> map));
        for (var entryOptionCombination : mapOptionCombinations.entrySet()) {
            if (mapOldVariantProduct.containsKey(entryOptionCombination.getKey())) continue;
            insertNewVariant(event.id(), event.comparePrice(), event.regularPrice(), entryOptionCombination.getValue());
        }

        for (var entryVariantProduct : mapOldVariantProduct.entrySet()) {
            if (mapOptionCombinations.containsKey(entryVariantProduct.getKey())) continue;
            entryVariantProduct.getValue().setDelete(true);
            var result = inventoryRepository.save(entryVariantProduct.getValue());
            log.info("Delete inventory for product {} with options {}", event.id(), result);
        }
        variantProductRedisManage.delete(event.id());
    }

    private String generateSku(String productId, Map<OptionType, String> options) {
        if (options == null || options.isEmpty()) return "";
        StringBuilder sku = new StringBuilder("SKU").append("-").append(productId);
        // Add option values to SKU
        options.forEach((key, value) -> sku.append("-").append(key).append("-").append(value));
        return sku.toString().toUpperCase();
    }

    private List<Map<OptionType, String>> generateOptionCombinations(List<DataVariantEvent.Option> options) {
        List<Map<OptionType, String>> result = new ArrayList<>();

        // Start with an empty combination
        Map<OptionType, String> emptyCombination = new HashMap<>();
        generateCombinationsRecursive(options, 0, emptyCombination, result);

        return result;
    }

    private void generateCombinationsRecursive(List<DataVariantEvent.Option> options,
                                               int optionIndex,
                                               Map<OptionType, String> currentCombination,
                                               List<Map<OptionType, String>> result) {

        // If we've processed all options, add the current combination to the result
        if (optionIndex >= options.size()) {
            result.add(new HashMap<>(currentCombination));
            return;
        }

        // Get the current option
        DataVariantEvent.Option currentOption = options.get(optionIndex);
        OptionType optionType = currentOption.type();
        List<String> values = currentOption.values();

        // For each value of the current option, create a new combination
        for (String value : values) {
            currentCombination.put(optionType, value);
            generateCombinationsRecursive(options, optionIndex + 1, currentCombination, result);
        }

        // Remove the current option to backtrack
        currentCombination.remove(optionType);
    }

    private String generateTitleVariant(Map<OptionType, String> options) {
        return options.values().stream().reduce("", (s, s2) -> s + "-" + s2).substring(1);
    }

    private void insertNewVariant(String id, double comparePrice, double regularPrice, Map<OptionType, String> options) {
        VariantProduct inventory = VariantProduct.builder()
                .productId(id)
                .comparePrice(comparePrice)
                .regularPrice(regularPrice)
                .options(options)
                .title(generateTitleVariant(options))
                .sku(generateSku(id, options))
                .build();
        inventoryRepository.save(inventory);
        log.info("Created inventory for product {} with options {}", id, options);
    }

    @Override
    public boolean existsVariantProductId(String variantId) {
        return variantRedisManage
                .get(variantId)
                .or(() -> variantRedisManage.cache(variantId, variantId, () -> inventoryRepository.findById(variantId)))
                .isPresent();
    }

    @Override
    public String getProductId(String variantId) {
        return variantRedisManage
                .get(variantId)
                .or(() -> variantRedisManage.cache(variantId, variantId, () -> inventoryRepository.findById(variantId)))
                .map(VariantProduct::getProductId).orElseThrow(() -> ApplicationException.createException(ExceptionEnum.PRODUCT_NOT_FOUND));
    }

    @Override
    public VariantProduct getVariantProductById(String variantId) {
        return variantRedisManage
                .get(variantId)
                .or(() -> variantRedisManage.cache(variantId, variantId, () -> inventoryRepository.findById(variantId)))
                .orElseThrow(() -> ApplicationException.createException(ExceptionEnum.VARIANT_PRODUCT_NOT_FOUND));
    }

    @Override
    public List<VariantProduct> updateVariantProducts(Map<String, Integer> quantities) {
        var result = new ArrayList<VariantProduct>(quantities.size());
        var listTask = new ArrayList<CompletableFuture<Void>>();
        quantities.forEach((key, value) -> listTask.add(CompletableFuture.runAsync(() -> {
            var variant = getVariantProductById(key);
            if (variant == null || variant.getQuantity() < value) return;
            variant.setQuantity(variant.getQuantity() + value);
            variant = inventoryRepository.save(variant);
            variantRedisManage.delete(key);
            result.add(variant);
        })));
        CompletableFuture.allOf(listTask.toArray(CompletableFuture[]::new)).join();
        return result;
    }

    @Override
    public Map<String, VariantAndInventoryInfo> getVariantAndInventoryInfo(List<String> productIds) {
        var aggregation = Aggregation.newAggregation(
                Aggregation.group("product_id")
                        .count().as("totalVariants")
                        .sum("quantity").as("totalInventories"),
                Aggregation.project("totalVariants", "totalInventories")
                        .and("_id").as("productId")
                        .andExclude("_id")
        );

        var result = mongoTemplate.aggregate(aggregation, VariantProduct.class, VariantAndInventoryInfoDto.class).getMappedResults();
        return result.stream().collect(Collectors.toMap(VariantAndInventoryInfoDto::getProductId, inventoryMapper::toVariantAndInventoryInfo));
    }
}
