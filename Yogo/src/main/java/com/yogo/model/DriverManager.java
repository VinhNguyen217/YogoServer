package com.yogo.model;

import java.util.ArrayList;
import com.yogo.model.User;

public class DriverManager {
	public ArrayList<User> driverWork;		//danh sách những driver đang làm việc
	public ArrayList<User> driverWait;		//danh sách những driver đang chờ cuốc xe
	private static DriverManager instance = null;
	
	private DriverManager() {
		driverWork = new ArrayList<>();
		driverWait = new ArrayList<>();
	}
	
	public static DriverManager getInstance() {
		if(instance == null) {
			instance = new DriverManager();
		}
		return instance;
	}
}
