/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 1:54 PM - 12/03/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.notification_service.service.impl;

import brevo.ApiException;
import brevoApi.TransactionalEmailsApi;
import brevoModel.SendSmtpEmail;
import brevoModel.SendSmtpEmailSender;
import brevoModel.SendSmtpEmailTo;
import com.lamnguyen.notification_service.message.SendMailVerifyMessage;
import com.lamnguyen.notification_service.service.ISendMailService;
import com.lamnguyen.notification_service.utils.enums.BrevoTemplate;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class SendMailServiceImpl implements ISendMailService {
	TransactionalEmailsApi transactionalEmailsApi;
	SendSmtpEmailSender sender;

	@Override
	@KafkaListener(topics = "send-mail-verify")
	public void sendMailVerity(SendMailVerifyMessage message) {
		log.info("Send mail have body: {}", message);
		BrevoTemplate template = switch (message.type()) {
			case VERIFY_ACCOUNT -> BrevoTemplate.VERITY_ACCOUNT;
			case RESET_PASSWORD -> BrevoTemplate.RESET_PASSWORD;
			case CHANGE_PASSWORD -> BrevoTemplate.CHANGE_PASSWORD;
		};

		sentOtp(message.email(), message.otp(), template);
	}

	public void sentOtp(String to, String opt, BrevoTemplate template) {
		var map = new HashMap<String, Object>();
		map.put("code", opt);

		SendSmtpEmail sendSmtpEmail = new SendSmtpEmail();
		SendSmtpEmailTo recipient = new SendSmtpEmailTo();
		recipient.setEmail(to);
		sendSmtpEmail.setSender(sender);
		sendSmtpEmail.setTo(List.of(recipient));
		sendSmtpEmail.setTemplateId(template.getId());
		sendSmtpEmail.setParams(map);
		try {
			transactionalEmailsApi.sendTransacEmail(sendSmtpEmail);
		} catch (Exception e) {
			log.error("Error sending email", e);
		}
	}
}
