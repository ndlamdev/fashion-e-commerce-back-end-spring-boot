/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 3:19 PM - 12/03/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.fashion_e_commerce.util.property;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


public class OtpProperty {
    @Component
    @FieldDefaults(level = AccessLevel.PRIVATE)
    @Getter
    @Setter
    public static class AccountVerification {
        @Value("${application.token.otp.verity_account.key}")
        String Key;
        @Value("${application.token.otp.verity_account.length}")
        int length;
        @Value("${application.token.otp.verity_account.expire}")
        int expire;
        @Value("${application.token.otp.verity_account.total_try.key}")
        String totalTryKey;
        @Value("${application.token.otp.verity_account.total_try.value}")
        int totalTryValue;
        @Value("${application.token.otp.verity_account.total_resend.key}")
        String totalResendKey;
        @Value("${application.token.otp.verity_account.total_resend.value}")
        int totalResendValue;
        @Value("${application.token.otp.verity_account.total_resend.expire}")
        int totalResendExpire;
    }

    @Component
    @FieldDefaults(level = AccessLevel.PRIVATE)
    @Getter
    @Setter
    public static class ResetPasswordVerification {
        @Value("${application.token.otp.reset_password.key}")
        String key;
        @Value("${application.token.otp.reset_password.expire}")
        int expire;
        @Value("${application.token.otp.reset_password.total_try.key}")
        String totalTryKey;
        @Value("${application.token.otp.reset_password.total_try.value}")
        int totalTryValue;
        @Value("${application.token.otp.reset_password.total_resend.key}")
        String totalResendKey;
        @Value("${application.token.otp.reset_password.total_resend.value}")
        int totalResendValue;
        @Value("${application.token.otp.reset_password.total_resend.expire}")
        int totalResendExpire;
    }
}
