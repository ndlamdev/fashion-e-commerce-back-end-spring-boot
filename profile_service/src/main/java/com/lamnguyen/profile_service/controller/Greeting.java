/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 2:23 PM - 07/04/2025
 * User: kimin
 **/

package com.lamnguyen.profile_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/greeting")
public class Greeting {
	@GetMapping
	public String greeting() {
		return "Hello world!";
	}
}
