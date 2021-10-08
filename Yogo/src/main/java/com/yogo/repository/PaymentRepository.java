package com.yogo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yogo.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer>{

}
