package com.yogo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yogo.repository.ServiceRepository;

@Service
public class SerService {
	
	@Autowired
	private ServiceRepository repo;
	
	public List<com.yogo.model.Service> getAll(){
		return repo.findAll();
	}
		
	public com.yogo.model.Service getById(Integer id){
		return repo.findById(id).get();
	}
}
