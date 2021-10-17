package com.yogo.socket;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;

public class SocketServer {
	public static final SocketIOServer server = init();

	public static SocketIOServer init() {
		Configuration config = new Configuration();
		config.setHostname("localhost");
		config.setPort(8080);
		final SocketIOServer server = new SocketIOServer(config);
		return server;
	}
}
