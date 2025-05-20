package com.lamnguyen.product_service.domain.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductFilterResponse {
	private String product;
	private String color;
	private String size;
	private Integer minPrice;
	private Integer maxPrice;
	private Integer price;
}