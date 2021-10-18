package com.yogo.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yogo.model.Service;
import com.yogo.service.SerService;

@RestController
@RequestMapping("/services")
public class ServiceController {
	
	@Autowired
	private SerService service;
	
	@GetMapping()
	public ResponseEntity<HashMap<String, Object>> getServices(){
		HashMap<String, Object> map = new HashMap<>();
		List<Service> services =  service.listAll();
		map.put("services", services);
		return ResponseEntity.status(HttpStatus.OK).body(map);
	}
}
