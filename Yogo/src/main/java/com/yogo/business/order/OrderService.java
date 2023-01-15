package com.yogo.business.order;


import com.yogo.business.booking.BookingInfoDto;

import javax.servlet.http.HttpServletRequest;

public interface OrderService {

    BookingInfoDto acceptBooking(String bookingId, HttpServletRequest servletRequest);

    BookingInfoDto rejectBooking(String bookingId, HttpServletRequest servletRequest);
}
