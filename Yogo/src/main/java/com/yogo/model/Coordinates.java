package com.yogo.model;

import java.math.BigDecimal;

public class Coordinates {
	private BigDecimal latitude;	// Vĩ độ
	private BigDecimal longitude;	// Kinh độ
	
	public Coordinates() {
	
	}
	
	public Coordinates(BigDecimal latitude, BigDecimal longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
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
