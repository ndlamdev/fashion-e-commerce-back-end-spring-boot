/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 12:52 PM - 09/04/2025
 * User: kimin
 **/

package com.lamnguyen.product_service.repository;

import com.lamnguyen.product_service.domain.dto.CollectionSaveRedisDto;
import com.lamnguyen.product_service.model.Collection;
import com.lamnguyen.product_service.utils.enums.CollectionType;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ICollectionRepository extends MongoRepository<Collection, String> {
	Optional<Collection> findByIdAndLockIsFalse(String id);

	List<CollectionSaveRedisDto> findAllByType(CollectionType type);
}
