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
import com.lamnguyen.product_service.domain.dto.CollectionDto;
import com.lamnguyen.product_service.mapper.ICollectionMapper;
import com.lamnguyen.product_service.repository.ICollectionRepository;
import com.lamnguyen.product_service.service.business.ICollectionService;
import com.lamnguyen.product_service.service.redis.ICollectionRedisManager;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CollectionServiceImpl implements ICollectionService {
	ICollectionRepository collectionRepository;
	ICollectionMapper collectionMapper;
	ICollectionRedisManager manager;

	@Override
	public CollectionDto findById(String id) {
		return manager.get(id)
				.orElseGet(() -> findInDb(id).orElseThrow(() -> ApplicationException.createException(ExceptionEnum.COLLECTION_NOT_FOUND)));
	}

	private Optional<CollectionDto> findInDb(String id) {
		return manager.cache(id, id, id, s -> collectionRepository.findByIdAndLockIsFalse(s).map(collectionMapper::toCollectionDto));
	}
}
