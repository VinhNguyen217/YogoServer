package com.yogo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yogo.model.Oder;
import com.yogo.repository.OrderRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository repo;

	public void save(Oder oder) {
		repo.save(oder);
	}
	
	public Oder findLastOder() {
		return repo.findLastOder();
	}
	
	public Oder findById(Integer id) {
		return repo.findById(id).get();
	}
}
