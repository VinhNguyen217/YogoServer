package com.yogo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yogo.model.Booking;
import com.yogo.repository.BookingRepository;

@Service
public class BookingService {
	
	@Autowired
	private BookingRepository repo;
	
	public void save(Booking booking) {
		repo.save(booking);
	}
	
}
