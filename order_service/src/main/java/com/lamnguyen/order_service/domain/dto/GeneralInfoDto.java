package com.lamnguyen.order_service.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public  class GeneralInfoDto {
	long userId;
	long totalOrder;
	long totalSpent;

	public GeneralInfoDto(long userId, long totalOrder, long totalSpent) {
		this.userId = userId;
		this.totalOrder = totalOrder;
		this.totalSpent = totalSpent;
	}
}