package com.yogo.controller;

import com.yogo.business.order.OrderService;
import com.yogo.message.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/acceptBooking")
    public ResponseEntity<?> acceptBookingRequest(@RequestParam String bookingId, HttpServletRequest servletRequest) {
        return ResponseMessage.success(orderService.acceptBooking(bookingId, servletRequest));
    }

    @PostMapping("/rejectBooking")
    public ResponseEntity<?> rejectBookingRequest(@RequestParam String bookingId, HttpServletRequest servletRequest) {
        return ResponseMessage.success(orderService.rejectBooking(bookingId, servletRequest));
    }
}
