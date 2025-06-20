/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 1:53 PM - 12/03/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.notification_service.service.kafka;

import brevo.ApiException;
import com.lamnguyen.notification_service.message.SendMailVerifyMessage;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;

public interface ISendMailService {

	@KafkaListener(topics = "send-mail-verify")
	@RetryableTopic(
			backoff = @Backoff(value = 3000L),
			attempts = "5",
			include = ApiException.class)
	void sendMailVerity(SendMailVerifyMessage message);
}
