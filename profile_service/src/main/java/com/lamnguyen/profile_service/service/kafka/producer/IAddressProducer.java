/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 11:24 AM-24/04/2025
 * User: kimin
 **/

package com.lamnguyen.profile_service.service.kafka.producer;

import com.lamnguyen.profile_service.message.InfoAddressShipping;

public interface IAddressProducer {
	void sendInfoAddressShipping(InfoAddressShipping infoCustomer);
}
