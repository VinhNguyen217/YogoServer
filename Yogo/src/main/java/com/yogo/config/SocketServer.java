package com.yogo.config;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

@Log4j2
public class SocketServer {

    public final static SocketIOServer socket = init();

    public static SocketIOServer init() {
        Configuration config = new Configuration();
        config.setHostname("0.0.0.0");
        config.setPort(8086);
        final SocketIOServer socketIOServer = new SocketIOServer(config);

        socketIOServer.addConnectListener(new ConnectListener() {
            @Override
            public void onConnect(SocketIOClient client) {
                log.info("connect with " + client.getSessionId());
            }
        });
        return socketIOServer;
    }
}
