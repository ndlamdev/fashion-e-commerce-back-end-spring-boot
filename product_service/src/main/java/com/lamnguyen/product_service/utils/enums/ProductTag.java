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
	BEST_SELLER, WORTH_BUYING, CLEARANCE_SALE, OUTLET, SAVING_PACKS, NEW, LIMITED, HOT;
}
