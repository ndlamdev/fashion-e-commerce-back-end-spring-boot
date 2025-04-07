package com.lamnguyen.product_service.model;

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
public class OptionItem {
	String title; // Xanh nhạt
	String slug; // xanh-nhat
	String label; // ""
	List<Image> image; // Danh sách các hình đại diện cho màu đó.
}