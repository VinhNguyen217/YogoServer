package com.yogo.socket;

import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.corundumstudio.socketio.AckCallback;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.corundumstudio.socketio.listener.DataListener;
import com.yogo.model.BookingInfo;
import com.yogo.model.Coordinates;
import com.yogo.model.SessionManager;
import com.yogo.model.SocketManager;
import com.yogo.model.User;
import com.yogo.service.UserService;

@Component
public class SocketHandler {
	private SocketIOServer server = SocketServer.server;

	@Autowired
	private UserService userService;

	@OnEvent(value = "booking")
	public void sendBooking(SocketIOClient client, BookingInfo data, User driver) {
		Integer idDriver = driver.getId_user();
		if (SocketManager.getInstance().map.get(idDriver) != null) {
			UUID uuid = SocketManager.getInstance().map.get(idDriver);	//socket của driver
			server.getClient(uuid).sendEvent("booking", data);
		}
	}
	
	// Gửi tracking
	@OnEvent(value="tracking")
	public void sendTracking(Coordinates coordinates) {
		server.addEventListener("tracking", Coordinates.class, new DataListener<Coordinates>() {
			@Override
			public void onData(SocketIOClient client, Coordinates data, AckRequest ackSender) throws Exception {
				server.getBroadcastOperations().sendEvent("tracking", data);
			}
		});
	}
	
	//Gửi thông tin lái xe
	@OnEvent(value = "driver_info")
	public void sendDriverInfo(User user) {
		server.addEventListener("driver_info", User.class, new DataListener<User>() {
			@Override
			public void onData(SocketIOClient client, User user, AckRequest ackSender) throws Exception {
				server.getBroadcastOperations().sendEvent("tracking", user);
			}		
		});
	}
}
