package com.yogo.controller;

import java.util.HashMap;
import java.util.UUID;

import com.yogo.message.MessageText;
import com.yogo.message.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.yogo.model.Booking;
import com.yogo.model.BookingInfo;
import com.yogo.model.Coordinates;
import com.yogo.model.DriverManager;
import com.yogo.model.Payment;
import com.yogo.model.SocketManager;
import com.yogo.model.User;
import com.yogo.service.BookingService;
import com.yogo.service.PaymentService;
import com.yogo.service.UserService;
import com.yogo.socket.SocketHandler;
import com.yogo.socket.SocketServer;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping("/booking")
public class BookingController {

    private SocketIOServer server = SocketServer.server;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private UserService userService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private SocketHandler socket;

    @GetMapping("/test")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok("Hello");
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestHeader(value = "session") String sessionKey,
                                    @RequestBody Booking booking) {
        HashMap<String, Object> map = new HashMap<>();

        if (userService.isSessionValid(sessionKey) != null) {
            return ResponseMessage.success(bookingService.save(booking));
        } else {
            map.put("booking", null);
            return ResponseMessage.success(map);
        }
    }

    @PostMapping("/acceptBooking")
    public ResponseEntity<?> acceptBooking(@RequestHeader(value = "session") String sessionKey,
                                           @RequestParam Integer idBooking) {
        HashMap<String, Object> map = new HashMap<>();
        if (userService.isSessionValid(sessionKey) != null) {
            User driverSelected = userService.findDriver(); // Tìm ra lái xe

            if (driverSelected != null) {

                Booking booking = bookingService.findById(idBooking); // lấy ra đối tượng Booking
                booking.setStatus("accept");
                bookingService.save(booking);

                User client = userService.isSessionValid(sessionKey); // Lấy ra đối tượng Client
                Payment payment = paymentService.findLastPayment();
                BookingInfo bookingInfo = new BookingInfo(client, booking, payment); // Tạo đối tượng BookingInfo

                // Tìm socket của client
                UUID uuidClient = SocketManager.getInstance().map.get(client.getUserId());
                SocketIOClient socketIOClient = server.getClient(uuidClient);

                socket.sendBooking(socketIOClient, bookingInfo, driverSelected);
                map.put("result", "Đã tìm thấy lái xe.");
                return ResponseMessage.success(map);
            } else {
                map.put("result", "Không tìm thấy lái xe.");
                return ResponseMessage.success(map);
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
        }
    }

    @PostMapping("/cancelBooking")
    public ResponseEntity<?> cancleBooking(@RequestHeader(value = "session") String sessionKey, @RequestParam Integer idBooking) {
        HashMap<String, Object> map = new HashMap<>();
        if (userService.isSessionValid(sessionKey) != null) {
            Booking existBooking = bookingService.findById(idBooking);
            existBooking.setStatus("cancel"); // Cập nhật trạng thái hủy cho booking
            bookingService.save(existBooking);
            map.put("result", "Hủy thành công");
            return ResponseMessage.success(map);
        } else throw new HttpClientErrorException(HttpStatus.FORBIDDEN, MessageText.FORBIDDEN);
    }

    @PostMapping("/finish")
    public void finishJourney(@RequestHeader(value = "session") String sessionKey, @RequestParam Integer idClient) {
        if (userService.isSessionValid(sessionKey) != null) {
            User driver = userService.isSessionValid(sessionKey); // lấy ra driver dựa vào sessionId

            UUID uuidDriver = SocketManager.getInstance().map.get(driver.getUserId());
            SocketIOClient socketIOClient = server.getClient(uuidDriver);

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

            UUID uuidUser1 = SocketManager.getInstance().map.get(user1.getUserId());
            SocketIOClient socketIOClient = server.getClient(uuidUser1);

            socket.sendTracking(socketIOClient, location, idUser);
        }
    }

}
