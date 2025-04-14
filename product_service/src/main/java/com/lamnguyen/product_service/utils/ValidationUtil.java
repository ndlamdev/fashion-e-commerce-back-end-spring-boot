package com.lamnguyen.product_service.utils;

import com.lamnguyen.product_service.config.exception.ApplicationException;
import com.lamnguyen.product_service.config.exception.ExceptionEnum;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ValidationUtil {

	private final Validator validator;

	public ValidationUtil(Validator validator) {
		this.validator = validator;
	}

	public <T> void validate(T object) {
		Set<ConstraintViolation<T>> violations = validator.validate(object);

		if (!violations.isEmpty()) {
			String errors = violations.stream()
					.map(v -> v.getPropertyPath() + " " + v.getMessage())
					.collect(Collectors.joining("; "));
			throw ApplicationException.createException(ExceptionEnum.UNVALIDATED, "Validation error: " + errors);
		}
	}
}