package com.yogo.model;

import java.util.ArrayList;
import com.yogo.model.User;

public class DriverManagement {
	public ArrayList<User> driverWork;
	private static DriverManagement instance = null;
	
	private DriverManagement() {
		driverWork = new ArrayList<>();
	}
	
	public static DriverManagement getInstance() {
		if(instance == null) {
			instance = new DriverManagement();
		}
		return instance;
	}

}
