package com.yogo.controller;

import com.yogo.business.booking.BookingRequest;
import com.yogo.business.booking.Reason;
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
                                           @RequestBody Reason reason,
                                           HttpServletRequest servletRequest) {
        return ResponseMessage.success(bookingService.cancelBooking(bookingId, driverId, reason, servletRequest));
    }

    @PostMapping("/finish")
    public ResponseEntity<?> finishBooking(@RequestParam(value = "bookingId") String bookingId,
                                           HttpServletRequest servletRequest) {
        return ResponseMessage.success(bookingService.finishBooking(bookingId, servletRequest));
    }
}
