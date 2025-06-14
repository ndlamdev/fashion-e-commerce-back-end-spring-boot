/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 6:49 AM-14/06/2025
 * User: kimin
 **/

package com.lamnguyen.order_service.config;

import jakarta.annotation.PostConstruct;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.stereotype.Component;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;

@Component
public class EurekaClientConfig {
	private final EurekaInstanceConfigBean config;

	public EurekaClientConfig(EurekaInstanceConfigBean config) {
		this.config = config;
	}

	@PostConstruct
	public void customizeEurekaClientConfig() {
		String ip = getLocalIpAddress();
		String prefixPath = "/api/" + config.getAppname();
		String baseUrl = config.isNonSecurePortEnabled() ? "http://" + config.getIpAddress() + ":" + config.getNonSecurePort() : "https://" + config.getIpAddress() + ":" + config.getSecurePort();
		config.setIpAddress(ip);
		config.setPreferIpAddress(true);
		config.setStatusPageUrlPath(prefixPath + config.getStatusPageUrlPath());
		config.setStatusPageUrl(baseUrl + config.getStatusPageUrlPath());
		config.setHealthCheckUrlPath(prefixPath + config.getHealthCheckUrlPath());
		config.setHealthCheckUrl(baseUrl + config.getHealthCheckUrlPath());
	}

	private String getLocalIpAddress() {
		try {
			for (NetworkInterface ni : Collections.list(NetworkInterface.getNetworkInterfaces())) {
				for (InetAddress address : Collections.list(ni.getInetAddresses())) {
					if (!address.isLoopbackAddress() && address instanceof Inet4Address) {
						return address.getHostAddress();
					}
				}
			}
		} catch (SocketException e) {
			e.printStackTrace(System.err);
		}
		return "127.0.0.1"; // fallback
	}


}
