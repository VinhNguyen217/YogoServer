package com.yogo.controller;

import java.util.UUID;

import com.yogo.business.booking.BookingRequest;
import com.yogo.message.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.yogo.model.Coordinates;
import com.yogo.model.DriverManager;
import com.yogo.socket.SocketManager;
import com.yogo.model.User;
import com.yogo.business.booking.BookingService;
import com.yogo.business.auth.UserService;
import com.yogo.socket.SocketHandler;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private SocketIOServer socketIOServer;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private UserService userService;

    @Autowired
    private SocketHandler socket;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody BookingRequest bookingRequest, HttpServletRequest servletRequest) {
        return ResponseMessage.success(bookingService.create(bookingRequest, servletRequest));
    }

    @PostMapping("/acceptBooking")
    public ResponseEntity<?> acceptBooking(@RequestHeader(value = "session") String sessionKey,
                                           @RequestParam Integer idBooking) {
//        HashMap<String, Object> map = new HashMap<>();
//        if (userService.isSessionValid(sessionKey) != null) {
//            User driverSelected = userService.findDriver(); // Tìm ra lái xe
//
//            if (driverSelected != null) {
//
//                Booking booking = bookingService.findById(idBooking); // lấy ra đối tượng Booking
//                bookingService.create(booking);
//
//                User client = userService.isSessionValid(sessionKey); // Lấy ra đối tượng Client
//                BookingInfo bookingInfo = new BookingInfo(client, booking); // Tạo đối tượng BookingInfo
//
//                // Tìm socket của client
//                UUID uuidClient = SocketManager.getInstance().map.get(client.getId());
//                SocketIOClient socketIOClient = socketIOServer.getClient(uuidClient);
//
//                socket.sendBooking(socketIOClient, bookingInfo, driverSelected);
//                map.put("result", "Đã tìm thấy lái xe.");
//                return ResponseMessage.success(map);
//            } else {
//                map.put("result", "Không tìm thấy lái xe.");
//                return ResponseMessage.success(map);
//            }
//        } else {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
//        }
        return ResponseMessage.success(null);
    }

    @PostMapping("/cancelBooking")
    public ResponseEntity<?> cancleBooking(@RequestHeader(value = "session") String sessionKey, @RequestParam Integer idBooking) {
//        HashMap<String, Object> map = new HashMap<>();
//        if (userService.isSessionValid(sessionKey) != null) {
//            Booking existBooking = bookingService.findById(idBooking);
//            bookingService.create(existBooking);
//            map.put("result", "Hủy thành công");
//            return ResponseMessage.success(map);
//        } else throw new HttpClientErrorException(HttpStatus.FORBIDDEN, MessageText.FORBIDDEN);
        return null;
    }

    @PostMapping("/finish")
    public void finishJourney(@RequestHeader(value = "session") String sessionKey, @RequestParam Integer idClient) {
        if (userService.isSessionValid(sessionKey) != null) {
            User driver = userService.isSessionValid(sessionKey); // lấy ra driver dựa vào sessionId

            UUID uuidDriver = SocketManager.getInstance().map.get(driver.getId());
            SocketIOClient socketIOClient = socketIOServer.getClient(uuidDriver);

            socket.finish(socketIOClient, idClient);

            DriverManager.getInstance().driverWork.remove(driver); // Xóa driver khỏi danh sách đang làm việc
            DriverManager.getInstance().driverWait.add(driver); // Thêm driver vào danh sách chờ
        }
    }

    @PostMapping("/setTracking")
    public void setTracking(@RequestHeader(value = "session") String sessionKey, @RequestBody Coordinates location,
                            @RequestParam Integer idUser) {
        if (userService.isSessionValid(sessionKey) != null) {

            User user1 = userService.isSessionValid(sessionKey);

            UUID uuidUser1 = SocketManager.getInstance().map.get(user1.getId());
            SocketIOClient socketIOClient = socketIOServer.getClient(uuidUser1);

            socket.sendTracking(socketIOClient, location, idUser);
        }
    }

}
