package com.yogo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yogo.model.Booking;
import com.yogo.repository.BookingRepository;

@Service
public class BookingService {

    @Autowired
    private BookingRepository repo;

    public Booking save(Booking booking) {
        return repo.save(booking);
    }

    public Booking findById(Integer id) {
        return repo.findById(id).get();
    }

}
