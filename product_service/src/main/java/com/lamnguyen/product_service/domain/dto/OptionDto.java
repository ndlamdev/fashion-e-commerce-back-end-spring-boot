/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:29 PM - 13/04/2025
 * User: kimin
 **/

package com.lamnguyen.product_service.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lamnguyen.product_service.utils.enums.OptionType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OptionDto implements Serializable {
	OptionType type; // size | color

	String title; // Màu  | Size

	List<String> values; // Danh sách các giá trị của option này
}
