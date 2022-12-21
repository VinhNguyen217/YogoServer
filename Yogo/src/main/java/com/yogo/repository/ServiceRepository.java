package com.yogo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yogo.model.Service;

public interface ServiceRepository extends JpaRepository<Service, Integer>{
}
