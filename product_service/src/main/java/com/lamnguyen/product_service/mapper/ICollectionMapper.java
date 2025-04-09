/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 1:00 PM - 09/04/2025
 * User: kimin
 **/

package com.lamnguyen.product_service.mapper;

import com.lamnguyen.product_service.domain.request.TitleCollectionRequest;
import com.lamnguyen.product_service.domain.request.UpdateCollectionRequest;
import com.lamnguyen.product_service.model.Collection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ICollectionMapper {
	Collection toCollection(TitleCollectionRequest request);

	Collection toCollection(UpdateCollectionRequest request);
}
