package com.lamnguyen.profile_service.service.kafka.consumer.v1;

import com.lamnguyen.profile_service.config.exception.ApplicationException;
import com.lamnguyen.profile_service.config.exception.ExceptionEnum;
import com.lamnguyen.profile_service.mapper.ICustomerMapper;
import com.lamnguyen.profile_service.message.SaveProfileUserMessage;
import com.lamnguyen.profile_service.message.UpdateAvatarUserMessage;
import com.lamnguyen.profile_service.repository.ICustomerRepository;
import com.lamnguyen.profile_service.service.kafka.consumer.IProfileConsumer;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.Uuid;
import org.apache.kafka.common.errors.ApiException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProfileConsumerImpl implements IProfileConsumer {
    ICustomerRepository customerRepository;
    ICustomerMapper mapper;

    @Override
    public void saveUserDetail(SaveProfileUserMessage message) {
        log.info("Consuming the message from save-profile Topic: {}", message);
        createAnonymousAuthentication(message.getEmail());
        customerRepository.save(mapper.toCustomer(message));
    }

    private void createAnonymousAuthentication(String email) {
        var context = SecurityContextHolder.getContext();
        var roles = List.of(new SimpleGrantedAuthority("ROLE_ANONYMOUS"));
        context.setAuthentication(new AnonymousAuthenticationToken(Uuid.randomUuid().toString(), new User(email, "", roles), roles));
    }

    @Override
    public void updateAvatarUser(UpdateAvatarUserMessage message) {
        log.info("Consuming the message from update-avatar-user: {}", message);
        var profile = customerRepository.findByUserId(message.getUserId()).orElseThrow(() -> ApplicationException.createException(ExceptionEnum.USER_EXIST));
        createAnonymousAuthentication(profile.getEmail());
        if (profile.getAvatar() != null) return;
        profile.setAvatar(message.getAvatar());
        customerRepository.save(profile);
    }
}
