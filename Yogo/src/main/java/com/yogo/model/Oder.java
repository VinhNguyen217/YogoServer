package com.yogo.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Oder {
	private Integer id_oder;
	private Date accept_time;
	private Integer id_user;
	private Integer id_booking;
	private String status;
	
	public Oder() {
		super();
	}
	
	public Oder(Integer id_oder, Date accept_time, Integer id_user, Integer id_booking, String status) {
		super();
		this.id_oder = id_oder;
		this.accept_time = accept_time;
		this.id_user = id_user;
		this.id_booking = id_booking;
		this.status = status;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId_oder() {
		return id_oder;
	}
	public void setId_oder(Integer id_oder) {
		this.id_oder = id_oder;
	}
	public Date getAccept_time() {
		return accept_time;
	}
	public void setAccept_time(Date accept_time) {
		this.accept_time = accept_time;
	}
	public Integer getId_user() {
		return id_user;
	}
	public void setId_user(Integer id_user) {
		this.id_user = id_user;
	}
	public Integer getId_booking() {
		return id_booking;
	}
	public void setId_booking(Integer id_booking) {
		this.id_booking = id_booking;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
