package com.yogo.socket;

import com.corundumstudio.socketio.AuthorizationListener;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.HandshakeData;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.yogo.model.SessionManager;

public class SocketServer {

	
	public static void connectSocketIo() throws InterruptedException {

		Configuration config = new Configuration();
//		config.setHostname("0.0.0.0");
		config.setHostname("localhost");
		config.setPort(8080);
		config.setAuthorizationListener(new AuthorizationListener() {
			
			@Override
			public boolean isAuthorized(HandshakeData data) {
				// TODO Auto-generated method stub
//				String sessionId = data.getSingleUrlParam("sessionId");
//				if(isSessionValid(sessionId))
//					return true;
//				else return false;
				String username=data.getSingleUrlParam("username");
				String password=data.getSingleUrlParam("password");
				
				if(username != null && password != null)
					return true;
				else return false;
			}
		});
		final SocketIOServer server = new SocketIOServer(config);

		server.addConnectListener(new ConnectListener() {
			@Override
			public void onConnect(SocketIOClient client) {
				System.out.println("Connection is initialized");
			}
		});

		server.addDisconnectListener(new DisconnectListener() {

			@Override
			public void onDisconnect(SocketIOClient client) {
				// TODO Auto-generated method stub
				System.out.println("Disconnected");
			}
		});
		
		server.start();
	}

	/**
	 * Check sessionId
	 * @param session
	 * @return
	 */
	private static boolean isSessionValid(String session) {
		if (SessionManager.getInstance().map.containsKey(session))
			return true;
		else
			return false;
	}
}
