package com.yogo.socket;

import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.yogo.model.BookingInfo;
import com.yogo.model.SessionManager;
import com.yogo.model.SocketManager;
import com.yogo.model.User;
import com.yogo.service.UserService;

@Component
public class SocketHandler {
	private SocketIOServer server = SocketServer.server;

	@Autowired
	private UserService userService;

	@OnConnect
	public void onConnect(SocketIOClient client) {
		Timer t = new Timer();
		TimerTask task = new TimerTask() {
			int time = 10;

			public void run() {
				
				
				String sessionId = client.getHandshakeData().getSingleUrlParam("session");
				if (userService.isSessionValid(sessionId) != null) {
					t.cancel();
					User user = SessionManager.getInstance().map.get(sessionId);
					// SocketManager store id-user and id-socket-user
					SocketManager.getInstance().map.put(user.getId_user(), client.getSessionId());
				}
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (time == 0) {
					t.cancel();
					if (sessionId == null)
						client.disconnect();
				}

				time--;
			}
		};
		t.schedule(task, 1000, 1000);
	}

	@OnDisconnect
	public void onDisConnect() {

	}

	@OnEvent(value = "bookingEvent")
	public void sendBooking(SocketIOClient client, BookingInfo data, User driver) {
		Integer idDriver = driver.getId_user();
		if (SocketManager.getInstance().map.get(idDriver) != null) {
			UUID uuid = SocketManager.getInstance().map.get(idDriver);
			server.getClient(uuid).sendEvent("bookingEvent", data);
		}
	}
}
