/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 2:24 PM - 04/04/2025
 * User: kimin
 **/

package com.lamnguyen.authentication_service.event;

import com.lamnguyen.authentication_service.utils.enums.MailType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SendMailVerifyEvent {
    String email;
    String otp;
    MailType type;
}
