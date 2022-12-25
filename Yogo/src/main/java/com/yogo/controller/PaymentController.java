package com.yogo.controller;

import com.yogo.business.payment.PaymentRequest;
import com.yogo.business.payment.PaymentService;
import com.yogo.message.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("")
    public ResponseEntity<?> onPayment(@RequestBody PaymentRequest request, HttpServletRequest servletRequest) {
        return ResponseMessage.success(paymentService.onPayment(request, servletRequest));
    }
}
