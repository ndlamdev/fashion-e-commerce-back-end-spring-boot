/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 6:14 PM - 07/04/2025
 * User: kimin
 **/

package com.lamnguyen.product_service.model;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Discount implements Serializable {
	Integer percent;
	LocalDateTime start;
	LocalDateTime end;
}
