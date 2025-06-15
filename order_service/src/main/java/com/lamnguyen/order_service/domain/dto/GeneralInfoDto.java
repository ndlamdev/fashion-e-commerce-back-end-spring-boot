package com.lamnguyen.order_service.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public  class GeneralInfoDto {
	long userId;
	long totalOrder;
	double totalSpent;

	public GeneralInfoDto(long userId, long totalOrder, double totalSpent) {
		this.userId = userId;
		this.totalOrder = totalOrder;
		this.totalSpent = totalSpent;
	}
}