/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 12:50 PM - 09/04/2025
 * User: kimin
 **/

package com.lamnguyen.product_service.controller;

import com.lamnguyen.product_service.domain.dto.CollectionSaveRedisDto;
import com.lamnguyen.product_service.service.business.ICollectionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/collection/v1")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CollectionController {
	ICollectionService collectionService;


	@GetMapping("/{id}")
	CollectionSaveRedisDto getCollectionByCollectionId(@PathVariable("id") String id) {
		return collectionService.findById(id);
	}
}
