package com.lamnguyen.profile_service.utils.annotation;

import com.lamnguyen.profile_service.utils.validation.InternationalPhoneValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = InternationalPhoneValidator.class)
@Documented
public @interface ValidInternationalPhone {
    String message() default "Invalid phone number for the country";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String countryField();
    String phoneField();
}
