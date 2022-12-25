package com.yogo.business.payment;

import com.yogo.business.auth.UserService;
import com.yogo.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private UserService userService;

    @Override
    public List<PaymentResult> onPayment(PaymentRequest paymentRequest, HttpServletRequest servletRequest) {
        userService.checkSession(servletRequest);
        List<com.yogo.model.Service> serviceList = serviceRepository.findAll();
        List<PaymentResult> res = new ArrayList<>();
        serviceList.forEach(service -> {
            PaymentResult paymentResult = new PaymentResult().withId(service.getId())
                    .withName(service.getName())
                    .withPrice(service.getPrice())
                    .withTotalPrice(paymentRequest.getDistance() * service.getPrice())
                    .withDescribe(service.getDescribe());
            res.add(paymentResult);
        });
        return res;
    }
}
