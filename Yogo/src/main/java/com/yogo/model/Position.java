package com.yogo.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Position {
	private Integer id;
	private BigDecimal latitude;	// Vĩ độ
	private BigDecimal longitude;	// Kinh độ
	
	
	public Position() {
	
	}
	
	
	public Position(Integer id, BigDecimal latitude, BigDecimal longitude) {
		super();
		this.id = id;
		this.latitude = latitude;
		this.longitude = longitude;
	}


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public BigDecimal getLatitude() {
		return latitude;
	}


	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}


	public BigDecimal getLongitude() {
		return longitude;
	}


	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	
}
