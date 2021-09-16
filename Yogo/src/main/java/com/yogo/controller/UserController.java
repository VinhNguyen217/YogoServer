package com.yogo.controller;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.yogo.model.SessionManager;
import com.yogo.model.User;
import com.yogo.service.UserService;
import com.google.common.hash.Hashing;

@RestController
public class UserController {
	
	@Autowired
	private UserService service;
	
	@GetMapping("/user")
	public List<User> list(){
		return service.listAll();
	}
	
	@PostMapping("/user")
	public HashMap<String,Boolean> add(@RequestBody User user) {
		service.save(user);
		HashMap<String, Boolean> map = new HashMap<>();
		map.put("success", true);
		return map;
	}
	
	@PostMapping("/user/login")
	public HashMap<String,String> login(@RequestParam Integer id) {
		HashMap<String, String> map = new HashMap<String, String>();
		if(service.checkId(id)) {
			String idSession = Hashing.murmur3_32().hashString(String.valueOf(id),StandardCharsets.UTF_8).toString();
			if(SessionManager.getInstance().map.containsKey(idSession)) {
				map.put("Login", "Success");
			}else {
				SessionManager.getInstance().map.put(idSession, id);
			map.put("ID_SESSION", idSession);
			}
			return map;
		}
		throw new RuntimeException("Id is not valid");
	}
	
	@GetMapping("/user/login/{id}")
	public HashMap<String,Integer> getId(@PathVariable String id) {
		HashMap<String, Integer> map = new HashMap<>();
		Integer idUser = SessionManager.getInstance().map.get(id);
		if(idUser != null) {
			map.put("ID_USER", idUser);
			return map;
		}
		throw new RuntimeException("ID is not valid ");
	}
	
	
}
