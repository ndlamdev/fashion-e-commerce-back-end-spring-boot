/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 1:53 PM - 12/03/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.notification_service.service;

import com.lamnguyen.notification_service.message.SendMailVerifyMessage;

public interface ISendMailService {
	void sendMailVerity(SendMailVerifyMessage message);
}
