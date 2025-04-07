/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 2:24 PM - 04/04/2025
 * User: kimin
 **/

package com.lamnguyen.authentication_service.event;

import com.lamnguyen.authentication_service.util.enums.MailType;
import lombok.Builder;

@Builder
public record SendMailVerifyEvent(String email,
                                  String otp,
                                  MailType type) {
}
