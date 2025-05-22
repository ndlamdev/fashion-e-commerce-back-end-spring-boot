/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 2:18 PM-18/05/2025
 * User: kimin
 **/

package com.lamnguyen.payment_service.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vn.payos.PayOS;

@Configuration
@Getter
@Setter
@ConfigurationProperties(prefix = "application.payos")
public class PayOsConfig {
	private String baseUrl;
	private String clientId;
	private String apiKey;
	private String checksumKey;

	@Bean
	public PayOS payOS() {
		return new PayOS(clientId, apiKey, checksumKey);
	}
}
