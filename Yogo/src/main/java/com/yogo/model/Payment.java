package com.yogo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Payment {
	private Integer id_payment;
	private float price;
	
	public Payment() {
		super();
	}

	public Payment(Integer id_payment, float price) {
		super();
		this.id_payment = id_payment;
		this.price = price;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId_payment() {
		return id_payment;
	}

	public void setId_payment(Integer id_payment) {
		this.id_payment = id_payment;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

}
