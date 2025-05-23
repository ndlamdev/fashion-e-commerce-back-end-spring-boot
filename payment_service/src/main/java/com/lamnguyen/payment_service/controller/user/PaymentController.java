/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 11:40 PM-22/05/2025
 * User: kimin
 **/

package com.lamnguyen.payment_service.controller.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.lamnguyen.payment_service.service.business.IPaymentService;
import com.lamnguyen.payment_service.service.kafka.producer.IOrderKafkaProducer;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import vn.payos.PayOS;
import vn.payos.type.PaymentLinkData;
import vn.payos.type.Webhook;
import vn.payos.type.WebhookData;

@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping("/payment/v1")
public class PaymentController {
	PayOS payOS;
	IPaymentService paymentService;
	IOrderKafkaProducer orderKafkaProducer;

	@GetMapping(path = "/{orderCode}")
	public ObjectNode getOrderById(@PathVariable("orderCode") long orderCode) {
		ObjectMapper objectMapper = new ObjectMapper();
		ObjectNode response = objectMapper.createObjectNode();

		try {
			PaymentLinkData order = payOS.getPaymentLinkInformation(orderCode);

			response.set("data", objectMapper.valueToTree(order));
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

	@PutMapping(path = "/{orderCode}")
	public ObjectNode cancelOrder(@PathVariable("orderCode") int orderCode) {
		ObjectMapper objectMapper = new ObjectMapper();
		ObjectNode response = objectMapper.createObjectNode();
		try {
			PaymentLinkData order = payOS.cancelPaymentLink(orderCode, null);
			orderKafkaProducer.removeOrder(orderCode);
			response.set("data", objectMapper.valueToTree(order));
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

	@PostMapping(path = "/payos-transfer-handler")
	public ObjectNode payosTransferHandler(@RequestBody ObjectNode body)
			throws JsonProcessingException, IllegalArgumentException {

		ObjectMapper objectMapper = new ObjectMapper();
		ObjectNode response = objectMapper.createObjectNode();
		Webhook webhookBody = objectMapper.treeToValue(body, Webhook.class);
		try {
			response.put("error", 0);
			response.put("message", "Webhook delivered");
			response.set("data", null);

			WebhookData data = payOS.verifyPaymentWebhookData(webhookBody);
			var orderCode = data.getOrderCode();
			paymentService.paySuccess(orderCode);
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
