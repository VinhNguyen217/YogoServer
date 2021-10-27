package com.yogo;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.yogo.socket.SocketServer;

@SpringBootApplication
public class Application {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(Application.class, args);
		SocketServer.server.start();
		SocketServer.server.addConnectListener(new ConnectListener() {
			
			@Override
			public void onConnect(SocketIOClient client) {
				// TODO Auto-generated method stub
				System.out.println("Connect success");
			}
		});
		
		SocketServer.server.addEventListener("chatmsg", String.class, new DataListener<String>() {

			@Override
			public void onData(SocketIOClient client, String data, AckRequest ackSender) throws Exception {
				// TODO Auto-generated method stub
				SocketServer.server.getBroadcastOperations().sendEvent("chatmsg", data);
			}
			
		});
	}

}
