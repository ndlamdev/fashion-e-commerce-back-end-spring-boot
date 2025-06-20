/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 4:45 PM-02/05/2025
 * User: kimin
 **/

package com.lamnguyen.base_service.service.kafka.consumer.v1;

import com.lamnguyen.base_service.service.kafka.consumer.IBaseConsumer;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class BaseConsumerImpl implements IBaseConsumer {
	@Override
	public void processBaseMessage() {

	}
}
