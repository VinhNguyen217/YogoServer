package com.yogo.business.travel_service;

import com.yogo.message.MessageText;
import com.yogo.model.Service;
import com.yogo.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class SerServiceImpl implements SerService{

    @Autowired
    private ServiceRepository serviceRepository;

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
}
