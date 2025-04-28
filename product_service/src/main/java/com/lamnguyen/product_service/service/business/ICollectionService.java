/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 12:53 PM - 09/04/2025
 * User: kimin
 **/

package com.lamnguyen.product_service.service.business;

import com.lamnguyen.product_service.domain.dto.CollectionSaveRedisDto;

public interface ICollectionService {
	CollectionSaveRedisDto findById(String id);
}
