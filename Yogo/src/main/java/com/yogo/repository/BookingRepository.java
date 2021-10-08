package com.yogo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.yogo.model.Booking;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

}
