/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 12:50 PM - 09/04/2025
 * User: kimin
 **/

package com.lamnguyen.product_service.controller.user;

import com.lamnguyen.product_service.domain.dto.CollectionSaveRedisDto;
import com.lamnguyen.product_service.domain.response.ProductResponse;
import com.lamnguyen.product_service.service.business.ICollectionService;
import com.lamnguyen.product_service.utils.enums.CollectionType;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/collection/v1")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CollectionController {
	ICollectionService collectionService;

	@GetMapping
	Map<CollectionType, List<CollectionSaveRedisDto>> getCollections() {
		return collectionService.getCollections();
	}

	@GetMapping("/{id}")
	CollectionSaveRedisDto getCollectionByCollectionId(@PathVariable("id") String id) {
		return collectionService.findById(id);
	}

	@GetMapping("/{id}/products")
	Page<ProductResponse> getAllProductByCollectionId(@PathVariable("id") String id, @PageableDefault(sort = {"_id"}, page = 0, size = 10, direction = Sort.Direction.ASC) Pageable pageable) {
		return collectionService.getProducts(id, pageable);
	}
}
