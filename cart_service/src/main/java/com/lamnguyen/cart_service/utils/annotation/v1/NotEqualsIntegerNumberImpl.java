/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 7:12 AM-08/05/2025
 * User: kimin
 **/

package com.lamnguyen.cart_service.utils.annotation.v1;

import com.lamnguyen.cart_service.utils.annotation.NotEqualsIntegerNumber;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotEqualsIntegerNumberImpl implements ConstraintValidator<NotEqualsIntegerNumber, Integer> {
	int compareValue;

	@Override
	public boolean isValid(Integer value, ConstraintValidatorContext context) {
		if (value == null) return true;
		return !value.equals(compareValue);
	}

	@Override
	public void initialize(NotEqualsIntegerNumber constraintAnnotation) {
		compareValue = constraintAnnotation.valueCompare();
	}
}
