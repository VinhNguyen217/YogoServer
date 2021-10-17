package com.yogo.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Booking {
	private Integer id_booking;
	private Integer number;
	private Timestamp timer;
	private Integer id_service;
	private String pick_location;
	private String destination_location;

	public Booking() {
		super();
	}

	public Booking(Integer id_booking, Integer number, Timestamp timer, Integer id_service,
			String pick_location, String destination_location) {
		super();
		this.id_booking = id_booking;
		this.number = number;
		this.timer = timer;
		this.id_service = id_service;
		this.pick_location = pick_location;
		this.destination_location = destination_location;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId_booking() {
		return id_booking;
	}

	public void setId_booking(Integer id_booking) {
		this.id_booking = id_booking;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Timestamp getTimer() {
		return timer;
	}

	public void setTimer(Timestamp timer) {
		this.timer = timer;
	}

	public Integer getId_service() {
		return id_service;
	}

	public void setId_service(Integer id_service) {
		this.id_service = id_service;
	}

	public String getPick_location() {
		return pick_location;
	}

	public void setPick_location(String pick_location) {
		this.pick_location = pick_location;
	}

	public String getDestination_location() {
		return destination_location;
	}

	public void setDestination_location(String destination_location) {
		this.destination_location = destination_location;
	}

}
