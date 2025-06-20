/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 1:53 PM - 12/03/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.authentication_service.service.mail;

public interface ISendMailService {
	void sendMailVerifyAccountCode(String to, String opt);

	void sendMailResetPasswordCode(String to, String opt);

	void sendMailChangePasswordCode(String to, String opt);
}
