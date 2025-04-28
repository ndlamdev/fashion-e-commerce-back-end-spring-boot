package com.lamnguyen.product_service.model;

import com.lamnguyen.product_service.utils.enums.OptionType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Getter
@Setter
public class Option {
	OptionType type; // size | color

	String title; // Màu  | Size

	Set<String> values; // Danh sách các giá trị của option này

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		Option option = (Option) o;
		return type == option.type;
	}

	@Override
	public int hashCode() {
		return Objects.hash(type, title, values);
	}
}