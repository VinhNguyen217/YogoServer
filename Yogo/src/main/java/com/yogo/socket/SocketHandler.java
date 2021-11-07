package com.yogo.socket;

import java.util.UUID;
import org.springframework.stereotype.Component;


import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.corundumstudio.socketio.listener.DataListener;
import com.yogo.model.BookingInfo;
import com.yogo.model.Coordinates;
import com.yogo.model.SocketManager;
import com.yogo.model.User;


@Component
public class SocketHandler {


	@OnEvent(value = "booking")
	public void sendBooking(SocketIOClient client, BookingInfo data, User driver) {
		Integer idDriver = driver.getId_user();		// Lấy id của driver
		if (SocketManager.getInstance().map.get(idDriver) != null) {
			UUID uuid = SocketManager.getInstance().map.get(idDriver);	//lấy ra id socket của driver
			SocketServer.server.getClient(uuid).sendEvent("booking", data);
		}
	}
	
	// Gửi tracking
	@OnEvent(value="tracking")
	public void sendTracking(Coordinates coordinates) {
		SocketServer.server.addEventListener("tracking", Coordinates.class, new DataListener<Coordinates>() {
			@Override
			public void onData(SocketIOClient client, Coordinates data, AckRequest ackSender) throws Exception {
				SocketServer.server.getBroadcastOperations().sendEvent("tracking", data);
			}
		});
	}
	
	//Gửi thông tin lái xe
	@OnEvent(value = "driver_info")
	public void sendDriverInfo(User user) {
		SocketServer.server.addEventListener("driver_info", User.class, new DataListener<User>() {
			@Override
			public void onData(SocketIOClient client, User user, AckRequest ackSender) throws Exception {
				SocketServer.server.getBroadcastOperations().sendEvent("tracking", user);
			}		
		});
	}
}
