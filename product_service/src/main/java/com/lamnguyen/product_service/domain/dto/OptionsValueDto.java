package com.lamnguyen.product_service.domain.dto;

import com.lamnguyen.product_service.model.OptionItem;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class OptionsValueDto {
	String title; // Màu sắc

	@Field("option_id")
	String optionId;

	@Field("option_item_ids")
	List<OptionItem> optionItems;
}