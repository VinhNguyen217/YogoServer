package com.yogo.config;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.yogo.business.chat.Message;
import com.yogo.business.chat.MessageChild;
import com.yogo.socket.SocketManager;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.annotation.PreDestroy;
import java.util.UUID;

@CrossOrigin
@Component
@Log4j2
public class SocketIOConfig {

    @Value("${socket.host}")
    private String host;

    @Value("${socket.port}")
    private Integer port;
    private SocketIOServer server;

    @Bean
    public SocketIOServer socketIOServer() {
        Configuration config = new Configuration();
        config.setHostname(host);
        config.setPort(port);
        server = new SocketIOServer(config);
        server.start();
        server.addConnectListener(new ConnectListener() {
            @Override
            public void onConnect(SocketIOClient client) {
                log.info("connect with " + client.getSessionId());
            }
        });

        server.addEventListener("auth", String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient client, String data, AckRequest ackSender) throws Exception {
                SocketManager.getInstance().map.put(data, client.getSessionId());    //yêu cầu truyền sesionId từ client vể server
//                if (SessionManager.getInstance().map.get(data) != null) {
//                    User user = SessionManager.getInstance().map.get(data);
//                    SocketManager.getInstance().map.put(user.getId(), client.getSessionId());
//                    SocketManager.getInstance().socketClientList.add(client);
//                    client.sendEvent("auth", "Kết nối thành công"); // Gửi tin nhắn đến phía client
//                    System.out.println("Kết nối thành công");
//                }
            }
        });


        server.addEventListener("chat", Message.class, new DataListener<Message>() {
            @Override
            public void onData(SocketIOClient socketIOClient, Message data, AckRequest ackRequest) throws Exception {
                UUID uuid = UUID.fromString(data.getTarget());
                MessageChild dataSent = new MessageChild()
                        .withUserName(data.getUserName())
                        .withContent(data.getContent());
                server.getClient(uuid).sendEvent("chat_private", dataSent);
            }
        });

        server.addDisconnectListener(new DisconnectListener() {
            @Override
            public void onDisconnect(SocketIOClient client) {
                log.info(client.getSessionId() + " disconnect");
            }
        });
        return server;
    }

    @PreDestroy
    public void stopSocketIOServer() {
        this.server.stop();
    }
}
