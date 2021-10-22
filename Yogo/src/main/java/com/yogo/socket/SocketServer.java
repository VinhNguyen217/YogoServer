package com.yogo.socket;

import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;
import com.yogo.model.SessionManager;
import com.yogo.model.SocketManager;
import com.yogo.model.User;

@Component
public class SocketServer {
	public static final SocketIOServer server = init();

	public static SocketIOServer init() {
		Configuration config = new Configuration();
		config.setHostname("localhost");
		config.setPort(8080);
		final SocketIOServer server = new SocketIOServer(config);

		Timer t = new Timer();
		TimerTask task = new TimerTask() {
			int time = 10;
			boolean active = false;

			@Override
			public void run() {
				System.out.println(time);
				server.addEventListener("auth", String.class, new DataListener<String>() {
					@Override
					public void onData(SocketIOClient client, String data, AckRequest ackSender) throws Exception {
						if (SessionManager.getInstance().map.get(data) != null) {
							t.cancel();
							server.removeAllListeners("auth");
							active = true;
							User user = SessionManager.getInstance().map.get(data);
							SocketManager.getInstance().map.put(user.getId_user(), client.getSessionId());
							client.sendEvent("auth", "success");
						}
					}
				});
				if (time == 0) {
					t.cancel();
					if (active == false) {
						server.addEventListener("auth", String.class, new DataListener<String>() {
							@Override
							public void onData(SocketIOClient client, String data, AckRequest ackSender)
									throws Exception {
								client.disconnect();
							}

						});
					}
				}
				time--;
			}
		};
		t.schedule(task, 1000, 1000);
		return server;
	}
}
