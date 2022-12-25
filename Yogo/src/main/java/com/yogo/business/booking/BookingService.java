package com.yogo.business.booking;

import com.yogo.model.Booking;

import javax.servlet.http.HttpServletRequest;

public interface BookingService {

    BookingDriverResult create(BookingRequest bookingRequest, HttpServletRequest servletRequest);

    Booking findById(String id,HttpServletRequest servletRequest);
}
