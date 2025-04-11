package com.lamnguyen.profile_service.service.consumer;

import com.lamnguyen.profile_service.mapper.ICustomerMapper;
import com.lamnguyen.profile_service.message.SaveUserDetailMessage;
import com.lamnguyen.profile_service.repository.ICustomerRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomerConsumer {
    ICustomerRepository customerRepository;
    ICustomerMapper mapper;

    @KafkaListener(topics = "save-user-detail", groupId = "authenticationGroup")
    public void saveUserDetail(SaveUserDetailMessage message) {
        log.info("Consuming the message from save-user-detail Topic:: {}", message);
        customerRepository.save(mapper.toCustomer(message));
    }
}
