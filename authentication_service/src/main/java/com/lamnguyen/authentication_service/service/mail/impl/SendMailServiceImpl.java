/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 1:54 PM - 12/03/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.authentication_service.service.mail.impl;

import com.lamnguyen.authentication_service.event.SendMailVerifyEvent;
import com.lamnguyen.authentication_service.service.mail.ISendMailService;
import com.lamnguyen.authentication_service.util.enums.MailType;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class SendMailServiceImpl implements ISendMailService {
	KafkaTemplate<String, SendMailVerifyEvent> template;

	@Override
	public void sendMailVerifyAccountCode(String to, String opt) {
		sendMailVerify(to, opt, MailType.VERIFY_ACCOUNT);
	}

	@Override
	public void sendMailResetPasswordCode(String to, String opt) {
		sendMailVerify(to, opt, MailType.RESET_PASSWORD);
	}

	@Override
	public void sendMailChangePasswordCode(String to, String opt) {
		sendMailVerify(to, opt, MailType.CHANGE_PASSWORD);
	}

	private void sendMailVerify(String to, String otp, MailType type) {
		template.send("send-mail-verify", SendMailVerifyEvent.builder()
				.email(to)
				.otp(otp)
				.type(type)
				.build());
	}
}
