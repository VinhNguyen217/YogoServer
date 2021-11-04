package com.yogo.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import com.corundumstudio.socketio.SocketIOClient;


public class SocketManager {
	public HashMap<Integer, UUID> map;
	public ArrayList<SocketIOClient> socketClientList;
	private static SocketManager instance = null;

	private SocketManager() {
		map = new HashMap<Integer, UUID>(); // key : id của user - value : id socket của client
		socketClientList = new ArrayList<>();
	}

	public static SocketManager getInstance() {
		if (instance == null) {
			instance = new SocketManager();
		}
		return instance;
	}

}
