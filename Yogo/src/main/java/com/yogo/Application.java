package com.yogo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;
import com.yogo.model.Message;

@SpringBootApplication
public class Application {

	public static void main(String[] args) throws InterruptedException {
		
		Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(9092);

        final SocketIOServer server = new SocketIOServer(config);
        server.addEventListener("chatevent", Message.class, new DataListener<Message>() {
            @Override
            public void onData(SocketIOClient client, Message data, AckRequest ackRequest) {
                // broadcast messages to all clients
                server.getBroadcastOperations().sendEvent("chatevent", data);
            }
        });
        
        
        SpringApplication.run(Application.class, args);
        
        server.start();

        Thread.sleep(Integer.MAX_VALUE);

        server.stop();
		
		
	}
}
