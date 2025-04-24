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

public interface IProfileConsumer {
	void saveUserDetail(SaveProfileUserMessage message);

	void updateAvatarUser(UpdateAvatarUserMessage message);
}
