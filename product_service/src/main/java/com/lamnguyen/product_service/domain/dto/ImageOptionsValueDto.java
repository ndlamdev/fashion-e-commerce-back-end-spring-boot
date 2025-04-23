package com.lamnguyen.product_service.domain.dto;

import com.lamnguyen.product_service.utils.enums.OptionType;
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

	OptionType option;

	List<OptionItemDto> options;
}