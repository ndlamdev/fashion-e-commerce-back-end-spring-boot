/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 11:23 AM-24/04/2025
 * User: kimin
 **/

package com.lamnguyen.profile_service.service.kafka.consumer;

import com.lamnguyen.profile_service.message.SaveProfileUserMessage;
import com.lamnguyen.profile_service.message.UpdateAvatarUserMessage;
import org.apache.kafka.common.errors.ApiException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;

public interface IProfileConsumer {
	@KafkaListener(topics = "save-profile", groupId = "profile-user")
	@RetryableTopic(
			backoff = @Backoff(value = 3000L),
			attempts = "5",
			include = ApiException.class)
	void saveUserDetail(SaveProfileUserMessage message);

	@KafkaListener(topics = "update-avatar-user", groupId = "profile-user")
	@RetryableTopic(
			backoff = @Backoff(value = 3000L),
			attempts = "5",
			include = ApiException.class)
	void updateAvatarUser(UpdateAvatarUserMessage message);
}
