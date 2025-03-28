/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 1:54 PM - 12/03/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.fashion_e_commerce.service.mail;

import brevo.ApiException;
import brevoApi.TransactionalEmailsApi;
import brevoModel.SendSmtpEmail;
import brevoModel.SendSmtpEmailSender;
import brevoModel.SendSmtpEmailTo;
import com.lamnguyen.fashion_e_commerce.util.enums.BrevoTemplate;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class SendMailServiceImpl implements ISendMailService{
    TransactionalEmailsApi transactionalEmailsApi;
    SendSmtpEmailSender sender;

    @Override
    @Async
    public void sendMailVerifyAccountCode(String to, String opt) {
        var map = new HashMap<String, Object>();
        map.put("code", opt);
        sent(to, map, BrevoTemplate.VERITY);
    }

    @Override
    @Async
    public void sendMailResetPasswordCode(String to, String opt) {
        var map = new HashMap<String, Object>();
        map.put("code", opt);
        sent(to, map, BrevoTemplate.RESET_PASSWORD);
    }


    public void sent(String to, Map<String, Object> params, BrevoTemplate template) {
        SendSmtpEmail sendSmtpEmail = new SendSmtpEmail();
        SendSmtpEmailTo recipient = new SendSmtpEmailTo();
        recipient.setEmail(to);
        sendSmtpEmail.setSender(sender);
        sendSmtpEmail.setTo(List.of(recipient));
        sendSmtpEmail.setTemplateId(template.getId());
        sendSmtpEmail.setParams(params);
//        try {
//            transactionalEmailsApi.sendTransacEmail(sendSmtpEmail);
//        } catch (ApiException e) {
//            log.error("Error sending email", e);
//        }
    }
}
