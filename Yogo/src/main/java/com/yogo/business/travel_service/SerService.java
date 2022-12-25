package com.yogo.business.travel_service;

import java.util.List;

import com.yogo.model.Service;

public interface SerService {

    Service insert(Service service);

    List<Service> getAll();

    Service getById(String id);
}
