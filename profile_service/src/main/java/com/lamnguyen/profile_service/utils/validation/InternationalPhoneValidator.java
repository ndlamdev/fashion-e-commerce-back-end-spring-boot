package com.lamnguyen.profile_service.utils.validation;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.lamnguyen.profile_service.utils.annotation.ValidInternationalPhone;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Field;

public class InternationalPhoneValidator implements ConstraintValidator<ValidInternationalPhone, Object> {

    private String countryFieldName;
    private String phoneFieldName;

    @Override
    public void initialize(ValidInternationalPhone constraintAnnotation) {
        this.countryFieldName = constraintAnnotation.countryField();
        this.phoneFieldName = constraintAnnotation.phoneField();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        try {
            // Dùng reflection để lấy giá trị các field
            Field countryField = value.getClass().getDeclaredField(countryFieldName);
            Field phoneField = value.getClass().getDeclaredField(phoneFieldName);

            countryField.setAccessible(true);
            phoneField.setAccessible(true);

            Object countryObj = countryField.get(value);
            Object phoneObj = phoneField.get(value);

            if (countryObj == null || phoneObj == null) return false;

            String country = countryObj.toString();
            String phone = phoneObj.toString();

            boolean re =  MyPhoneNumberUtil.isPhoneNumberValid(country, phone);
            return re;
        } catch (Exception e) {
            return false;
        }
    }
}
