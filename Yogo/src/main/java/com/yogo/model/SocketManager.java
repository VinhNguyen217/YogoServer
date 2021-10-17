package com.yogo.model;

import java.util.HashMap;
import java.util.UUID;

public class SocketManager {
	public HashMap<Integer, UUID> map;
	private static SocketManager instance = null;

	private SocketManager() {
		map = new HashMap<Integer, UUID>();
	}

	public static SocketManager getInstance() {
		if (instance == null) {
			instance = new SocketManager();
		}
		return instance;
	}

}
