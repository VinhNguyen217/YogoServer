package com.yogo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.yogo.model.Booking;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
	@Query(value = "SELECT * FROM booking ORDER BY id_booking DESC LIMIT 1",nativeQuery = true)
	Booking findLastBooking();
}
