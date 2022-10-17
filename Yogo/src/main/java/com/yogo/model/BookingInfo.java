package com.yogo.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookingInfo {
	private User user;
	private Booking booking;
	private Payment payment;
}
