package com.yogo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.yogo.model.Payment;


public interface PaymentRepository extends JpaRepository<Payment, Integer>{
	@Query(value = "SELECT * FROM payment ORDER BY id_payment DESC LIMIT 1",nativeQuery = true)
	Payment findLastPayment();
}
