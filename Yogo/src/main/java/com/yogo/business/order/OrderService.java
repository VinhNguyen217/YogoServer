package com.yogo.business.order;


import com.yogo.business.booking.BookingInfoDto;
import com.yogo.model.Booking;

import javax.servlet.http.HttpServletRequest;

public interface OrderService {

    BookingInfoDto rejectBooking(String bookingId, HttpServletRequest servletRequest);
}
