/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 4:09 PM-23/05/2025
 * User: kimin
 **/

package com.lamnguyen.order_service.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lamnguyen.order_service.utils.enums.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class PaymentRequest {
	@NotNull
	PaymentMethod method;
	@JsonProperty("return_url")
	String returnUrl;
	@JsonProperty("cancel_url")
	String cancelUrl;
}
