/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 6:22 PM-19/05/2025
 * User: kimin
 **/

package com.lamnguyen.order_service.event;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class DeleteCartItemsEvent {
	long userId;
	List<String> variantIds;
}
