package com.yogo.config;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.yogo.business.auth.UserService;
import com.yogo.business.socket.SocketClientManage;
import com.yogo.business.socket.SocketDriverManage;
import com.yogo.business.socket.UserSocket;
import com.yogo.enums.Role;
import com.yogo.enums.Status;
import com.yogo.model.User;
import com.yogo.utils.EventConstants;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class SocketServer {

    public final static SocketIOServer socket = init();

    @Autowired
    private static UserService userService;

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

        socketIOServer.addEventListener(EventConstants.AUTH, String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient client, String userId, AckRequest ackSender) throws Exception {
                log.info(userId);
//                SocketClientManage.getInstance().list.add(new UserSocket()
//                        .withUserId(userId)
//                        .withSocketId(client.getSessionId()));
//                SocketDriverManage.getInstance().list.add(new UserSocket()
//                        .withUserId(userId)
//                        .withSocketId(client.getSessionId()));

//                if (Role.ROLE_CLIENT.equals(user.getRole())) {
//                    if (!checkClientExist(userId))
//                        SocketClientManage.getInstance().list.add(new UserSocket()
//                                .withUserId(userId)
//                                .withSocketId(client.getSessionId()));
//                } else {
//                    if (!checkDriverExist(userId))
//                        SocketDriverManage.getInstance().list.add(new UserSocket()
//                                .withUserId(userId)
//                                .withSocketId(client.getSessionId())
//                                .withStatus(Status.READY));
//                }
                log.info("list of clients : " + SocketClientManage.getInstance().list.size());
                log.info("list of drivers : " + SocketDriverManage.getInstance().list.size());
            }
        });

        return socketIOServer;
    }

    private static Boolean checkDriverExist(String userId) {
        List<String> driverIds = SocketDriverManage.getInstance().list.stream().map(UserSocket::getUserId).collect(Collectors.toList());
        return driverIds.contains(userId);
    }

    private static Boolean checkClientExist(String userId) {
        List<String> clientIds = SocketClientManage.getInstance().list.stream().map(UserSocket::getUserId).collect(Collectors.toList());
        return clientIds.contains(userId);
    }
}
