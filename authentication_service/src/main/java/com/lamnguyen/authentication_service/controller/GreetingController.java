/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 7:15 PM - 26/03/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.authentication_service.controller;

import com.lamnguyen.authentication_service.service.grpc.impl.ProfileUserGrpcClientImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping("/authentication-service/greeting")
public class GreetingController {
	private ProfileUserGrpcClientImpl profileServiceGrpc;
	@GetMapping()
	public String greeting() {
		return "Hello World!";
	}

}
