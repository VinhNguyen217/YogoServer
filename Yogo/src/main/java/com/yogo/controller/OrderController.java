package com.yogo.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.UUID;

import com.yogo.message.MessageText;
import com.yogo.message.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.yogo.model.DriverManager;
import com.yogo.model.Oder;
import com.yogo.socket.SocketManager;
import com.yogo.model.User;
import com.yogo.service.OrderService;
import com.yogo.business.auth.UserService;
import com.yogo.socket.SocketHandler;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private SocketIOServer server;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private SocketHandler socket;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestHeader(value = "session") String sessionKey,
                                    @RequestBody Oder order) {
        HashMap<String, Object> map = new HashMap<>();
        if (userService.isSessionValid(sessionKey) != null) {
            orderService.save(order);
            Oder u = orderService.findLastOder();
            map.put("order", u);
            return ResponseMessage.success(map);
        }
        throw new HttpClientErrorException(HttpStatus.FORBIDDEN, MessageText.FORBIDDEN);
    }

    @PostMapping("/acceptBookingRequest")
    public void acceptBookingRequest(@RequestHeader(value = "session") String sessionKey, @RequestParam Integer idOrder,
                                     @RequestParam Integer idClient) {
        HashMap<String, Object> map = new HashMap<>();
        if (userService.isSessionValid(sessionKey) != null) {
            User driver = userService.isSessionValid(sessionKey); // Lấy ra đối tượng lái xe

            LocalDateTime acceptTime = LocalDateTime.now();
            Oder order = orderService.findById(idOrder);
            order.setAcceptTime(acceptTime);
            order.setStatus("accept");
            orderService.save(order);

            UUID uuidDriver = SocketManager.getInstance().map.get(driver.getId());
            SocketIOClient socketIOClient = server.getClient(uuidDriver);

            socket.sendDriverInfo(socketIOClient, driver, idClient); // Gửi thông tin lái xe cho khách hàng

            DriverManager.getInstance().driverWait.remove(driver);
            DriverManager.getInstance().driverWork.add(driver);
        }
    }

    @PostMapping("/rejectBookingRequest")
    public void rejectBookingRequest(@RequestHeader(value = "session") String sessionKey, @RequestParam Integer idOrder,
                                     @RequestParam Integer idClient) {
        HashMap<String, Object> map = new HashMap<>();
        if (userService.isSessionValid(sessionKey) != null) {
            User driver = userService.isSessionValid(sessionKey); // Lấy ra đối tượng lái xe

            LocalDateTime acceptTime = LocalDateTime.now();
            Oder order = orderService.findById(idOrder);
            order.setAcceptTime(acceptTime);
            order.setStatus("cancel");
            orderService.save(order);

            UUID uuidDriver = SocketManager.getInstance().map.get(driver.getId());
            SocketIOClient socketIOClient = server.getClient(uuidDriver);

            socket.sendDriverInfo(socketIOClient, null, idClient); // Gửi thông tin là null cho khách hàng
        }
    }
}
