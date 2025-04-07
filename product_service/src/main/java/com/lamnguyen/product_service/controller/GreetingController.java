/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 11:48 AM - 07/04/2025
 * User: kimin
 **/

package com.lamnguyen.product_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/greeting")
@RestController
public class GreetingController {

	@GetMapping
	public String greeting() {
		return "Hello world";
	}
}
