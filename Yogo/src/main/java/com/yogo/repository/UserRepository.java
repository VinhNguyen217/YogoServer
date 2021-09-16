package com.yogo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yogo.model.User;



public interface UserRepository extends JpaRepository<User, Integer> {

}
