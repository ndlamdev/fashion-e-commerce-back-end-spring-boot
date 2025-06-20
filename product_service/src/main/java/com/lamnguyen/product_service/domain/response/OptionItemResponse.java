package com.lamnguyen.product_service.domain.response;

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
public class OptionItemResponse implements Serializable {
	String title; // Xanh nhạt
	String slug; // xanh-nhat
	String label;
	List<ImageResponse> images;
}