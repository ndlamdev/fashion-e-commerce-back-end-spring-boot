package com.lamnguyen.product_service.domain.response;

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
public class ImageOptionsValueResponse implements Serializable {
	String title;

	OptionType option;

	List<OptionItemResponse> options;
}