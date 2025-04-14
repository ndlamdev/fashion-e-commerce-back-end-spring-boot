/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 11:34 AM - 25/08/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.profile_service.utils.validation;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

public class MyPhoneNumberUtil {
    public static boolean isPhoneNumberValid(String region, String phoneNumber) {
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        Phonenumber.PhoneNumber phone = null;
        try {
            var ignored = Long.parseLong(phoneNumber.substring(1));
            phone = phoneUtil.parse(phoneNumber, region);
        } catch (NumberParseException e) {
            return false;
        }
        return phoneUtil.isValidNumber(phone);
    }

    public static String formatPhoneNumber(String region, String phoneNumber) {
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        Phonenumber.PhoneNumber phone = null;
        try {
            phone = phoneUtil.parse(phoneNumber, region);
        } catch (NumberParseException e) {
            throw new RuntimeException(e);
        }
        return phoneUtil.format(phone, PhoneNumberUtil.PhoneNumberFormat.E164);
    }
}
