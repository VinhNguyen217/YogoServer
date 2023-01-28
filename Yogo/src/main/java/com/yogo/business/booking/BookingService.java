package com.yogo.business.booking;

import com.yogo.model.Booking;

import javax.servlet.http.HttpServletRequest;

public interface BookingService {

    Booking create(BookingRequest bookingRequest, HttpServletRequest servletRequest);

    Booking findById(String id,HttpServletRequest servletRequest);

    Booking acceptBooking(String bookingId, HttpServletRequest servletRequest);
}
