package com.yogo.controller;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yogo.model.Client;
import com.yogo.model.Driver;
import com.yogo.model.SessionManager;
import com.yogo.model.User;
import com.yogo.service.UserService;

import javassist.expr.Instanceof;

import com.google.common.hash.Hashing;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService service;

	@PostMapping("")
	public ResponseEntity<HashMap<String, Object>> register(@RequestBody User user) {
		HashMap<String, Object> map = new HashMap<>();
		service.save(user);
		map.put("id_user", service.getId());
		return ResponseEntity.status(HttpStatus.OK).body(map);
	}

	@PostMapping("/login")
	public ResponseEntity<HashMap<String, Object>> login(@RequestParam Integer id) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		if (service.isIdValid(id)) {
			String idSession = Hashing.murmur3_32().hashString(String.valueOf(id), StandardCharsets.UTF_8).toString();
			User user = service.get(id);
			map.put("session_id", idSession);
			map.put("info", user);
			SessionManager.getInstance().map.put(idSession, user);
			return ResponseEntity.status(HttpStatus.OK).body(map);
		} else {
			map.put("session_id", null);
			map.put("info", null);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
		}
	}

	@PostMapping("/find")
	public ResponseEntity<HashMap<String, Object>> findDriver(@RequestHeader(value = "session") String sessionKey) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		if (service.isSessionValid(sessionKey) != null) {
			if (service.findDriver() != null) {
				User driver = service.findDriver();
				map.put("driver", driver);
			}
			return ResponseEntity.status(HttpStatus.OK).body(map);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
		}
	}

}
