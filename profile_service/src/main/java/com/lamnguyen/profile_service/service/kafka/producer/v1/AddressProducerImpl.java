package com.lamnguyen.profile_service.service.kafka.producer.v1;

import com.lamnguyen.profile_service.event.InfoAddressShippingEvent;
import com.lamnguyen.profile_service.service.kafka.producer.IAddressProducer;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AddressProducerImpl implements IAddressProducer {
	KafkaTemplate<String, InfoAddressShippingEvent> kafkaTemplate;

	public void sendInfoAddressShipping(InfoAddressShippingEvent infoAddress) {
		log.info("Send info address shipping with json to info-address-shipping-topic {}", infoAddress);
		kafkaTemplate.send("info_address_shipping_topic", infoAddress);
	}
}
