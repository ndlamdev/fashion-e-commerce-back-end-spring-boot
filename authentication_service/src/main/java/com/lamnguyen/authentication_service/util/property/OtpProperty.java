/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 3:19 PM - 12/03/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.authentication_service.util.property;

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
        @Value("${application.token.otp.verify_account.key}")
        String Key;
        @Value("${application.token.otp.length}")
        int length;
        @Value("${application.token.otp.verify_account.expire}")
        int expire;
        @Value("${application.token.otp.verify_account.total_try.key}")
        String totalTryKey;
        @Value("${application.token.otp.verify_account.total_try.max_try}")
        int maxTry;
        @Value("${application.token.otp.verify_account.total_resend.key}")
        String totalResendKey;
        @Value("${application.token.otp.verify_account.total_resend.max_resend}")
        int maxResend;
        @Value("${application.token.otp.verify_account.total_resend.expire}")
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
        @Value("${application.token.otp.reset_password.total_try.max_try}")
        int maxTry;
        @Value("${application.token.otp.reset_password.total_resend.key}")
        String totalResendKey;
        @Value("${application.token.otp.reset_password.total_resend.max_resend}")
        int maxResend;
        @Value("${application.token.otp.reset_password.total_resend.expire}")
        int totalResendExpire;
        @Value("${application.token.otp.length}")
        int length;
    }

    @Component
    @FieldDefaults(level = AccessLevel.PRIVATE)
    @Getter
    @Setter
    public static class ChangePasswordVerification {
        @Value("${application.token.otp.change_password.key}")
        String key;
        @Value("${application.token.otp.change_password.expire}")
        int expire;
        @Value("${application.token.otp.change_password.total_try.key}")
        String totalTryKey;
        @Value("${application.token.otp.change_password.total_try.max_try}")
        int maxTry;
        @Value("${application.token.otp.change_password.total_resend.key}")
        String totalResendKey;
        @Value("${application.token.otp.change_password.total_resend.max_resend}")
        int maxResend;
        @Value("${application.token.otp.change_password.total_resend.expire}")
        int totalResendExpire;
        @Value("${application.token.otp.length}")
        int length;
    }
}
