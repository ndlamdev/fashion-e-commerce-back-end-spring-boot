/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 2:06 PM - 04/04/2025
 * User: kimin
 **/

package com.lamnguyen.notification_service.message;

import com.lamnguyen.notification_service.utils.enums.MailType;

public record SendMailVerifyMessage(
		String email,
		String otp,
		MailType type
) {
}
