package com.yogo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {
	
	private Integer id_user;
	private char type;
	private String first_name;
	private String last_name;
	private String address;
	private Integer gender;
	private String email;
	
	public User() {
		super();
	}

	public User(Integer id_user, char type, String first_name, String last_name, String address, Integer gender,
			String email) {
		super();
		this.id_user = id_user;
		this.type = type;
		this.first_name = first_name;
		this.last_name = last_name;
		this.address = address;
		this.gender = gender;
		this.email = email;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId_user() {
		return id_user;
	}
	
	public void setId_user(Integer id_user) {
		this.id_user = id_user;
	}
	public char getType() {
		return type;
	}
	public void setType(char type) {
		this.type = type;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}	
}
