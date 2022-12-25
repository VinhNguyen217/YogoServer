package com.yogo.business.travel_service;

import com.yogo.business.auth.UserService;
import com.yogo.message.MessageText;
import com.yogo.model.Service;
import com.yogo.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class SerServiceImpl implements SerService {

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private UserService userService;

    @Override
    public Service insert(Service service) {
        return serviceRepository.save(service);
    }

    @Override
    public List<Service> getAll() {
        return serviceRepository.findAll();
    }

    @Override
    public Service getById(String id) {
        Optional<Service> serviceOptional = serviceRepository.findById(id);
        if (serviceOptional.isPresent()) return serviceOptional.get();
        else throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, MessageText.NOT_FOUND);
    }

    @Override
    public List<ServiceDistanceResult> calByDistance(DistanceRequest request, HttpServletRequest servletRequest) {
        userService.checkSession(servletRequest);
        List<com.yogo.model.Service> serviceList = serviceRepository.findAll();
        List<ServiceDistanceResult> res = new ArrayList<>();
        serviceList.forEach(service -> {
            ServiceDistanceResult serviceDistanceResult = new ServiceDistanceResult()
                    .withId(service.getId())
                    .withName(service.getName())
                    .withPrice(service.getPrice())
                    .withTotalPrice(request.getDistance() * service.getPrice())
                    .withDescribe(service.getDescribe());
            res.add(serviceDistanceResult);
        });
        return res;
    }
}
