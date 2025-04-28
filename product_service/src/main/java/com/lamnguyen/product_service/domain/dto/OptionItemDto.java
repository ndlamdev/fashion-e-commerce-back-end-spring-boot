package com.lamnguyen.product_service.domain.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.List;

@Builder
@FieldDefaults(level = AccessLevel.PROTECTED)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OptionItemDto implements Serializable {
	String title; // Xanh nhạt
	String slug; // xanh-nhat
	String label;
	List<String> images;
}