/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 4:39 PM - 07/04/2025
 * User: kimin
 **/

package com.lamnguyen.product_service.utils.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ProductTag {
	BEST_SELLER("Bán chạy"),
	WORTH_BUYING("Đáng mua"),
	CLEARANCE_SALE("Giảm giá thanh lí"),
	OUTLET("outlet"),
	SAVING_PACKS("Gói tiết kiệm"),
	BAN_CHAY("Bán chạy"),
	DANG_MUA("Đáng mua"),
	NEW("Mới"),
	LIMITED("Giới hạn"),
	HOT("Nóng");

	String message;

	ProductTag(String message) {
		this.message = message;
	}
}
