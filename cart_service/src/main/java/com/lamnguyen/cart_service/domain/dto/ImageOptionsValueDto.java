package com.lamnguyen.cart_service.domain.dto;

import com.lamnguyen.cart_service.utils.enums.OptionType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.List;

@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImageOptionsValueDto implements Serializable {
	String title;

	OptionType type;

	List<OptionItemDto> options;
}