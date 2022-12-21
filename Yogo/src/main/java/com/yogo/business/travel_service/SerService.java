package com.yogo.business.travel_service;

import java.util.List;
import java.util.Optional;

import com.yogo.message.MessageText;
import com.yogo.model.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.yogo.repository.ServiceRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

@org.springframework.stereotype.Service
public class SerService {

    @Autowired
    private ServiceRepository serviceRepository;

    public Service insert(Service service) {
        return serviceRepository.save(service);
    }

    public List<Service> getAll() {
        return serviceRepository.findAll();
    }

    public Service getById(Integer id) {
        Optional<Service> serviceOptional = serviceRepository.findById(id);
        if (serviceOptional.isPresent()) return serviceOptional.get();
        else throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, MessageText.NOT_FOUND);
    }
}
