package com.yogo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Service {
	private Integer id_service;
	private String name;
	private Integer active;
	private float price;
	private String detail;
	
	public Service() {
		super();
	}

	public Service(Integer id_service, String name, Integer active, float price, String detail) {
		super();
		this.id_service = id_service;
		this.name = name;
		this.active = active;
		this.price = price;
		this.detail = detail;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId_service() {
		return id_service;
	}

	public void setId_service(Integer id_service) {
		this.id_service = id_service;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getActive() {
		return active;
	}

	public void setActive(Integer active) {
		this.active = active;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}
	
}
