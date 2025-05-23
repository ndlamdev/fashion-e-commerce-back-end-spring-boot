/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 4:01 PM-18/05/2025
 * User: kimin
 **/

package com.lamnguyen.payment_service.utils.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.lamnguyen.payment_service.config.PayOsConfig;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import net.minidev.json.JSONObject;
import org.apache.commons.codec.digest.HmacUtils;
import org.springframework.stereotype.Component;
import vn.payos.type.PaymentData;

import java.util.*;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
@Getter
public class SignAndVerifyDataHelper {
	PayOsConfig payOsConfig;

	public Boolean isValidData(String transactionStr, String transactionSignature) {
		try {
			String signature = new HmacUtils("HmacSHA256", payOsConfig.getChecksumKey()).hmacHex(transactionStr);
			return signature.equals(transactionSignature);
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
		return false;
	}

	public String createTransactionStr(PaymentData data) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS); // Sắp theo alphabet
		mapper.enable(SerializationFeature.INDENT_OUTPUT); // Format đẹp
		return mapper.writeValueAsString(data);
	}

	public String generateSignature(PaymentData data) throws JsonProcessingException {
		var transactionStr = createTransactionStr(data);
		return new HmacUtils("HmacSHA256", payOsConfig.getChecksumKey()).hmacHex(transactionStr);
	}
}
