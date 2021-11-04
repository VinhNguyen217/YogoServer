package com.yogo.model;

import java.util.HashMap;

public class SessionManager {
	public HashMap<String, User> map;
	private static SessionManager instance = null;
	
	private SessionManager() {
		map = new HashMap<String, User>();		//key : session của user - value : đối tượng user
	}
	
	public static SessionManager getInstance() {
		if(instance == null) {
			instance = new SessionManager();
		}
		return instance;
	}

}
