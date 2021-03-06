package com.yogo.controller;

import java.io.IOException;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yogo.service.PaymentService;
import com.yogo.service.UserService;

@RestController
@RequestMapping("/payments")
public class PaymentController {

	@Autowired
	private PaymentService service;

	@Autowired
	private UserService userService;

	@PostMapping()
	public ResponseEntity<HashMap<String, Object>> calPrice(@RequestHeader(value = "session") String sessionKey,
			@RequestParam String p0, @RequestParam String p1, @RequestParam Integer serviceId) throws IOException {
		HashMap<String, Object> map = new HashMap<>();
		if (userService.isSessionValid(sessionKey) != null) {
			map = service.calPrice(p0, p1, serviceId);
			return ResponseEntity.status(HttpStatus.OK).body(map);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
		}
	}
}
