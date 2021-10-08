package com.yogo.model;

public class Driver extends User {
	
	public Driver() {
		super();
	}

	public Driver(Integer id, char type, String first_name, String last_name, String address, Integer gender,
			String email) {
		super(id, type, first_name, last_name, address, gender, email);
	}
}
