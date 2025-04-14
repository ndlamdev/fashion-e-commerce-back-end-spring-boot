/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:17 AM - 14/04/2025
 * User: kimin
 **/

package com.lamnguyen.product_service.utils.annotation;


import com.lamnguyen.product_service.utils.annotation.impl.CompareAmountImageOptionsValueImpl;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = CompareAmountImageOptionsValueImpl.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CompareAmountImageOptionsValue {
	String message() default "Total value option not equals total image option value or image option not contain in option";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
