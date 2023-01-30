package com.yogo.config;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketConfig;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class SocketServer {

    public final static SocketIOServer socket = init();

    public static SocketIOServer init() {
        Configuration config = new Configuration();
        config.setHostname("0.0.0.0");
        config.setPort(8086);
        SocketConfig sockConf = new SocketConfig();
        sockConf.setReuseAddress(true);
        config.setSocketConfig(sockConf);

        final SocketIOServer socket = new SocketIOServer(config);

        socket.addConnectListener(new ConnectListener() {
            @Override
            public void onConnect(SocketIOClient client) {
                log.info("connect with : " + client.getSessionId());
            }
        });

        socket.addDisconnectListener(new DisconnectListener() {
            @Override
            public void onDisconnect(SocketIOClient client) {
                log.info("disconnect with " + client.getSessionId());
            }
        });
        return socket;
    }
}