package com.lamnguyen.product_service.model;

import com.lamnguyen.product_service.utils.enums.OptionType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Getter
@Setter
public class ImageOptionsValue {
	String title;

	OptionType type;

	List<OptionItem> options;
}