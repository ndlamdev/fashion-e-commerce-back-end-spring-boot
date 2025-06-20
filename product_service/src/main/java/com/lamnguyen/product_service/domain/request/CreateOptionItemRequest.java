package com.lamnguyen.product_service.domain.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateOptionItemRequest {
	String title; // Xanh nhạt
	String slug; // xanh-nhat
	String label;
	List<String> images;
}