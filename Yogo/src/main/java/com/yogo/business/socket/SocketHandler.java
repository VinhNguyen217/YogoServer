package com.yogo.business.socket;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;
import com.yogo.business.auth.UserService;
import com.yogo.business.booking.BookingInfoDto;
import com.yogo.business.chat.Message;
import com.yogo.business.chat.MessageChild;
import com.yogo.enums.Role;
import com.yogo.enums.Status;
import com.yogo.model.Booking;
import com.yogo.utils.EventConstants;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.yogo.model.BookingInfo;
import com.yogo.business.map.Coordinates;
import com.yogo.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class SocketHandler {

    @Autowired
    private SocketIOServer socketIOServer;

    @Autowired
    private UserService userService;

    @Bean
    public void handleEventAuto() {
        /**
         * Sự kiện clien gửi userId về server
         */
        socketIOServer.addEventListener(EventConstants.AUTH, String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient client, String userId, AckRequest ackSender) throws Exception {
                socketIOServer.getBroadcastOperations().sendEvent(EventConstants.AUTH, userId);
                User user = userService.findById(userId);

                if (Role.ROLE_CLIENT.equals(user.getRole())) {
                    if (!checkClientExist(userId))
                        SocketClientManage.getInstance().list.add(new UserSocket()
                                .withUserId(userId)
                                .withSocketId(client.getSessionId()));
                } else {
                    if (!checkDriverExist(userId))
                        SocketDriverManage.getInstance().list.add(new UserSocket()
                                .withUserId(userId)
                                .withSocketId(client.getSessionId())
                                .withStatus(Status.READY));
                }
                log.info("list of clients : " + SocketClientManage.getInstance().list.size());
                log.info("list of drivers : " + SocketDriverManage.getInstance().list.size());
            }
        });

        /**
         * Sự kiện chat giữa 2 user
         */
        socketIOServer.addEventListener(EventConstants.CHAT, Message.class, new DataListener<Message>() {
            @Override
            public void onData(SocketIOClient socketIOClient, Message data, AckRequest ackRequest) throws Exception {
                log.info(socketIOClient.getSessionId() + " : " + data.toString());
                socketIOServer.getBroadcastOperations().sendEvent(EventConstants.CHAT, data.getContent());
//                UUID uuid = UUID.fromString(data.getTarget());
//                MessageChild dataSent = new MessageChild()
//                        .withUserName(data.getUserName())
//                        .withContent(data.getContent());
//                socketIOServer.getClient(uuid).sendEvent(EventConstants.CHAT_PRIVATE, dataSent);
            }
        });
    }

    /**
     * Gửi thông tin khách hàng và thông tin đặt xe cho lái xe
     */
    @OnEvent(value = EventConstants.SEND_BOOKING)
    public void sendBooking(BookingInfoDto booking, UUID uuidDriver) {
        log.info("booking : " + booking.toString());
        log.info("driver : " + uuidDriver.toString());
        socketIOServer.getClient(uuidDriver).sendEvent(EventConstants.SEND_BOOKING, booking);
    }

    /**
     * Gửi tọa độ của driver cho client
     */
    @OnEvent(value = EventConstants.TRACK)
    public void sendTracking(Coordinates coordinates, UUID uuid) {
        socketIOServer.getClient(uuid).sendEvent(EventConstants.TRACK, coordinates);
    }

    /**
     * Gửi thông tin lái xe cho khách hàng
     *
     * @param
     */
    @OnEvent(value = EventConstants.SEND_DRIVER)
    public void sendDriverInfo(User driverInfo, UUID uuidClient) {
        socketIOServer.getClient(uuidClient).sendEvent(EventConstants.SEND_DRIVER, driverInfo);
    }

    private Boolean checkDriverExist(String userId) {
        List<String> driverIds = SocketDriverManage.getInstance().list.stream().map(UserSocket::getUserId).collect(Collectors.toList());
        return driverIds.contains(userId);
    }

    private Boolean checkClientExist(String userId) {
        List<String> clientIds = SocketClientManage.getInstance().list.stream().map(UserSocket::getUserId).collect(Collectors.toList());
        return clientIds.contains(userId);
    }

}
