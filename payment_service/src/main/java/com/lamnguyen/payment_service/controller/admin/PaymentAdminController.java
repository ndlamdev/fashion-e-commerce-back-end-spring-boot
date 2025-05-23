/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 11:40 PM-22/05/2025
 * User: kimin
 **/

package com.lamnguyen.payment_service.controller.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.lamnguyen.payment_service.domain.request.WebhookUrlRequest;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.payos.PayOS;

@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping("/admin/payment/v1")
public class PaymentAdminController {
	PayOS payOS;

	@PostMapping(path = "/confirm-webhook")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
	public ObjectNode confirmWebhook(@RequestBody @Valid WebhookUrlRequest urlRequest) {
		ObjectMapper objectMapper = new ObjectMapper();
		ObjectNode response = objectMapper.createObjectNode();
		try {
			String str = payOS.confirmWebhook(urlRequest.url());
			response.set("data", objectMapper.valueToTree(str));
			response.put("error", 0);
			response.put("message", "ok");
			return response;
		} catch (Exception e) {
			e.printStackTrace(System.err);
			response.put("error", -1);
			response.put("message", e.getMessage());
			response.set("data", null);
			return response;
		}
	}
}
