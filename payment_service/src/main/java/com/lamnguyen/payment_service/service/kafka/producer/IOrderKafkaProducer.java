/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 3:01 PM-23/05/2025
 * User: kimin
 **/

package com.lamnguyen.payment_service.service.kafka.producer;

public interface IOrderKafkaProducer {
	void removeOrder(long orderId);
}
