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
import com.lamnguyen.inventory_service.mapper.IInventoryMapper;
import com.lamnguyen.inventory_service.message.CreateVariantEvent;
import com.lamnguyen.inventory_service.model.VariantProduct;
import com.lamnguyen.inventory_service.repository.InventoryRepository;
import com.lamnguyen.inventory_service.service.business.IInventoryService;
import com.lamnguyen.inventory_service.service.redis.IVariantProductRedisManage;
import com.lamnguyen.inventory_service.utils.enums.OptionType;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ApiException;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Implementation of the inventory management service
 */
@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InventoryServiceImpl implements IInventoryService {
	InventoryRepository inventoryRepository;
	IVariantProductRedisManage variantProductRedisManage;

	/**
	 * Process a CreateVariantEvent to create inventory records
	 *
	 * @param event the CreateVariantEvent
	 */
	public void createVariantProduct(CreateVariantEvent event) {
		String productId = event.id();
		List<CreateVariantEvent.Option> options = event.options();

		// Generate all possible combinations of options
		List<Map<OptionType, String>> optionCombinations = generateOptionCombinations(options);

		// Create inventory records for each combination
		optionCombinations.forEach(optionCombination -> {
			// Check if inventory already exists
			inventoryRepository.findByProductIdAndOptions(productId, optionCombination)
					.ifPresentOrElse(
							// If exists, log it
							inventory -> log.info("VariantProduct already exists for product {} with options {}", productId, optionCombination),
							// If not exists, create it
							() -> {
								VariantProduct inventory = VariantProduct.builder()
										.productId(event.id())
										.comparePrice(event.comparePrice())
										.regularPrice(event.regularPrice())
										.options(optionCombination)
										.title(optionCombination.values().stream().reduce("", (s, s2) -> s + "-" + s2).substring(1))
										.sku(generateSku(productId, optionCombination))
										.build();
								inventoryRepository.save(inventory);
								log.info("Created inventory for product {} with options {}", productId, optionCombination);
							}
					);
		});

	}

	/**
	 * Generate all possible combinations of options
	 *
	 * @param options the list of options
	 * @return list of all possible combinations
	 */
	private List<Map<OptionType, String>> generateOptionCombinations(List<CreateVariantEvent.Option> options) {
		List<Map<OptionType, String>> result = new ArrayList<>();

		// Start with an empty combination
		Map<OptionType, String> emptyCombination = new HashMap<>();
		generateCombinationsRecursive(options, 0, emptyCombination, result);

		return result;
	}

	/**
	 * Recursive helper method to generate all possible combinations of options
	 *
	 * @param options            the list of options
	 * @param optionIndex        the current option index
	 * @param currentCombination the current combination being built
	 * @param result             the list to store all combinations
	 */
	private void generateCombinationsRecursive(
			List<CreateVariantEvent.Option> options,
			int optionIndex,
			Map<OptionType, String> currentCombination,
			List<Map<OptionType, String>> result) {

		// If we've processed all options, add the current combination to the result
		if (optionIndex >= options.size()) {
			result.add(new HashMap<>(currentCombination));
			return;
		}

		// Get the current option
		CreateVariantEvent.Option currentOption = options.get(optionIndex);
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

	/**
	 * Update inventory quantity
	 *
	 * @param productId the product ID
	 * @param options   the options map
	 * @param quantity  the new quantity
	 * @return true if updated, false if not found
	 */
	public boolean updateInventoryQuantity(String productId, Map<OptionType, String> options, int quantity) {
		return inventoryRepository.findByProductIdAndOptions(productId, options)
				.map(inventory -> {
					inventoryRepository.save(inventory);
					log.info("Updated inventory quantity for product {} with options {} to {}", productId, options, quantity);
					return true;
				})
				.orElse(false);
	}

	/**
	 * Get available inventory for a product
	 *
	 * @param productId the product ID
	 * @return list of available inventories
	 */
	public List<VariantProduct> getAvailableInventory(String productId) {
		var data = getAllInventory(productId);
		return data.stream().filter(VariantProduct::isAvailable).toList();
	}

	/**
	 * Get all inventory for a product
	 *
	 * @param productId the product ID
	 * @return list of all inventories
	 */
	public List<VariantProduct> getAllInventory(String productId) {
		return Arrays.stream(variantProductRedisManage
				.get(productId)
				.or(() -> variantProductRedisManage.cache(productId, () -> Optional.of(inventoryRepository.findByProductId(productId).toArray(VariantProduct[]::new))))
				.orElseGet(() -> new VariantProduct[0])).toList();
	}

	/**
	 * Generate a SKU based on product ID and options
	 */
	private String generateSku(String productId, Map<OptionType, String> options) {
		if (options == null || options.isEmpty()) return "";
		StringBuilder sku = new StringBuilder("SKU").append("-").append(productId);
		// Add option values to SKU
		options.forEach((key, value) -> sku.append("-").append(key).append("-").append(value));
		return sku.toString().toUpperCase();
	}
}
