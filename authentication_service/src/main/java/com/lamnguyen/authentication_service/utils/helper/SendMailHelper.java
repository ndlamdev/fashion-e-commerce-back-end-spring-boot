/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 8:19 AM - 21/04/2025
 * User: kimin
 **/

package com.lamnguyen.authentication_service.utils.helper;

import com.lamnguyen.authentication_service.service.mail.ISendMailService;
import com.lamnguyen.authentication_service.service.redis.IVerifyCodeRedisManager;

public class SendMailHelper {
	public static void sendMailVerify(IVerifyCodeRedisManager tokenManager, ISendMailService sendMailService, long userId, String email) {
		String opt = OtpUtil.generate(6);
		tokenManager.setCode(userId, opt);
		sendMailService.sendMailVerifyAccountCode(email, opt);
	}
}
