/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 7:10 AM-08/05/2025
 * User: kimin
 **/
package com.lamnguyen.cart_service.utils.annotation;

import com.lamnguyen.cart_service.utils.annotation.v1.NotEqualsIntegerNumberImpl;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(NotEqualsIntegerNumber.List.class)
@Documented
@Constraint(
		validatedBy = NotEqualsIntegerNumberImpl.class
)
public @interface NotEqualsIntegerNumber {
	String message() default "Not equals";

	int valueCompare() default 0;

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	@interface List {
		NotEqualsIntegerNumber[] value();
	}
}
