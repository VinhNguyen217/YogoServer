package com.yogo.business.payment;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface PaymentService {
    List<PaymentResult> onPayment(PaymentRequest paymentRequest, HttpServletRequest servletRequest);
}
