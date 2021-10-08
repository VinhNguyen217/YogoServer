package com.yogo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yogo.model.Booking;
import com.yogo.service.BookingService;
import com.yogo.service.UserService;

@RestController
@RequestMapping("/bookings")
public class BookingController {
	
	@Autowired
	private BookingService service;
	
	@Autowired
	private UserService userService;
	
	@PostMapping()
	public ResponseEntity<String> create(@RequestHeader(value = "session") String sessionKey , @RequestBody Booking booking) {
		if(userService.isSessionValid(sessionKey) != null){
			service.save(booking);
			return ResponseEntity.status(HttpStatus.OK).body("success");
		}else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("error");
		}
	}

}
