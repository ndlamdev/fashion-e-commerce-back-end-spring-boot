/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:37 AM-21/05/2025
 * User: user
 **/

package com.lamnguyen.order_service.domain.response;

import com.lamnguyen.order_service.utils.enums.PaymentMethod;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PaymentResponse {
	PaymentMethod method;
	String returnUrl;
	String checkoutUrl;
}
