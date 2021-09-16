package com.yogo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yogo.model.Position;


public interface PositionRepository extends JpaRepository<Position, Integer> {
	
}
