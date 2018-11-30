package com.xwj.sso.client.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
	
	@GetMapping("/me")
	public Authentication authentication(Authentication user) {
		return user;
	}
	
}
