package com.yogo.socket;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.yogo.model.DriverManager;
import com.yogo.model.SessionManager;
import com.yogo.model.SocketManager;
import com.yogo.model.User;
import com.yogo.service.UserService;

public class SocketServer {

	@Autowired
	private static UserService userService;

	public final static SocketIOServer server = init();

	public static SocketIOServer init() {
		Configuration config = new Configuration();
		config.setHostname("0.0.0.0");
		config.setPort(8080);
		final SocketIOServer server = new SocketIOServer(config);

		server.addConnectListener(new ConnectListener() {
			@Override
			public void onConnect(SocketIOClient client) {
				Timer t = new Timer();
				TimerTask task = new TimerTask() {
					int time = 2;
					boolean status = false;

					@Override
					public void run() {
//						System.out.println(time);
						if (SocketManager.getInstance().socketClientList.contains(client)) {
							status = true;
							t.cancel();
						}
						if (time == 0) {
							t.cancel();
							if (status == false) {
								client.disconnect();
							}
						}
						time--;
					}
				};
				t.schedule(task, 1000, 1000);

			}
		});

		server.addEventListener("auth", String.class, new DataListener<String>() {
			@Override
			public void onData(SocketIOClient client, String data, AckRequest ackSender) throws Exception {
				if (SessionManager.getInstance().map.get(data) != null) {
					User user = SessionManager.getInstance().map.get(data);
					SocketManager.getInstance().map.put(user.getId_user(), client.getSessionId());
					SocketManager.getInstance().socketClientList.add(client);
					client.sendEvent("auth", "K???t n???i th??nh c??ng"); // G???i tin nh???n ?????n ph??a client
//					System.out.println("K???t n???i th??nh c??ng");
				}
			}
		});
		return server;
	}
}
