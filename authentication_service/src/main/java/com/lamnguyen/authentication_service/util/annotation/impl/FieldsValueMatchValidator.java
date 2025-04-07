/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 5:34 PM - 27/02/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.authentication_service.util.annotation.impl;

import com.lamnguyen.authentication_service.util.annotation.FieldsValueMatch;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

import java.util.Objects;

public class FieldsValueMatchValidator implements ConstraintValidator<FieldsValueMatch, Object> {
    private String field;
    private String fieldMatch;

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        var bean = new BeanWrapperImpl(value);
        var value1 = bean.getPropertyValue(field);
        var value2 = bean.getPropertyValue(fieldMatch);
        return Objects.equals(value1, value2);
    }

    @Override
    public void initialize(FieldsValueMatch constraintAnnotation) {
        this.field = constraintAnnotation.field();
        this.fieldMatch = constraintAnnotation.fieldMatch();
    }
}
