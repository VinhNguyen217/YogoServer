package com.yogo.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class timer_booking {
	private Integer id_timer_booking;
	private Timestamp  time;
	
	public timer_booking() {
		super();
	}

	public timer_booking(Integer id_timer_booking, Timestamp time) {
		super();
		this.id_timer_booking = id_timer_booking;
		this.time = time;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId_timer_booking() {
		return id_timer_booking;
	}

	public void setId_timer_booking(Integer id_timer_booking) {
		this.id_timer_booking = id_timer_booking;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}
	
}
