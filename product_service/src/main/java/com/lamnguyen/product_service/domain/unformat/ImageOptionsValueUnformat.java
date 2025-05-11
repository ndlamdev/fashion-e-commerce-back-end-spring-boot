/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 12:42 PM - 09/04/2025
 * User: kimin
 **/

package com.lamnguyen.product_service.domain.unformat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lamnguyen.product_service.utils.enums.OptionType;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ImageOptionsValueUnformat(
		String title,

		OptionType type,

		List<CreateOptionItemUnformat> options
) implements Serializable {
}
