package com.yogo.controller;

import com.yogo.business.booking.BookingRequest;
import com.yogo.message.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.corundumstudio.socketio.SocketIOServer;
import com.yogo.business.map.Coordinates;
import com.yogo.business.booking.BookingService;
import com.yogo.business.auth.UserService;
import com.yogo.business.socket.SocketHandler;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody BookingRequest bookingRequest, HttpServletRequest servletRequest) {
        return ResponseMessage.success(bookingService.create(bookingRequest, servletRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") String id, HttpServletRequest servletRequest) {
        return ResponseMessage.success(bookingService.findById(id, servletRequest));
    }

    @PostMapping("/acceptBooking")
    public ResponseEntity<?> acceptBooking(@RequestParam("bookingId") String bookingId,
                                           HttpServletRequest servletRequest) {
        return ResponseMessage.success(bookingService.acceptBooking(bookingId, servletRequest));
    }

    @PostMapping("/cancelBooking")
    public ResponseEntity<?> cancelBooking(@RequestParam(value = "bookingId") String bookingId,
                                           @RequestParam(value = "driverId") String driverId,
                                           HttpServletRequest servletRequest) {
        return ResponseMessage.success(bookingService.cancelBooking(bookingId, driverId, servletRequest));
    }

    @PostMapping("/finish")
    public ResponseEntity<?> finishBooking(@RequestParam(value = "bookingId") String bookingId,
                                           HttpServletRequest servletRequest) {
        return ResponseMessage.success(bookingService.finishBooking(bookingId, servletRequest));
    }

    @PostMapping("/setTracking")
    public void setTracking(@RequestHeader(value = "session") String sessionKey, @RequestBody Coordinates location,
                            @RequestParam Integer idUser) {
//        if (userService.isSessionValid(sessionKey) != null) {
//
//            User user1 = userService.isSessionValid(sessionKey);
//
//            UUID uuidUser1 = SocketManager.getInstance().map.get(user1.getId());
//            SocketIOClient socketIOClient = socketIOServer.getClient(uuidUser1);
//
//            socket.sendTracking(socketIOClient, location, idUser);
//        }
    }

}
