/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 2:04 PM - 07/04/2025
 * User: kimin
 **/

package com.lamnguyen.inventory_service.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@FieldDefaults(level = AccessLevel.PROTECTED)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class ApiResponse<T> {
	int code;
}
