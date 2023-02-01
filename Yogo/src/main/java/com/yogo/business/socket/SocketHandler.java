package com.yogo.business.socket;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.listener.DataListener;
import com.yogo.business.auth.UserDto;
import com.yogo.business.auth.UserService;
import com.yogo.business.booking.BookingInfoDto;
import com.yogo.business.booking.CancelInfo;
import com.yogo.business.booking.FinishInfo;
import com.yogo.business.chat.MessageReceive;
import com.yogo.business.chat.MessageSend;
import com.yogo.business.track.TrackReceiveInfo;
import com.yogo.business.track.TrackSendInfo;
import com.yogo.config.SocketServer;
import com.yogo.enums.Role;
import com.yogo.enums.Status;
import com.yogo.model.User;
import com.yogo.utils.EventConstants;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class SocketHandler {

    @Autowired
    private UserService userService;

    @Bean
    public void handleEventAuto() {

        /**
         * send userId to server
         */
        SocketServer.socket.addEventListener(EventConstants.AUTH, String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient client, String userId, AckRequest ackSender) {
                User user = userService.findById(userId);

                if (Role.ROLE_CLIENT.equals(user.getRole())) {
                    if (!checkClientExist(userId))
                        SocketClientManage.getInstance().list.add(new UserSocket()
                                .withUserId(userId)
                                .withSocketIOClient(client));
                } else {
                    if (!checkDriverExist(userId))
                        SocketDriverManage.getInstance().list.add(new UserSocket()
                                .withUserId(userId)
                                .withSocketIOClient(client)
                                .withStatus(Status.READY));
                }
                log.info("list of clients : " + SocketClientManage.getInstance().list.size());
                log.info("list of drivers : " + SocketDriverManage.getInstance().list.size());
            }
        });

        /**
         * Chat for client and driver
         */
        SocketServer.socket.addEventListener(EventConstants.CHAT, MessageSend.class, new DataListener<MessageSend>() {
            @Override
            public void onData(SocketIOClient socketIOClient, MessageSend messageSend, AckRequest ackRequest) {
                Optional<UserSocket> userSocketOptional;
                User user = userService.findById(messageSend.getTo());
                if (Role.ROLE_CLIENT.equals(user.getRole()))
                    userSocketOptional = SocketClientManage
                            .getInstance()
                            .list.stream().filter(c -> messageSend.getTo().equals(c.getUserId()))
                            .findFirst();
                else userSocketOptional = SocketDriverManage
                        .getInstance()
                        .list.stream().filter(d -> messageSend.getTo().equals(d.getUserId()))
                        .findFirst();
                if (userSocketOptional.isPresent()) {
                    UserSocket userSocket = userSocketOptional.get();
                    MessageReceive messageReceive = new MessageReceive()
                            .withFrom(messageSend.getFrom())
                            .withMsg(messageSend.getMsg());
                    userSocket.getSocketIOClient().sendEvent(EventConstants.CHAT, messageReceive);
                }
            }
        });

        /**
         * Send track info from driver to client
         */
        SocketServer.socket.addEventListener(EventConstants.TRACK, TrackSendInfo.class, new DataListener<TrackSendInfo>() {
            @Override
            public void onData(SocketIOClient socketIOClient, TrackSendInfo trackSendInfo, AckRequest ackRequest) {
                log.info("socket driver : " + socketIOClient);
                log.info("track send info : " + trackSendInfo);
                // find client
                Optional<UserSocket> userSocketClient = SocketClientManage
                        .getInstance()
                        .list.stream().filter(c -> trackSendInfo.getTo().equals(c.getUserId()))
                        .findFirst();
                log.info("socket client : " + userSocketClient.get());

                TrackReceiveInfo trackReceiveInfo = new TrackReceiveInfo()
                        .withLat(trackSendInfo.getLat())
                        .withLon(trackSendInfo.getLon());
                log.info("track receive : " + trackReceiveInfo);
                userSocketClient.get()
                        .getSocketIOClient()
                        .sendEvent(EventConstants.TRACK, trackReceiveInfo);
            }
        });
    }

    /**
     * send client info and booking info to driver
     */
    @OnEvent(value = EventConstants.SEND_BOOKING)
    public void sendBooking(BookingInfoDto booking, SocketIOClient socketIODriver) {
        socketIODriver.sendEvent(EventConstants.SEND_BOOKING, booking);
    }

    /**
     * send driver info to client
     *
     * @param
     */
    @OnEvent(value = EventConstants.SEND_DRIVER)
    public void sendDriverInfo(UserDto driverInfo, UUID uuidClient) {
        SocketServer.socket.getClient(uuidClient).sendEvent(EventConstants.SEND_DRIVER, driverInfo);
    }

    /**
     * Send cancel booking info to driver
     *
     * @param cancelInfo
     * @param socketIOClient
     */
    @OnEvent(value = EventConstants.CANCEL_BOOKING)
    public void cancelBooing(CancelInfo cancelInfo, SocketIOClient socketIOClient) {
        socketIOClient.sendEvent(EventConstants.CANCEL_BOOKING, cancelInfo);
    }

    /**
     * Send finish booking info to client
     *
     * @param finishInfo
     * @param socketIOClient
     */
    @OnEvent(value = EventConstants.FINISH)
    public void finishBooing(FinishInfo finishInfo, SocketIOClient socketIOClient) {
        socketIOClient.sendEvent(EventConstants.FINISH, finishInfo);
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
