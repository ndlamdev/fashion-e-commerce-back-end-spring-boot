/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 10:19 AM - 14/04/2025
 * User: kimin
 **/

package com.lamnguyen.product_service.utils.annotation.impl;

import com.lamnguyen.product_service.model.Product;
import com.lamnguyen.product_service.utils.annotation.CompareAmountImageOptionsValue;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CompareAmountImageOptionsValueImpl implements ConstraintValidator<CompareAmountImageOptionsValue, Product> {

	@Override
	public boolean isValid(Product value, ConstraintValidatorContext context) {
		if (value.getOptionsValues() == null || value.getOptionsValues().isEmpty() || value.getOptions() == null || value.getOptions().isEmpty())
			return true;
		for (var option : value.getOptions()) {
			for (var optionsValue : value.getOptionsValues()) {
				if (option.getType().equals(optionsValue.getOption())) {
					if (optionsValue.getOptions() == null || optionsValue.getOptions().isEmpty()) continue;
					if (option.getValues().size() != optionsValue.getOptions().size()) return false;
					for (var valueInOption : optionsValue.getOptions()) {
						if (!option.getValues().contains(valueInOption.getTitle())) return false;
					}
				}
			}
		}
		return true;
	}
}
