package com.yogo.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yogo.model.User;
import com.yogo.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository repo;
	
	public List<User> listAll(){
		return repo.findAll();
	}
	
	public void save(User user) {
		repo.save(user);
	}
	
	public User get(Integer id) {
		return repo.findById(id).get();
	}
	
	/**
	 * Check id if exists return true , otherwise return false
	 * @param id
	 * @return
	 */
	public boolean checkId(Integer id) {
		Optional<User> user = repo.findById(id);
		if(user.isPresent()) return true;
		else return false;
	}
}
