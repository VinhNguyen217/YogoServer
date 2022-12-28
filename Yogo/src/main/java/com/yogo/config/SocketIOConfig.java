package com.yogo.config;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.annotation.PreDestroy;

@CrossOrigin
@Component
@Log4j2
public class SocketIOConfig {

    @Value("${socket.host}")
    private String host;

    @Value("${socket.port}")
    private Integer port;
    private SocketIOServer socketIOServer;

    @Bean
    public SocketIOServer socketIOServer() {
        Configuration config = new Configuration();
        config.setHostname(host);
        config.setPort(port);
        socketIOServer = new SocketIOServer(config);
        socketIOServer.start();
        socketIOServer.addConnectListener(new ConnectListener() {
            @Override
            public void onConnect(SocketIOClient client) {
                log.info("connect with " + client.getSessionId());
            }
        });

        socketIOServer.addDisconnectListener(new DisconnectListener() {
            @Override
            public void onDisconnect(SocketIOClient client) {
                log.info(client.getSessionId() + " disconnect");
            }
        });
        return socketIOServer;
    }

    @PreDestroy
    public void stopSocketIOServer() {
        this.socketIOServer.stop();
    }
}
