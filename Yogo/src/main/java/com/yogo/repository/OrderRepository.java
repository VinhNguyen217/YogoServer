package com.yogo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.yogo.model.Oder;

public interface OrderRepository extends JpaRepository<Oder, String>{
	@Query(value = "SELECT * FROM oder ORDER BY id_oder DESC LIMIT 1",nativeQuery = true)
	Oder findLastOder();
}
