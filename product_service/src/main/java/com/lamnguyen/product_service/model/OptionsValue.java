package com.lamnguyen.product_service.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;

@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Getter
@Setter
public class OptionsValue {
	String title; // Màu sắc

	@DBRef
	Option option;

	@DBRef
	List<OptionItem> optionItems;
}