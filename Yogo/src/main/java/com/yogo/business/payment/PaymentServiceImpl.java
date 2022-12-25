package com.yogo.business.payment;

import com.yogo.business.auth.UserService;
import com.yogo.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private UserService userService;

}
