package com.yogo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.yogo.model.Booking;

import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, String> {
    @Query(value = "SELECT * FROM booking WHERE id = :id", nativeQuery = true)
    Optional<Booking> findById(String id);

    Optional<Booking> findByIdAndUserId(String id, String userId);
}
