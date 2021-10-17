package com.yogo.model;

public class BookingInfo {
	private User user;
	private Booking booking;
	
	public BookingInfo(User user, Booking booking) {
		super();
		this.user = user;
		this.booking = booking;
	}
	
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Booking getBooking() {
		return booking;
	}
	public void setBooking(Booking booking) {
		this.booking = booking;
	}
	
	
}
