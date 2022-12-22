package com.yogo.socket;

import java.util.List;
import java.util.UUID;

import com.corundumstudio.socketio.SocketIOServer;
import com.yogo.business.auth.UserService;
import com.yogo.enums.Role;
import com.yogo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.yogo.model.BookingInfo;
import com.yogo.model.Coordinates;
import com.yogo.model.User;
import org.springframework.stereotype.Service;

@Service
public class SocketHandler {

    @Autowired
    private SocketIOServer socketIOServer;

    @Autowired
    private UserRepository userRepository;

    /**
     * Gửi thông tin khách hàng và thông tin đặt xe cho lái xe
     *
     * @param client
     * @param data
     * @param driver
     */
    @OnEvent(value = "booking")
    public void sendBooking(SocketIOClient client, BookingInfo data, User driver) {
        Integer idDriver = driver.getId(); // Lấy id của driver
        if (SocketManager.getInstance().map.get(idDriver) != null) {
            UUID uuid = SocketManager.getInstance().map.get(idDriver); // lấy ra id socket của driver
            socketIOServer.getClient(uuid).sendEvent("booking", data);
        }
    }

    /**
     * Gửi tọa độ của user 1 cho user 2
     *
     * @param client
     * @param data
     * @param idUser2
     */
    @OnEvent(value = "track")
    public void sendTracking(SocketIOClient client, Coordinates data, Integer idUser2) {
        if (SocketManager.getInstance().map.get(idUser2) != null) {
            UUID uuid = SocketManager.getInstance().map.get(idUser2);
            socketIOServer.getClient(uuid).sendEvent("track", data);
        }
    }

    /**
     * Gửi thông tin lái xe cho khách hàng
     *
     * @param
     */
    @OnEvent(value = "driver_info")
    public void sendDriverInfo(SocketIOClient driver, User driverInfo, Integer idClient) {
        List<User> drivers = userRepository.findByRole(Role.ROLE_DRIVER);
        if (SocketManager.getInstance().map.get(idClient) != null) {
            UUID uuid = SocketManager.getInstance().map.get(idClient); // Lấy ra id socket của client
            socketIOServer.getClient(uuid).sendEvent("driver_info", driverInfo);
        }
    }

    @OnEvent(value = "finish")
    public void finish(SocketIOClient driver, Integer idClient) {
        if (SocketManager.getInstance().map.get(idClient) != null) {
            UUID uuid = SocketManager.getInstance().map.get(idClient); // Lấy ra id socket của client
            socketIOServer.getClient(uuid).sendEvent("finish", "Hoàn thành chuyến đi");
            driver.sendEvent("finish", "Hoàn thành chuyến đi");
        }
    }
}
