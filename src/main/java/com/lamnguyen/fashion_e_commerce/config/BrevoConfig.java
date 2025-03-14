/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 1:49 PM - 12/03/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.fashion_e_commerce.config;

import brevo.ApiClient;
import brevoApi.TransactionalEmailsApi;
import brevoModel.SendSmtpEmailSender;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BrevoConfig {
    @Value("${brevo.api-key}")
    String brevoApiKey;
    @Value("${brevo.email}")
    String email;
    @Value("${brevo.name}")
    String name;

    @Bean
    public TransactionalEmailsApi  transactionalEmailsApi(){
        ApiClient defaultClient = brevo.Configuration.getDefaultApiClient();
        defaultClient.setApiKey(brevoApiKey);
        return new TransactionalEmailsApi();
    }

    @Bean
    public SendSmtpEmailSender sendSmtpEmailSender() {
        SendSmtpEmailSender sender = new SendSmtpEmailSender();
        sender.setEmail(email);
        sender.setName(name);
        return sender;
    }
}
