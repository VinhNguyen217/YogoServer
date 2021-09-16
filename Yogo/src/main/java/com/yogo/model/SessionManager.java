package com.yogo.model;

import java.util.HashMap;

public class SessionManager {
	public HashMap<String, Integer> map;
	private static SessionManager instance = null;
	
	private SessionManager() {
		map = new HashMap<String, Integer>();
	}
	
	public static SessionManager getInstance() {
		if(instance == null) {
			instance = new SessionManager();
		}
		return instance;
	}
}
