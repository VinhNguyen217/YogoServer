package com.yogo.config;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketConfig;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.yogo.business.socket.SocketClientManage;
import com.yogo.business.socket.SocketDriverManage;
import com.yogo.business.socket.UserSocket;
import lombok.extern.log4j.Log4j2;

import java.util.Optional;

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
                log.info("connect with : " + client);
            }
        });

        socket.addDisconnectListener(new DisconnectListener() {
            @Override
            public void onDisconnect(SocketIOClient client) {
                Optional<UserSocket> userSocketClient = SocketClientManage
                        .getInstance()
                        .list
                        .stream().filter(u -> client == u.getSocketIOClient())
                        .findFirst();
                if (userSocketClient.isPresent()) {
                    SocketClientManage.getInstance().list.remove(userSocketClient.get());
                } else {
                    Optional<UserSocket> userSocketDriver = SocketDriverManage
                            .getInstance()
                            .list
                            .stream().filter(u -> client == u.getSocketIOClient())
                            .findFirst();
                    userSocketDriver
                            .ifPresent(userSocket -> SocketDriverManage.getInstance().list.remove(userSocket));
                }
                log.info("list of clients : " + SocketClientManage.getInstance().list.size());
                log.info("list of drivers : " + SocketDriverManage.getInstance().list.size());
            }
        });
        return socket;
    }
}
