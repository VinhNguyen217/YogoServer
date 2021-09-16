package com.yogo.controller;

import java.math.BigDecimal;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yogo.model.Position;
import com.yogo.service.PositionService;

@RestController
public class PositionController {
	
	@Autowired
	private PositionService service;
	
	@GetMapping("/positions")
	public HashMap<String , BigDecimal> get(@RequestParam String place) {
		HashMap<String, BigDecimal> map = new HashMap<>();
		Position position =  service.find(place);
		map.put("longitude",position.getLongitude());
		map.put("latitude",position.getLatitude());
		return map;
	}
	
	
}
