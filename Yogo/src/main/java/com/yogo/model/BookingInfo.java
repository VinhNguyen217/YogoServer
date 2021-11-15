package com.yogo.model;

public class BookingInfo {
	private User user;
	private Booking booking;
	private Payment payment;
	
	public BookingInfo() {
		super();
	}

	public BookingInfo(User user, Booking booking, Payment payment) {
		super();
		this.user = user;
		this.booking = booking;
		this.payment = payment;
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
	public Payment getPayment() {
		return payment;
	}
	public void setPayment(Payment payment) {
		this.payment = payment;
	}
	
	
	
	
}
