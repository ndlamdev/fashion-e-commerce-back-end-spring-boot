/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 5:10 PM - 26/02/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.authentication_service.service.business.user.v1;

import com.lamnguyen.authentication_service.event.SaveProfileUserEvent;
import com.lamnguyen.authentication_service.event.UpdateAvatarUserEvent;
import com.lamnguyen.authentication_service.service.business.user.IProfileUserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service("CustomUserDetailServiceImpl")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ProfileUserServiceImpl implements IProfileUserService {
    KafkaTemplate<String, Object> template;

    @Override
    public void save(SaveProfileUserEvent user) {
        template.send("save-profile", user);
    }

    @Override
    public void updateAvatar(UpdateAvatarUserEvent event) {
        template.send("update-avatar-user", event);
    }


}
