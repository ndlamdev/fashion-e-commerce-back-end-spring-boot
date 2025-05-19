/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 4:01 PM-18/05/2025
 * User: kimin
 **/

package com.lamnguyen.payment_service.utils.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import vn.payos.type.PaymentData;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
@Getter
public class SignAndVerifyDataHelper {
	@Value("${application.payos.key.private-key}")
	@NonFinal
	String privateKey;

	@Value("${application.payos.key.public-key}")
	@NonFinal
	String publicKey;
	ObjectMapper objectMapper;


	private PrivateKey loadPrivateKey() throws Exception {
		byte[] decoded = Base64.getDecoder().decode(privateKey);

		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decoded);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		return keyFactory.generatePrivate(keySpec);
	}

	private PublicKey loadPublicKey() throws Exception {
		byte[] decoded = Base64.getDecoder().decode(publicKey);

		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decoded);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		return keyFactory.generatePublic(keySpec);
	}

	public String sign(PaymentData data) throws Exception {
		var message = objectMapper.writeValueAsString(data);
		Signature signature = Signature.getInstance("SHA256withRSA");
		signature.initSign(loadPrivateKey());
		signature.update(message.getBytes());
		return Base64.getEncoder().encodeToString(signature.sign());
	}

	public boolean verify(String data, String signatureBytes) throws Exception {
		Signature signature = Signature.getInstance("SHA256withRSA");
		signature.initVerify(loadPublicKey());
		signature.update(data.getBytes());
		return signature.verify(Base64.getDecoder().decode(signatureBytes));
	}
}
