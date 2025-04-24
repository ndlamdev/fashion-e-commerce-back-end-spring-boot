/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:38 AM-24/04/2025
 * User: kimin
 **/

package com.lamnguyen.product_service.mapper;

import com.lamnguyen.product_service.event.CreateVariantEvent;
import com.lamnguyen.product_service.model.Option;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IOptionMapper {
	CreateVariantEvent.Option toCreateVariantOption(Option option);

	List<CreateVariantEvent.Option> toCreateVariantOptions(List<Option> options);
}
