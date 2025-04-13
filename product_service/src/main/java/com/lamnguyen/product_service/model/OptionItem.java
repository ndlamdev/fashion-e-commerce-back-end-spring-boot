package com.lamnguyen.product_service.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@FieldDefaults(level = AccessLevel.PROTECTED)
@RequiredArgsConstructor
@Getter
@Setter
public class OptionItem {
	String title; // Xanh nhạt
	String slug; // xanh-nhat
	List<Image> images; // Danh sách các hình đại diện cho màu đó.

	@SuperBuilder
	@FieldDefaults(level = AccessLevel.PRIVATE)
	@RequiredArgsConstructor
	@Getter
	@Setter
	public static class OptionItemImage extends OptionItem {
		Image display; // Hình ảnh để làm hình option.
	}

	@SuperBuilder
	@FieldDefaults(level = AccessLevel.PRIVATE)
	@RequiredArgsConstructor
	@Getter
	@Setter
	public static class OptionItemTitle extends OptionItem {
		String display; // Title để làm hình option.
	}
}