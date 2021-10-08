package com.yogo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	public List<Service> list(){
		return service.listAll();
	}
}
