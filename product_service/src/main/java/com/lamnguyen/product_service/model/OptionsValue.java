package com.lamnguyen.product_service.model;

import com.lamnguyen.product_service.utils.enums.DisplayOptionValueType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Getter
@Setter
public class OptionsValue {
	String title; // title để hiển thị kế bên các giá thị của option (ví dụ: màu sắc hoặc size)

	@Field("option_id")
	@DocumentReference(lazy = true)
	Option option; // Thuộc nhóm option nào

	@Field("option_item_ids")
	List<OptionItem> optionItems; // Danh sách các giá trị của option này

	@Field("display_option_type")
	DisplayOptionValueType displayOptionValueType;
}