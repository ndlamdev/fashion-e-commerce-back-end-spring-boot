/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 8:58 AM - 13/04/2025
 * User: kimin
 **/

package com.lamnguyen.product_service.service.redis;

import com.lamnguyen.product_service.domain.dto.CollectionSaveRedisDto;
import com.lamnguyen.product_service.utils.enums.CollectionType;

import java.util.List;
import java.util.Map;

public interface ICollectionRedisManager extends ICacheManage<CollectionSaveRedisDto> {
	List<CollectionSaveRedisDto> getByCollectionType(CollectionType type);

	List<CollectionSaveRedisDto> cache(CollectionType type, CallbackDB<List<CollectionSaveRedisDto>> callDB);

	void deleteCollection(CollectionType type);

	void deleteCollections();
}
