/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 5:10 PM - 26/02/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.authentication_service.service.business.user.v1;

import com.lamnguyen.authentication_service.event.SaveUserDetailEvent;
import com.lamnguyen.authentication_service.service.business.user.IUserDetailService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service("CustomUserDetailServiceImpl")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserDetailServiceImpl implements IUserDetailService {
	KafkaTemplate<String, SaveUserDetailEvent> template;

	@Override
	public void save(SaveUserDetailEvent user) {
		template.send("save-user-detail", user);
	}
}
